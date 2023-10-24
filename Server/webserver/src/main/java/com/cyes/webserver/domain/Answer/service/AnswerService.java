package com.cyes.webserver.domain.Answer.service;

import com.cyes.webserver.domain.Answer.dto.AnswerResponse;
import com.cyes.webserver.domain.Answer.dto.AnswerSaveServiceRequest;
import com.cyes.webserver.domain.Answer.entity.Answer;
import com.cyes.webserver.domain.Answer.repository.AnswerRepository;
import com.cyes.webserver.exception.CustomException;
import com.cyes.webserver.exception.CustomExceptionList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerService{

    private final AnswerRepository answerRepository;

    /**답안을 제출하는 메서드
     * 같은 문항에 이미 답을 제출했는지 체크하고
     * 제출하지 않았다면 저장한다.
    */
    @Transactional
    public AnswerResponse save(AnswerSaveServiceRequest answerSaveServiceRequest){
        Long memberId = answerSaveServiceRequest.getMemberId();
        Long quizId = answerSaveServiceRequest.getQuizId();
        Integer problemNumber = answerSaveServiceRequest.getProblemNumber();
        //이미 답을 제출한 적 있는지 확인
        Optional<Answer> findOptionalAnswer = answerRepository.findAnswerByMemberIdAndQuizIdAndProblemNumber(memberId, quizId, problemNumber);

        if (findOptionalAnswer.isPresent()){
            throw new CustomException(CustomExceptionList.ALREADY_SUBMIT);
        }

        //Dto -> Entity
        Answer answer = answerSaveServiceRequest.toEntity();

        //save
        answerRepository.save(answer);

        //Entity -> Dto
        AnswerResponse answerResponse = answer.toAnswerResponse();

        return answerResponse;
    }

    //Sort정보를 Pageable에 넘겨줘야 한다.
    public List<AnswerResponse> findAnswerByMemberIdAndQuizId(Long memberId, Long quizId){
        //problem_number를 기준으로 오름차순 정렬을 해주는 Sort 객체 생성한다.
        Sort sort = Sort.by(Sort.Order.asc("problem_number"));

        //memberId, quizId, Sort를 파라미터로 넘겨준다.
        List<Answer> answerList = answerRepository.findAnswerByMemberIdAndQuizId(memberId, quizId, sort);

        //Response로 바꿔준다.
        List<AnswerResponse> answerResponseList = toAnswerResponse(answerList);

        //반환
        return answerResponseList;
    }

    private List<AnswerResponse> toAnswerResponse(List<Answer> answerList){

        List<AnswerResponse> list = new ArrayList<>();

        for (Answer answer : answerList) {
            AnswerResponse answerResponse = answer.toAnswerResponse();
            list.add(answerResponse);
        }
        return list;
    }
}
