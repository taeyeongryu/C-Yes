import React, { useState } from "react";
import "./quizwordcreate.css";
import axios from "axios"; // Axios 라이브러리 import

const QuizWordCrate = () => {
  const [question, setQuestion] = useState("");
  const [answer, setAnswer] = useState("");
  const [category, setCategory] = useState("");

  const noCheckProblemWord = () => {
    // Axios를 사용하여 API 호출 수행
    axios
      .get(
        `http://localhost:5000/api/adminproblem/create?question=${question}&answer=${answer}&category=${category}`
      )
      .then((response) => {
        console.log("API 응답 데이터: ", response.data);
        // 원하는 작업 수행
      })
      .catch((error) => {
        console.error("API 호출 중 오류 발생: ", error);
      });
  };

  return (
    <div className="login-container">
      <div className="adminquiz">
        문제 생성 (문제, 답, 카테고리)
        <br></br>
        <br></br>
        <input
          type="text"
          value={question}
          onChange={(e) => setQuestion(e.target.value)}
          placeholder="문제를 입력하세요."
          className="input-field"
        />
        <br></br>
        <input
          type="text"
          value={answer}
          onChange={(e) => setAnswer(e.target.value)}
          placeholder="답을 입력하세요."
          className="input-field"
        />
        <br></br>
        <input
          type="text"
          value={category}
          onChange={(e) => setCategory(e.target.value)}
          placeholder="카테고리를 입력하세요."
          className="input-field"
        />
        <br></br>
        <button
          onClick={noCheckProblemWord}
          className="no-check-problem-word-button"
        >
          문제 만들기
        </button>
      </div>
    </div>
  );
};

export default QuizWordCrate;
