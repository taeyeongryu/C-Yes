package com.cyes.webserver.domain.quiz.repository;

import com.cyes.webserver.domain.member.entity.Member;
import com.cyes.webserver.domain.member.enums.MemberAuthority;
import com.cyes.webserver.domain.member.repository.MemberRepository;
import com.cyes.webserver.domain.quiz.entity.Quiz;
import com.cyes.webserver.utils.oauth.enums.OAuthProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class QuizRepositoryTest {
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private MemberRepository memberRepository;
    @AfterEach
    void tearDown() {
        quizRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("당일 열리는 라이브 퀴즈를 조회한다.")
    void findLiveQuiz() {

        // given
        Member member1 = Member.builder().memberEmail("email1@123.123").memberNickname("nickname1").memberAuthority(MemberAuthority.USER).oAuthProvider(OAuthProvider.GUEST).build();
        Member member2 = Member.builder().memberEmail("email2@123.123").memberNickname("nickname1").memberAuthority(MemberAuthority.USER).oAuthProvider(OAuthProvider.GUEST).build();
        memberRepository.save(member1);
        memberRepository.save(member2);
        LocalDateTime startDateTime1 = LocalDateTime.of(2023, 5, 5, 5, 5, 5);
        LocalDateTime startDateTime2 = LocalDateTime.of(2023, 5, 5, 8, 5, 5);
        Quiz quiz1 = Quiz.builder().member(member1).title("가쥬아1").startDateTime(startDateTime1).build();
        Quiz quiz2 = Quiz.builder().member(member2).title("가쥬아2").startDateTime(startDateTime2).build();
        quizRepository.save(quiz1);
        quizRepository.save(quiz2);

        // when
        Optional<Quiz> liveQuiz = quizRepository.findLiveQuiz(LocalDateTime.of(2023, 5, 5, 5, 5));

        // then
        assertThat(liveQuiz).isNotEmpty();
        assertThat(liveQuiz.get()).extracting(
                quiz -> quiz.getMember().getMemberId()
                ,quiz -> quiz.getTitle()
                ,quiz -> quiz.getStartDateTime()).containsExactly(member1.getMemberId(), "가쥬아1", startDateTime1);
    }
}