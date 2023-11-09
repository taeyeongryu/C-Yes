import React, { useState, useEffect, useRef } from "react";
import RoundCornerBtn from "../../components/RoundCornerBtn";
import "./Quiz.css";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { useSelector } from "react-redux";
import ProgressBar from "../../components/ProgressBar";
import TextTimer from "../../components/TextTimer";
import { useLocation } from "react-router-dom";
import ChatComponent from "../../components/chat/ChatComponent";
import ModalContainer from "../../components/modal/ModalContainer";
import {
    ChatMessage,
    ProblemMessage,
} from "../../api/websocket/MessageInterface";
import NotifyBox from "./NotifyBox";
import SubmitShort from "./SubmitShort";
import QuizWebSocket from "../../api/websocket/QuizWebSocket";

const Quiz: React.FC = () => {
    const [progress, setProgress] = useState(0);
    const [submitted, setSubmitted] = useState(false);
    const [isTextareaEnabled, setIsTextareaEnabled] = useState(true);
    const [textareaValue, setTextareaValue] = useState("");
    const [showModalContent, setShowModalContent] = useState(false);
    const [isQuizStarted, setIsQuizStarted] = useState(false);
    const [messageList, setMessageList] = useState<ChatMessage[]>([]);

    // 웹소켓 연결
    const [socket, setSocket] = useState<QuizWebSocket>();

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
        answerLength: 0,
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
        const quizSocket = new QuizWebSocket(quizId);

        quizSocket.connect();

        setSocket(quizSocket);

        return () => {
            if (quizSocket.client.connected) {
                quizSocket.sendLeave();
            }

            quizSocket.disconnect();
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
            socket?.sendSubmit({
                quizId,
                type: "SUBMIT",
                memberId,
                problemOrder: problem.order,
                submitContent: textareaValue,
            });
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

    useEffect(() => {
        if (showModalContent) {
            socket?.disconnect();
        }
    }, [showModalContent]);

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
                console.log(recv);

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

                return;

            default:
            // 이건 와서는 안됨. 에러 처리
        }
    };

    return (
        <div className="container">
            <img className="live-logo-img" src="/img/live_logo.png" alt="" />
            <div className="head">SSA피드 퀴즈</div>

            <div className="form">
                <div className="form-group">
                    {/* Problem Container */}
                    <div className="quiz-container">
                        <div className="quiz">
                            {isQuizStarted ? (
                                <div className="quiz-content">
                                    {problem?.question}
                                </div>
                            ) : (
                                <ChatComponent
                                    quizId={quizId}
                                    memberId={memberId}
                                    memberNickname={memberNickname}
                                    messageList={messageList}
                                    socketSend={(message: ChatMessage) => {
                                        socket?.sendChat(message);
                                    }}
                                />
                            )}
                        </div>
                    </div>
                    {/* Problem Container End*/}

                    {/* Under Box */}
                    {isQuizStarted ? (
                        <SubmitShort
                            answer={thisAnswer}
                            answerLength={thisAnswerLength}
                            textareaValue={textareaValue}
                            isTextareaEnabled={isTextareaEnabled}
                            isSubmitted={submitted}
                            onSubmit={setSubmitted}
                            onTextAreaChanged={handleTextareaChange}
                        />
                    ) : (
                        <NotifyBox />
                    )}

                    <ProgressBar progress={progress} />
                </div>
            </div>

            <ModalContainer
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
