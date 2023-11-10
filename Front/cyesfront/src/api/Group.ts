import axios from "axios";

// axios 인스턴스를 생성합니다.
const groupApi = axios.create({
  baseURL: process.env.REACT_APP_SPRING_URI,
  headers: { "Content-Type": "application/json;charset=UTF-8" },
  timeout: 3000,
});

// 문제를 가져오는 새로운 함수를 정의합니다.
export const getGroupQuiz = async (): Promise<any> => {
  try {
    const response = await groupApi.get("/quiz/group/info");
    console.log("response", response);
    return response.data;
  } catch (err) {
    console.error("getGroupQuiz error: ", err);
    return null;
  }
};
