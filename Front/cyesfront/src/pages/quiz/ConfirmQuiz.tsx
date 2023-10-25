import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
type Props = {};

const ConfirmQuiz = (props: Props) => {
  const navigate = useNavigate();
  const location = useLocation();
  const question = location.state?.question;

  useEffect(() => {
    const timer = setTimeout(() => {
      navigate(-1);
    }, 3000);

    return () => {
      clearTimeout(timer);
    };
  }, [navigate]);

  return (
    <div>
      <h2>정답 확인</h2>
      <p>Question: {question?.question}</p>
      <p>Answer: {question?.answer}</p>
      
    </div>
  );
};
export default ConfirmQuiz;
