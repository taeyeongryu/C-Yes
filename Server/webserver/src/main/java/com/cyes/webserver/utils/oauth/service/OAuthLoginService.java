package com.cyes.webserver.utils.oauth.service;


import com.cyes.webserver.db.domain.User;
import com.cyes.webserver.db.repository.UserRepository;
import com.cyes.webserver.exception.CustomException;
import com.cyes.webserver.exception.CustomExceptionList;
import com.cyes.webserver.utils.jwt.AuthTokens;
import com.cyes.webserver.utils.jwt.AuthTokensGenerator;
import com.cyes.webserver.utils.oauth.request.OAuthLoginParams;
import com.cyes.webserver.utils.oauth.request.RegenTokenReq;
import com.cyes.webserver.utils.oauth.response.LoginResponse;
import com.cyes.webserver.utils.oauth.response.OAuthInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final UserRepository userRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public LoginResponse login(OAuthLoginParams params) {
        // API 서버로부터 유저 정보를 받아옴
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        
        // 유저 정보를 바탕으로 신규 유저는 회원가입을, 기존 유저는 로그인을 실행
        User user = findOrCreateUser(oAuthInfoResponse);
        
        // 로그인 한 유저 ID를 바탕으로 accessToken과 refreshToken 생성
        AuthTokens token = authTokensGenerator.generate(user.getUserId());

        // user 테이블에 refreshToken 저장
        user.setRefreshToken(token.getRefreshToken());
        userRepository.flush();

        return new LoginResponse(token, user.getUserId());
    }

    public Long logout(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(CustomExceptionList.USER_NOT_FOUND_ERROR));

        user.setRefreshToken(null);
        userRepository.save(user);

        return user.getUserId();
    }

    private User findOrCreateUser(OAuthInfoResponse oAuthInfoResponse) {
        return userRepository.findByEmail(oAuthInfoResponse
                        .getEmail())
                        .orElseGet(() -> newUser(oAuthInfoResponse));
    }

    private User newUser(OAuthInfoResponse oAuthInfoResponse) {
        User user = User.builder()
                .email(oAuthInfoResponse.getEmail())
                .nickname(oAuthInfoResponse.getNickname())
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();
        userRepository.save(user);

        return user;
    }

    public LoginResponse regenToken(RegenTokenReq regenTokenReq) {

        User user = userRepository.findById(regenTokenReq.getUserId())
                .orElseThrow(()-> new CustomException(CustomExceptionList.USER_NOT_FOUND_ERROR));

        String refreshToken = user.getRefreshToken();

        if (authTokensGenerator.verifyToken(regenTokenReq.getRefreshToken()) &&
                refreshToken.equals(regenTokenReq.getRefreshToken())) {

            AuthTokens token = authTokensGenerator.generate(user.getUserId());

            user.setRefreshToken(token.getRefreshToken());
            userRepository.save(user);

            return new LoginResponse(token, user.getUserId());

        } else {
            throw new CustomException(CustomExceptionList.REFRESH_TOKEN_ERROR);
        }
    }
}