package com.cyes.webserver.db.domain.member;

import com.cyes.webserver.db.domain.common.BaseEntity;
import com.cyes.webserver.utils.oauth.enums.OAuthProvider;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@NoArgsConstructor //기본 생성자 만들어줌
@AllArgsConstructor //여기에 필드에 쓴 모든생성자만 만들어줌
@DynamicUpdate //update 할때 실제 값이 변경됨 컬럼으로만 update 쿼리를 만듬
@Entity //JPA Entity 임을 명시
@Getter //Lombok 어노테이션으로 getter
@Setter //Lombok 어노테이션으로 setter
@Table(name = "member") //테이블 관련 설정 어노테이션
public class Member extends BaseEntity {// 생성일,수정일,삭제일 상속

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본 키 자동 생성
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "member_email", nullable = false, unique = true)
    private String email;

    @Column(name = "member_nickname", nullable = false)
    private String nickname;

    @Column(name = "refresh_token", nullable = true)
    private String refreshToken;

    @Column(name = "oauth_provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuthProvider oAuthProvider;




    @Builder
    public Member(String email, String nickname, OAuthProvider oAuthProvider) {
        this.email = email;
        this.nickname = nickname;
        this.oAuthProvider = oAuthProvider;
    }
}


