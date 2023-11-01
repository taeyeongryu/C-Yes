package com.cyes.webserver.domain.stompSocket.service;

import com.cyes.webserver.domain.Answer.entity.Answer;
import com.cyes.webserver.domain.problem.dto.ProblemResponse;
import com.cyes.webserver.domain.problem.entity.Problem;
import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.entity.ProblemType;
import com.cyes.webserver.domain.problem.repository.ProblemRepository;
import com.cyes.webserver.domain.problem.service.ProblemService;
import com.cyes.webserver.domain.rank.dto.GradingResult;
import com.cyes.webserver.domain.stompSocket.dto.SubmitRedis;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MessageServiceTest {

    @Autowired
    private ProblemService problemService;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private MessageService messageService;

    @Test
    @DisplayName("Problem, Answer를 이용해서 결과를 계산해서 순위대로 반환한다.")
    void getGradingResultList() {
        // given

        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<ProblemResponse> problemByCategoryOrType = problemService.findProblemByCategoryOrType(ProblemCategory.NETWORK, null, pageRequest);
        List<ProblemResponse> problemResponseList = problemByCategoryOrType.getContent();
        List<SubmitRedis> answerList = getAnswerList();

        // when
        List<GradingResult> gradingResultList = messageService.getGradingResultList(answerList, problemResponseList);
        // then


        assertThat(gradingResultList).hasSize(3).extracting("memberId", "correctProblemCount", "correctProblemDuringTime")
                .containsExactly(
                        Tuple.tuple(1L, 3, 222L)
                        , Tuple.tuple(3L, 3, 780L)
                        , Tuple.tuple(2L, 1, 210L)
                );
    }

    private List<SubmitRedis> getAnswerList(){
        List<SubmitRedis> arrayList = new ArrayList<>();
        arrayList.add(SubmitRedis.builder().memberId(1L).problemOrder(1).submitContent("소켓").duringTime(2L).build());
        arrayList.add(SubmitRedis.builder().memberId(1L).problemOrder(2).submitContent("프로세스간 통신").duringTime(20L).build());
        arrayList.add(SubmitRedis.builder().memberId(1L).problemOrder(3).submitContent("허브").duringTime(200L).build());
        arrayList.add(SubmitRedis.builder().memberId(2L).problemOrder(1).submitContent("").duringTime(20L).build());
        arrayList.add(SubmitRedis.builder().memberId(2L).problemOrder(2).submitContent("").duringTime(300L).build());
        arrayList.add(SubmitRedis.builder().memberId(2L).problemOrder(3).submitContent("허브").duringTime(210L).build());
        arrayList.add(SubmitRedis.builder().memberId(3L).problemOrder(1).submitContent("소켓").duringTime(250L).build());
        arrayList.add(SubmitRedis.builder().memberId(3L).problemOrder(2).submitContent("프로세스간 통신").duringTime(240L).build());
        arrayList.add(SubmitRedis.builder().memberId(3L).problemOrder(3).submitContent("허브").duringTime(290L).build());
        //소켓,프로세스간 통신, 허브
        return arrayList;
    }
}