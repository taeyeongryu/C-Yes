package com.cyes.webserver.TestDomain;

import com.cyes.webserver.domain.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class TestRedisServiceTest {

    @Autowired
    TestRedisService testRedisService;

    @Test
    void save() {

        Member member = new Member();
        member.setMemberId(100L);
        member.setMemberNickname("hihi");

        testRedisService.save("me", member);
        Member another = testRedisService.getObject("me", Member.class);

        System.out.println("another = " + another);
    }


    @Test
    void saveList() {

        List<Object> list = new ArrayList<>();

        for(int i=0; i<5; i++) {
            Member member = new Member();
            member.setMemberId((long) i);
            member.setMemberNickname("hihi");
            list.add(member);
        }

        testRedisService.saveList("me1", list);

        List<Member> getList = testRedisService.getListObject("me1", Member.class);

        System.out.println("getList = " + getList);

    }

}