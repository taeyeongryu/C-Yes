import axios from "axios";

// 백엔드 base URL
const BASE_URL = process.env.REACT_APP_SPRING_URL;

export const requestKakaoLoginInfo = async (authorizationCode: string) => {
    const resp = await axios.post(`${BASE_URL}/api/oauth/login/kakao`, { authorizationCode });
    console.log(resp);
};

export const exchangeCodeForToken = async (code: string) => {
    const response = await axios.get(`${BASE_URL}/oauth/token?code=${code}`);
    return response.headers.authorization;
};

export const fetchUserProfile = async (token: string) => {
    const response = await axios.get(`${BASE_URL}/user/mypage`, {
        headers: {
            Authorization: token,
            request: token,
        },
    });
    return response.data;
};
