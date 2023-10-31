package com.cyes.webserver.domain.member.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }
    @Test
    @DisplayName("설명")
    void findByMemberEmail() {
        // given

        // when

        // then
    }
    @Test
    @DisplayName("설명")
    void findByMemberIdAndMemberNickname() {
        // given

        // when

        // then
    }
}