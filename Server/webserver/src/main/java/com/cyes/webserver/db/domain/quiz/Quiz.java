package com.cyes.webserver.db.domain.quiz;

import com.cyes.webserver.db.domain.common.BaseEntity;
import com.cyes.webserver.db.domain.member.Member;
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

    @Column(name = "quiz_link", nullable = false)
    private String link;

    @Builder
    public Quiz(Long id, Member member, String title, LocalDateTime startDateTime, String link) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.startDateTime = startDateTime;
        this.link = link;
    }
}
