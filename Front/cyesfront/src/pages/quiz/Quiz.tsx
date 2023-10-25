import React, { useState, useEffect, useRef } from "react";
import EndQuiz from "./EndQuiz";
import "./Quiz.css";
import RoundCornerBtn from "../../components/RoundCornerBtn";
import { useNavigate } from "react-router-dom";
const Quiz: React.FC = () => {

  const [questions, setQuestions] = useState([
    {
      question:
        "프로그래밍에 집중한 유연한 개발 방식으로 상호작용, 소프트웨어, 협력, 변화 대응에 가치를 두는 이것은?",
      answer: "애자일",
    },
    { question: "두 번째 질문", answer: "아보카도" },
    { question: "세 번째 질문", answer: "답3" },
  ]);

  const [currentQuestion, setCurrentQuestion] = useState(0);
  const [progress, setProgress] = useState(0);
  const [showEndPage, setShowEndPage] = useState(false);
  const [submitted, setSubmitted] = useState(false);
  //
  const [showConfirmation, setShowConfirmation] = useState(false);
  const answerInput = useRef<HTMLTextAreaElement | null>(null);

  const toggleSubmit = () => {
    //여기서 backend랑 통신하면 댈듯
    if (!submitted) {
      setSubmitted(true); // 제출 완료 상태로 설정
    }
  };


  useEffect(() => {
    const timer = setInterval(() => {
      if (progress >= 100) {
        if (currentQuestion < questions.length - 1) {
   
          setShowConfirmation(true);

          setTimeout(() => {
            setShowConfirmation(false);
            // textarea 초기화
            if (answerInput.current) {
              answerInput.current.value = "";
            }
            // 문제 바뀌는 구간
            setSubmitted(false);
            setCurrentQuestion(currentQuestion + 1);
            setProgress(0);
          }, 3000); // 3초 후에 다음 문제로 이동

        } else {
          setShowEndPage(true);
          // clearInterval(timer);
        }
      } else {
        setProgress(progress + 20);
      }
    }, 1000); // 0.01초마다 업데이트 (1000 이 1초)

    return () => {
      clearInterval(timer);
    };
  }, [progress, currentQuestion, questions]);

  return (
    <div className="container">
      <img className="live-logo-img" src="/img/live_logo.png" alt="" />
      <div className="head">SSA피드 퀴즈</div>

      {!showEndPage ? (
        <div className="form">
          <div className="form-group">
            <div className="quiz-container">
              <div className="quiz">
                <div className="quiz-content">
                  {questions[currentQuestion].question}
                </div>
              </div>
            </div>

            <div>
              <div className="answer-box" style={{ display: "flex" }}>
                {Array.from({
                  length: questions[currentQuestion].answer.length,
                }).map((_, index) => (
                  <div key={index} className="square">
                    {showConfirmation
                      ? questions[currentQuestion].answer[index]
                      : null}
                  </div>
                ))}
              </div>
            </div>
            
            <div>
              <textarea ref={answerInput} id="answer-input" name="content" />
            </div>

            <RoundCornerBtn
              type="submit"
              onClick={() => toggleSubmit()}
              bgHover="black"
            >
              {submitted ? "제출 완료" : "제출"}
            </RoundCornerBtn>
          </div>

          <div>
            <ProgressBar progress={progress} />
          </div>
        </div>
      ) : (
        <EndQuiz />
      )}
    </div>
  );
};

const ProgressBar: React.FC<{ progress: number }> = ({ progress }) => (
  <div className="progress-bar">
    <div
      style={{
        width: `${progress}%`,
        backgroundColor: progress >= 80 ? "red" : "blue",
        height: "10px",
      }}
    />
  </div>
);

export default Quiz;
