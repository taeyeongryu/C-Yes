package com.cyes.webserver.domain.quiz.dto;

import com.cyes.webserver.domain.member.entity.Member;
import com.cyes.webserver.domain.quiz.entity.Quiz;
import com.cyes.webserver.domain.quizproblem.entity.QuizProblem;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Builder
public class QuizCreateRequestToService {

    private String quizTitle;
    private LocalDateTime quizStartDate;
    private List<String> problemList;

    @Builder
    public QuizCreateRequestToService(String quizTitle,LocalDateTime quizStartDate, List<String> problemList) {
        this.quizTitle = quizTitle;
        this.quizStartDate = quizStartDate;
        this.problemList = problemList;
    }

    public Quiz toQuizEntity(Member member) {

        Quiz quiz = Quiz.builder()
                .title(this.quizTitle)
                .member(member)
                .startDateTime(this.quizStartDate)
                .build();

        return quiz;
    }

    public QuizProblem toQuizProblemEntity(Quiz quiz, String problemId) {
        QuizProblem quizProblem = QuizProblem.builder()
                .quiz(quiz)
                .problemId(problemId)
                .build();
        return quizProblem;
    }

}
