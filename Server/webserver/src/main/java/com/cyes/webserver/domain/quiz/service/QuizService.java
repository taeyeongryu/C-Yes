package com.cyes.webserver.domain.quiz.service;

import com.cyes.webserver.domain.member.entity.Member;
import com.cyes.webserver.domain.member.repository.MemberRepository;
import com.cyes.webserver.domain.quiz.dto.QuizCreateRequestToService;
import com.cyes.webserver.domain.quiz.dto.QuizCreateResponse;
import com.cyes.webserver.domain.quiz.dto.QuizInfoResponse;
import com.cyes.webserver.domain.quiz.entity.Quiz;
import com.cyes.webserver.domain.quiz.repository.QuizRepository;
import com.cyes.webserver.domain.quizproblem.entity.QuizProblem;
import com.cyes.webserver.domain.quizproblem.repository.QuizProblemRepository;
import com.cyes.webserver.exception.CustomException;
import com.cyes.webserver.exception.CustomExceptionList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizProblemRepository quizProblemRepository;
    private final MemberRepository memberRepository;


    /* 
    퀴즈 정보 조회
     */
    public QuizInfoResponse searchQuiz() {

        // 가장 최근에 생성된 라이브 퀴즈쇼 조회
        Quiz quiz = quizRepository.findLiveQuiz().orElseThrow(() -> new CustomException(CustomExceptionList.QUIZ_NOT_FOUND_ERROR));

        // Entity -> Dto
        QuizInfoResponse quizInfoResponse = quiz.toQuizInfoResponse();

        return quizInfoResponse;

    }

    /*
    퀴즈 개설
     */
    public QuizCreateResponse createQuiz(QuizCreateRequestToService quizCreateRequestToService) {

        // TODO 이메일 임시로 하드코딩. 로그인 정보에서 가져와야함.
        Member member = memberRepository.findByEmail("jjhjjh1159@gmail.com").orElseThrow(() -> new CustomException(CustomExceptionList.MEMBER_NOT_FOUND_ERROR));

        // Dto -> Entity
        Quiz quiz = quizCreateRequestToService.toQuizEntity(member);

        // Insert Quiz
        quizRepository.save(quiz);

        // QuizProblem
        for (String problemId : quizCreateRequestToService.getProblemList()) {
            // Dto -> Entity
            QuizProblem quizProblem = quizCreateRequestToService.toQuizProblemEntity(quiz, problemId);
            // Insert QuizProblem
            quizProblemRepository.save(quizProblem);
        }

        // Entity -> Response Dto
        QuizCreateResponse quizCreateResponse = quiz.toQuizCreateResponse();

        return quizCreateResponse;

    }


}
