package com.cyes.webserver.redisListener;

import com.cyes.webserver.domain.problem.dto.ProblemResponse;
import com.cyes.webserver.domain.stompSocket.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTaskFactory {

    private final MessageService messageService;


    public void quizProcedureTask(Long quizId) throws InterruptedException, JsonProcessingException {

        List<ProblemResponse> problems; // 문제 찾아오기
        long solvableTime = 20000L; // 기본 10초

        problems = messageService.startSession(quizId);
        System.out.println("problems = " + problems);
        Thread.sleep(1000);

        for (ProblemResponse problem : problems) {
            messageService.sendProblem(quizId, problem);
            Thread.sleep(solvableTime);

            messageService.sendAnswer(quizId, problem);
            Thread.sleep(3000);
        }

        messageService.sendEnd(quizId);
        Thread.sleep(1000);
        messageService.sendResult(quizId,problems);
    }
}
