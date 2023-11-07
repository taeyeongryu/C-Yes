import React, { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import RoundCornerBtn from "../../components/RoundCornerBtn";
import "./Quiz.css";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { useSelector } from "react-redux";
import ProgressBar from "../../components/ProgressBar";
import LoadingModal from "../../components/LoadingModal";
import RankingModal from "../../components/modal/RankingModal";
import TextTimer from "../../components/TextTimer";
import { useLocation } from "react-router-dom";
import MomentOfRank from "../../components/modal/MomentOfRank";
import ChatComponent from "../../components/ChatComponent";

interface ModalProps {
  showModal: boolean;
  showContent: boolean;
  toggleContent: () => void;
  memberList: Array<any>;
  myScore?: number;
  totalProblemLength?: number;
  modalType: string;
}

function Modal(props: ModalProps) {
  const {
    showModal,
    showContent,
    toggleContent,
    memberList,
    myScore,
    totalProblemLength,
    modalType,
  } = props;
  const navigate = useNavigate();

  const moveMain = () => {
    navigate("/live");
  };

  if (!showModal) {
    return null;
  }

  const renderModalContent = () => {
    if (!showContent) {
      return <LoadingModal />;
    }

    switch (modalType) {
      case "result":
        return (
          <RankingModal
            memberList={memberList}
            myScore={myScore}
            totalProblemLength={totalProblemLength}
            onNavigate={moveMain}
          />
        );
      case "moment":
        return (
          <MomentOfRank
          // 여기에 필요한 props를 전달하세요.
          />
        );
      default:
        return null;
    }
  };

  return (
    <div className="modal">
      <div className="modal-content">{renderModalContent()}</div>
    </div>
  );
}

const Quiz: React.FC = () => {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);

  // URL 매개변수에서 정보 추출
  const targetHourString = searchParams.get("targetHour");
  const targetHour = targetHourString ? parseInt(targetHourString, 10) : 0;

  const targetMinString = searchParams.get("targetMin");
  const targetMin = targetMinString ? parseInt(targetMinString, 10) : 0;

  type ProblemMessage = {
    quizId: number;
    type: string;
    question: string;
    order: number;
    selections: Array<string>;
  };

  type AnswerMessage = {
    quizId: number;
    type: string;
    answer: string;
  };

  type ChatMessage = {
    quizId: number;
    type: string;
    memberId: number;
    message: string;
  };

  const [progress, setProgress] = useState(0);
  const [submitted, setSubmitted] = useState(false);
  const [isTextareaEnabled, setIsTextareaEnabled] = useState(true);
  const [textareaValue, setTextareaValue] = useState("");
  const [showModalContent, setShowModalContent] = useState(false);
  const [isQuizStarted, setIsQuizStarted] = useState(false);
  const [messageList, setMessageList] = useState<ChatMessage[]>([]);

  const answerInput = useRef<HTMLTextAreaElement | null>(null);

  // 웹소켓 연결
  const [sock, setSock] = useState<WebSocket>();
  const [webSocket, setWebSocket] = useState<Stomp.Client>();

  // redux 에서 가져오기
  const quizState = useSelector((state: any) => state.quiz.quiz);
  const quizId = quizState.quizId;
  const memberState = useSelector((state: any) => state.member.member);
  const memberId = memberState.memberId;
  const memberNickname = memberState.memberNickname;

  //문제리스트와 현재 문제 state
  const [problems, setProblems] = useState<ProblemMessage[]>([]);
  const [problem, setProblem] = useState<ProblemMessage>({
    quizId: -1,
    type: "PROBLEM",
    question: "문제를 기다리는 중",
    order: 0,
    selections: [],
  });

  //정답리스트와 현재 정답  state
  const [answers, setAnswers] = useState<Array<string>>([]);
  const [submits, setSubmits] = useState<Array<string>>([]);
  const [myScore, setMyScore] = useState<number>(0); // 맞은 개수
  const [thisAnswer, setThisAnswer] = useState<string>("");
  const [thisAnswerLength, setThisAnswerLength] = useState<number>(0);

  //결과 state
  const [memberList, setMemberList] = useState<Array<any>>([]);

  //모달
  const [showModal, setShowModal] = useState(false);
  const [modalType, setModalType] = useState("moment");

  /*
   * ===========================================================================
   * ================================소 켓 연 결================================
   */
  // componentdidmount
  useEffect(() => {
    const sockjs = new SockJS(`${process.env.REACT_APP_SPRING_URI}/ws/quiz`);
    const ws = Stomp.over(sockjs);

    ws.heartbeat.incoming = 0;
    ws.heartbeat.outgoing = 0;

    let reconnect = 0;

    const subscribeChannel = (frame?: Stomp.Frame | undefined) => {
      ws.subscribe("/sub/quiz/session/" + quizId, (message) => {
        // recv 콜백 함수

        console.log("메세지 받았다");
        const recvData = JSON.parse(message.body);
        console.log(recvData);
        messageHandler(recvData);
      });

      ws.send(
        "/pub/session/message/enter",
        {},
        JSON.stringify({
          type: "ENTER",
          quizId: quizId,
        })
      );

      setSock(sockjs);
      setWebSocket(ws);
    };

    ws.connect({}, subscribeChannel, (err) => {
      console.log(err);
      if (reconnect < 5) {
        setTimeout(() => {
          subscribeChannel();
        }, 1000);
      }
    });

    return () => {
      ws.send(
        "/pub/session/message/disconnect",
        {},
        JSON.stringify({
          type: "ENTER",
          quizId: quizId,
        })
      );
      ws?.disconnect(() => {
        console.log("socket disconnected");
      });
      sockjs?.close();
    };
  }, []);

  // timer effect
  useEffect(() => {
    if (isQuizStarted && problem.quizId !== -1 && progress < 100) {
      const remainingTime = 20000 - progress * 100; // 20초를 ms로 계산
      const timeInterval = remainingTime / 200; // 0.1초 간격으로 나누기
      setTimeout(() => {
        console.log("타이머 돈다");
        setProgress((prev) => prev + 0.5);
      }, timeInterval);
    }
  }, [problem, progress, isQuizStarted]);

  // 정답 제출
  useEffect(() => {
    if (submitted) {
      sendSubmit(textareaValue);
      setIsTextareaEnabled(false); //textarea 비활성화
    }
  }, [submitted]);

  // 정답 입력
  useEffect(() => {
    if (thisAnswer) {
      setAnswers((prevAnswers) => [...prevAnswers, thisAnswer]);
      setSubmits((prevSubmits) => [...prevSubmits, textareaValue]);
    }
  }, [thisAnswer]);

  //
  useEffect(() => {
    // 모달창이 켜질때면 지금까지 제출했던 답과 answer을 비교하여 Score에 저장
    if (showModal) {
      let score: number = 0;
      console.log(answers);
      console.log(submits);
      answers.forEach((answer, idx) => {
        score += answer === submits[idx] ? 1 : 0;
        console.log("score = ", score);
      });
      setMyScore(score);
    }
  }, [showModal]);

  const handleTextareaChange = (
    event: React.ChangeEvent<HTMLTextAreaElement>
  ) => {
    setTextareaValue(event.target.value);
  };

  const startQuiz = () => {
    setIsQuizStarted(true);
  };

  const toggleContent = () => {
    setShowModalContent(true);
  };

  const openModal = (modalType: string) => {
    setModalType(modalType);
    setShowModal(true);
  };

  // "PROBLEM" 메시지를 받았을 때 문제를 state에 추가
  const addProblem = (message: ProblemMessage) => {
    setProblem(message);
    setProblems((prevProblems) => [...prevProblems, message]);
  };

  // 메세지 받았을 시 컨트롤 함수
  const messageHandler = (recv: any) => {
    switch (recv.type) {
      case "CHAT":
        setMessageList((prev) => [...prev, recv]);
        return;
      case "START":
        startQuiz();
        return;

      case "PROBLEM":
        console.log("문제 받은 시각 : ", new Date());
        addProblem(recv);
        setThisAnswerLength(recv.answerLength);
        setIsTextareaEnabled(true);
        setSubmitted(false);
        setTextareaValue("");
        setProgress(0);
        setThisAnswer("");
        // setShowModal(false);
        return;

      case "ANSWER":
        console.log("정답 받은 시각 : ", new Date());
        setThisAnswer(recv.answer);
        setIsTextareaEnabled(false);
        setSubmitted(true);
        // openModal("moment");
        return;

      case "END":
        // 모든 제출 정답에 대해 총 점수 계산해서 점수를 state 에 저장
        // 계산만 해놓고 기다리기 모달 띄우기
        openModal("result");
        return;

      case "RESULT":
        // 결과를 받아와서 띄우기
        // 내 총점도 띄우기
        // 모든 처리 완료 하면
        toggleContent();
        setMemberList(recv.gradingResultPresentResponseList);

        webSocket?.disconnect(() => {});
        return;

      default:
      // 이건 와서는 안됨. 에러 처리
    }
  };

  // 답안 제출 웹소켓 전송
  const sendSubmit = (data: any) => {
    webSocket?.send(
      "/pub/session/message/submit",
      {},
      JSON.stringify({
        quizId: quizId,
        type: "SUBMIT",
        problemOrder: problem?.order,
        memberId: memberId,
        submitContent: data,
      })
    );
  };

  const sendChat = (data: string) => {
    webSocket?.send(
      "/pub/session/message/chat",
      {},
      JSON.stringify({
        quizId: quizId,
        type: "CHAT",
        memberId: memberId,
        message: memberNickname + ":" + data,
      })
    );
  };

  return (
    <div className="container">
      <img className="live-logo-img" src="/img/live_logo.png" alt="" />
      <div className="head">SSA피드 퀴즈</div>

      <div className="form">
        <div className="form-group">
          <div className="quiz-container">
            <div className="quiz">
              {isQuizStarted ? (
                <div className="quiz-content">{problem?.question}</div>
              ) : (
                <ChatComponent
                  memberId={memberId}
                  messageList={messageList}
                  socketSend={sendChat}
                />
              )}
            </div>
          </div>

          <div>
            {isQuizStarted ? (
              <div className="answer-box" style={{ display: "flex" }}>
                {Array.from({
                  length: thisAnswerLength ? thisAnswerLength : 0,
                }).map((_, index) => (
                  <div key={index} className="square">
                    {thisAnswer ? thisAnswer[index] : null}
                  </div>
                ))}
              </div>
            ) : (
              <div></div>
            )}
          </div>
          {isQuizStarted ? (
            // questions[currentQuestion].question

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
                  onClick={() => setSubmitted(true)}
                  bgcolor={submitted ? "#265587" : undefined}
                  bghover="#265587"
                  disabled={submitted}
                >
                  {submitted ? "제출 완료" : "제출"}
                </RoundCornerBtn>
              </div>
            </div>
          ) : (
            <div>
              {/* <textarea
                                placeholder="퀴즈가 곧 시작합니다!"
                                disabled
                            /> */}
              <div className="bottom_notice">🧩퀴즈가 곧 시작합니다!🧩</div>
              <div>
                <TextTimer targetHour={targetHour} targetMin={targetMin} />
              </div>
              <div className="notice">
                <br />
                <br />
                📌각 문제는 20초 동안 풀 수있는 스피드 퀴즈입니다
                <br />
                📌각 문제가 끝난후, 정답을 확인 할 수 있습니다
                <br />
                📌동점일 경우 제출한 순서대로 순위가 산정됩니다
                <br />
                📌모든 정답은 한글로 제출해주세요
                <br />
              </div>
            </div>
          )}

          <ProgressBar progress={progress} />
        </div>
      </div>

      <Modal
        showModal={showModal}
        showContent={showModalContent}
        toggleContent={toggleContent}
        memberList={memberList}
        myScore={myScore}
        totalProblemLength={problems.length}
        modalType={modalType}
      />
    </div>
  );
};

export default Quiz;
