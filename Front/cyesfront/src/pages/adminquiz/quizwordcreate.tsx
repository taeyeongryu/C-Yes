import React, { useState } from "react";
import axios from "axios"; // Axios 라이브러리 import

const QuizWordCrate = () => {
  const [inputValue, setInputValue] = useState("");

  const noCheckProblemWord = () => {
    // Axios를 사용하여 API 호출 수행
    axios
      .post(`http://localhost:5000/api/adminproblem/create/${inputValue}`)
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
      <input
        type="text"
        value={inputValue}
        onChange={(e) => setInputValue(e.target.value)}
        placeholder="단어 입력하세요."
        className="input-field"
      />
      <button
        onClick={noCheckProblemWord}
        className="no-check-problem-word-button"
      >
        단어 보내기
      </button>
    </div>
  );
};

export default QuizWordCrate;
