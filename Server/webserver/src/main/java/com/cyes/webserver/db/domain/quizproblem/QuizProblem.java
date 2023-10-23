package com.cyes.webserver.db.domain.quizproblem;

import com.cyes.webserver.db.domain.quiz.Quiz;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "quiz_problem")
public class QuizProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_problem_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quid_id", nullable = false)
    private Quiz quiz;

    @Column(name = "problem_id", nullable = false)
    private String problemId;

}
