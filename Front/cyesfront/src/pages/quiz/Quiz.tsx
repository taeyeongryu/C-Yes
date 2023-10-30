import React, { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import RoundCornerBtn from "../../components/RoundCornerBtn";
import "./Quiz.css";

interface ModalProps {
  showModal: boolean;
  showContent: boolean;
  toggleContent: () => void;
}

function Modal(props: ModalProps) {
  const { showModal, showContent, toggleContent } = props;
  const navigate = useNavigate();

  const moveMain = () => {
    //메인페이지 이동
    navigate("/live");
  };

  const handleCompleteClick = () => {
    toggleContent(); 
    // 랭킹 산정 완료 버튼을 클릭하면 랭킹내용 표시
  };

  return (
    showModal && (
      <div className="modal">
        <div className="modal-content">
          {showContent ? (
            <div className="modal-items">
              <div className="loading-background">
                <div className="loading-topic">
                  <p>🏆 오늘의 랭킹</p>
                </div>
              </div>

              <div className="rank-content">
                <p>1등 00 </p>
                <p>2등 00 </p>
                <p>3등 00 </p>
              </div>

              <RoundCornerBtn type="submit" onClick={moveMain} bgHover="black">
                메인
              </RoundCornerBtn>
            </div>
          ) : (
            //로딩중
            <div className="rank-loading">
              <div className="loading-background">
                <div className="end-topic">🥁 퀴즈 종료</div>
              </div>

              <div className="loading-text">순위 산정 중</div>
              <img src="/img/loading.gif" alt="로딩 중" width={60}></img>
              <button onClick={handleCompleteClick}>산정 완료</button>
            </div>
          )}
        </div>
      </div>
    )
  );
}

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
  const [submitted, setSubmitted] = useState(false);
  const [showConfirmation, setShowConfirmation] = useState(false);
  const answerInput = useRef<HTMLTextAreaElement | null>(null);
  const [isTextareaEnabled, setIsTextareaEnabled] = useState(true);
  const [textareaValue, setTextareaValue] = useState("");

  const [showModalContent, setShowModalContent] = useState(false);
  const [isQuizStarted, setIsQuizStarted] = useState(false);

  const [isThisQuestionStarted, setIsThisQuestionStarted] = useState(false);;


  const handleTextareaChange = (
    event: React.ChangeEvent<HTMLTextAreaElement>
  ) => {
    setTextareaValue(event.target.value);
  };

  const startQuiz = () => {
    setIsQuizStarted(true);
  };

  const toggleSubmit = () => {
    //여기서 backend랑 통신하면 댈듯
    if (!submitted) {
      setSubmitted(true); // 제출 완료 상태로 설정
      setIsTextareaEnabled(false); //textarea 비활성화
    }
  };

  const toggleContent = () => {
    setShowModalContent(true);
  };

  const [showModal, setShowModal] = useState(false);

  const openModal = () => {
    setShowModal(true);
  };


  useEffect(() => {
    const timer = setInterval(() => {
      if (isQuizStarted && progress >= 100) {
        clearInterval(timer);
        if (currentQuestion < questions.length - 1) {
          setIsTextareaEnabled(false);
          setShowConfirmation(true);

          setTimeout(() => {
            setShowConfirmation(false);

            // 문제 바뀌는 구간
            setSubmitted(false);
            setCurrentQuestion(currentQuestion + 1);

            //textarea 활성화
            setIsTextareaEnabled(true);
            setTextareaValue("");

            setProgress(0);
          }, 3000); // 3초 후에 다음 문제로 이동
        } else {
          // setShowEndPage(true);
          openModal();
          // clearInterval(timer);
        }
      } else if (isQuizStarted ) {
        setProgress(progress + 0.05);
      }
    }, 0.5); // 0.01초마다 업데이트 (1000 이 1초)

    return () => {
      clearInterval(timer);
    };
  }, [progress, currentQuestion, questions, isQuizStarted]);

  return (
    <div className="container">
      <img className="live-logo-img" src="/img/live_logo.png" alt="" />
      <div className="head">SSA피드 퀴즈</div>

      <div className="form">
        <div className="form-group">
          <div className="quiz-container">
            <div className="quiz">
              <div className="quiz-content">
                {isQuizStarted ? (
                  questions[currentQuestion].question
                ) : (
                  <div>
                    <div>대기 중</div>
                    <button onClick={startQuiz}>시작하기</button>
                  </div>
                )}
              </div>
            </div>
          </div>

          <div>
            {isQuizStarted && (
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
            )}
          </div>
          {isQuizStarted ? (
            <div className="input-content">
              <div>
                <textarea
                  ref={answerInput}
                  id="answer-input"
                  name="content"
                  value={textareaValue}
                  onChange={handleTextareaChange}
                  disabled={!isTextareaEnabled} // 비활성화 상태 조절
                  style={{
                    backgroundColor: isTextareaEnabled ? "white" : "lightgray", // 배경색 제어
                    color: isTextareaEnabled ? "black" : "gray", // 텍스트 색상 제어
                  }}
                  placeholder={isTextareaEnabled ? "입력하세요" : " "} // placeholder 설정
                />
              </div>

              <div>
                <RoundCornerBtn
                  type="submit"
                  onClick={() => toggleSubmit()}
                  bgColor={submitted ? "#265587" : undefined} 
                  bgHover="#265587"
                  disabled={submitted}
                >
                  {submitted ? "제출 완료" : "제출"}
                </RoundCornerBtn>
              </div>
            </div>
          ) : (
            <div>
              <textarea placeholder="퀴즈가 곧 시작합니다!" disabled />
            </div>
          )}
        </div>

        <div>
          <ProgressBar progress={progress} />
        </div>
        <p></p>
        <div>
            <button  >서버 연결</button>
            
            <button >정답 확인</button>
        </div>
      </div>

      <Modal
        showModal={showModal}
        showContent={showModalContent}
        toggleContent={toggleContent}
      />
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
