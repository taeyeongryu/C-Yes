import React from 'react';
import './Login.css';
import axios from 'axios';

// env로 변경
const REST_API_KEY = "17a1b4d9fcc605cecc82fd7399c0717f"; 
const REDIRECT_URI = "http://localhost:9510/login/kakao/callback";
const KAKAO_AUTH_URI = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;

const Login = () => {

    const handleKakaoLogin = () => {
        window.location.href = KAKAO_AUTH_URI;
    };

    const loginApi = axios.create({
        // baseURL: process.env.REACT_APP_SPRING_URL,
        headers: { "content-type": "application/json" },
        timeout: 3000,
    });

    // 수정된 handleCheck 함수
    const handleCheck = async () => {
        const data = await loginApi
            .get(`https://cyes.site/api/problem/all`)
            .then((resp) => {
                  return resp.data;
            })
            .catch((err) => {
                console.error("Error during login:", err);
            });
        
        console.log(data);
    };

    // const naverLogin = async () => {
    // };

    return (
        <div className='login-container'>
            <img className='logo-img' src='/img/cyes_logo_img.png' alt='logo img'/>
            <button onClick={handleKakaoLogin} className="login-button kakao-login"/>
            {/* <button onClick={naverLogin} className="login-button naver-login" /> */}
            <button onClick={handleCheck} className="test-login-button">
                zzz
            </button>
        </div>
    );
};

export default Login;