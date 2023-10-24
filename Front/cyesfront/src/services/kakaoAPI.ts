import axios from "axios";
 
// 백엔드 base URL
const BASE_URL = "http://k9b103a.p.ssafy.io:8080/api/v1";

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
