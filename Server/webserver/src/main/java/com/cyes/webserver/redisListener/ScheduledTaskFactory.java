package com.cyes.webserver.redisListener;

import com.cyes.webserver.domain.problem.dto.ProblemResponse;
import com.cyes.webserver.domain.stompSocket.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTaskFactory {

    private final MessageService messageService;


    public void sessionTask(Long quizId) throws InterruptedException {

        List<ProblemResponse> problems; // 문제 찾아오기
        Long solvableTime = 10000L; // 기본 10초

        problems = messageService.startSession(quizId);
        Thread.sleep(1000);

        for (ProblemResponse problem : problems) {
            messageService.sendQuestion(quizId, problem);
            Thread.sleep(solvableTime);

            messageService.sendAnswer(quizId, problem);
            Thread.sleep(3000);
        }


        Thread.sleep(1000);
        messageService.endSolve(quizId);
        messageService.sendResult(quizId);
    }

    public void 세션_시작(String quizId){
        // START
        log.info("세션 시작 : " + quizId);
    }

    public void 문제_전송(String quizId, int num){
        // QUESTION
        log.info("문제 전송 : {}, 문제 번호 : {} ", quizId, num);
    }

    public void 답_전송(String quiz, int num){
        // ANSWER
        log.info("답 전송 : {}, 문제 번호 : {} ", quiz, num);
    }

    public void 문제_종료_및_대기(String quiz){
        // END
        log.info("문제 종료 및 대기 : {}", quiz);
    }

    public void 결과_전송(String quiz) throws InterruptedException {
        //RESULT
        log.info("결과 전송 : {}", quiz);
    }

}
