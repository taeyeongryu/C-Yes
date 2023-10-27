package com.cyes.webserver.domain.quiz.entity;

import com.cyes.webserver.common.entity.BaseEntity;
import com.cyes.webserver.domain.member.entity.Member;
import com.cyes.webserver.domain.quiz.dto.QuizCreateResponse;
import com.cyes.webserver.domain.quiz.dto.QuizInfoResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "quiz")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "quiz_title", nullable = false)
    private String title;

    @Column(name = "quiz_start_date", nullable = false)
    private LocalDateTime startDateTime;



    @Builder
    public Quiz(Long id, Member member, String title, LocalDateTime startDateTime) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.startDateTime = startDateTime;
    }


    public QuizInfoResponse toQuizInfoResponse() {
        QuizInfoResponse quizInfoResponse = QuizInfoResponse.builder()
                .quizId(this.id)
                .quizTitle(this.title)
                .quizStartDate(this.startDateTime)
                .build();

        return quizInfoResponse;
    }

    public QuizCreateResponse toQuizCreateResponse() {
        QuizCreateResponse quizCreateResponseByService = QuizCreateResponse.builder()
                .quizId(this.id)
                .quizTitle(this.title)
                .build();

        return quizCreateResponseByService;
    }

}
