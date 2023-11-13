package com.cyes.webserver.domain.stompSocket.service.start;

import com.cyes.webserver.domain.problem.dto.response.ProblemResponse;
import com.cyes.webserver.domain.problem.service.ProblemService;
import com.cyes.webserver.domain.quizproblem.repository.QuizProblemRepository;
import com.cyes.webserver.domain.stompSocket.dto.SessionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

import static com.cyes.webserver.redis.KeyGenerator.TOTAL_PARTICIPANT;

@Service
@Slf4j
@RequiredArgsConstructor
public class StartService {

    private final QuizProblemRepository quizProblemRepository;
    private final ProblemService problemService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final ChannelTopic channelTopic;

    /**
     * client에게 퀴즈쇼 시작 신호를 전송하는 메서드
     * @param quizId (퀴즈id)
     * @return List<problemResponse> 퀴즈 정보
     */
    public List<ProblemResponse> startSession(Long quizId) {

        // 퀴즈 문제 pk 조회
        List<String> list = quizProblemRepository.findQuizProblems(quizId);
        // (문제, 정답) 리스트 조회
        List<ProblemResponse> problemAnswerList = problemService.findAllProblemByQuiz(list);
        // 클라이언트한테 시작 신호 보내기
        redisTemplate.convertAndSend(channelTopic.getTopic(), new SessionMessage(quizId, SessionMessage.MessageType.START));

        return problemAnswerList;
    }

    public int fixTotalParticipantsNumber(Long quizId) {
        String key = TOTAL_PARTICIPANT+quizId;

        String totalNumber = stringRedisTemplate.opsForValue().get(key);
        stringRedisTemplate.expire(key, Duration.ofMinutes(30));

        return Integer.parseInt(totalNumber);
    }
}
