package com.cyes.webserver.domain.quiz.dto;

import com.cyes.webserver.domain.problem.dto.request.ProblemSaveByUserRequest;
import com.cyes.webserver.domain.problem.entity.ProblemByUser;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizCreateRequestByUser {
    private Long memberEmail;
    private String quizTitle;
    private LocalDateTime startDateTime;
    private List<ProblemSaveByUserRequest> problemByUserList;
}
