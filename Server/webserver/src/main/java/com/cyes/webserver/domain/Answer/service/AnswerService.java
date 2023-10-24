package com.cyes.webserver.domain.Answer.service;

import com.cyes.webserver.domain.Answer.dto.AnswerResponse;
import com.cyes.webserver.domain.Answer.dto.AnswerSaveServiceRequest;
import com.cyes.webserver.domain.Answer.entity.Answer;
import com.cyes.webserver.domain.Answer.repository.AnswerRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerService {

    private final AnswerRepository answerRepository;
    @Transactional
    public AnswerResponse save(AnswerSaveServiceRequest answerSaveServiceRequest){
        //Dto -> Entity
        Answer answer = answerSaveServiceRequest.toEntity();

        //save
        answerRepository.save(answer);

        //Entity -> Dto
        AnswerResponse answerResponse = answer.toAnswerResponse();

        return answerResponse;
    }

    //Sort정보를 Pageable에 넘겨줘야 한다.
    public List<AnswerResponse> findAnswerByMemberIdAndQuizId(Long memberId,Long quizId){
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
