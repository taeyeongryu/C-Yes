package com.cyes.webserver.db.repository.member;

import com.cyes.webserver.db.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByMemberIdAndNickname(Long memberId, String nickname);
}
