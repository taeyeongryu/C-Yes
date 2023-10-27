import React, { useState, useEffect, useRef } from "react";
import EndQuiz from "./EndQuiz";
import "./Quiz.css";
import RoundCornerBtn from "../../components/RoundCornerBtn";
import { useNavigate } from "react-router-dom";
import SockJS from "sockjs-client";
import Stomp from "stompjs";

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
    // const [answerInput, setAnswerInput] = useState("");
    const answerInput = useRef<HTMLTextAreaElement | null>(null);
    //
    const [isTextareaEnabled, setIsTextareaEnabled] = useState(true);
    const [textareaValue, setTextareaValue] = useState("");

    const handleTextareaChange = (
        event: React.ChangeEvent<HTMLTextAreaElement>
    ) => {
        setTextareaValue(event.target.value);
    };

    const toggleTextarea = () => {
        setIsTextareaEnabled(!isTextareaEnabled);
    };

    const toggleSubmit = () => {
        //여기서 backend랑 통신하면 댈듯
        if (!submitted) {
            setSubmitted(true); // 제출 완료 상태로 설정
            setIsTextareaEnabled(false); //textarea 비활성화
        }
    };

    // 웹소켓 연결
    const [webSocket, setWebSocket] = useState<Stomp.Client>();
    let sessionId: string;
    let memberId: number;
    let memberNickname: string;

    useEffect(() => {
        // 리덕스 연결해서 정보 가져오면 지워버리세요
        sessionId = "0";
        memberId = 1;
        memberNickname = "고멤";

        // const ws = new Client({
        //     brokerURL: `ws://localhost:5000/quiz/session`,
        //     debug(str) {
        //         console.log(`debug`, str);
        //     },
        //     reconnectDelay: 5000,
        //     connectionTimeout: 100000,
        //     heartbeatIncoming: 4000,
        //     heartbeatOutgoing: 4000,
        // });

        // ws.onConnect = (frame) => {
        //     ws.subscribe("/sub/quiz/session/" + sessionId, (message) => {
        //         // recv 콜백 함수
        //         const recvData = JSON.parse(message.body);
        //         console.log(recvData);
        //         //TODO: 메세지 타입별로 처리
        //     });

        //     ws.publish({
        //         destination: "/pub/session/message",
        //         body: JSON.stringify({
        //             type: "ENTER",
        //             sessionId: sessionId,
        //             senderId: memberId,
        //             senderNickname: memberNickname,
        //             message: "안녕하세요?",
        //         }),
        //     });
        // };

        // ws.onStompError = (frame) => {
        //     console.log(`Broker reported Error`, frame.headers.message);
        //     console.log(`Additional details:${frame.body}`);
        // };

        // ws.activate();

        // setWebSocket(ws);

        const sock = new SockJS(`http://localhost:5000/quiz/session`);
        const ws = Stomp.over(sock);

        ws.connect(
            {},
            (frame) => {
                ws.subscribe("/sub/quiz/session/" + sessionId, (message) => {
                    // recv 콜백 함수
                    console.log("메세지 받았다");
                    const recvData = JSON.parse(message.body);
                    console.log(recvData);
                    //TODO: 메세지 타입별로 처리
                });
                ws.send(
                    "/pub/session/message/submit",
                    {},
                    JSON.stringify({
                        type: "ENTER",
                        sessionId: sessionId,
                        senderId: memberId,
                        senderNickname: memberNickname,
                        message: "안녕하세요?",
                    })
                );
            },
            (err) => {
                console.log(err);
            }
        );
    }, []);

    useEffect(() => {
        const timer = setInterval(() => {
            if (progress >= 100) {
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
                            <div
                                className="answer-box"
                                style={{ display: "flex" }}
                            >
                                {Array.from({
                                    length: questions[currentQuestion].answer
                                        .length,
                                }).map((_, index) => (
                                    <div key={index} className="square">
                                        {showConfirmation
                                            ? questions[currentQuestion].answer[
                                                  index
                                              ]
                                            : null}
                                    </div>
                                ))}
                            </div>
                        </div>

                        <div>
                            <textarea
                                ref={answerInput}
                                id="answer-input"
                                name="content"
                                value={textareaValue}
                                onChange={handleTextareaChange}
                                disabled={!isTextareaEnabled} // 비활성화 상태 조절
                                style={{
                                    backgroundColor: isTextareaEnabled
                                        ? "white"
                                        : "lightgray", // 배경색 제어
                                    color: isTextareaEnabled ? "black" : "gray", // 텍스트 색상 제어
                                }}
                                placeholder={
                                    isTextareaEnabled ? "입력하세요" : " "
                                } // placeholder 설정
                            />
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
