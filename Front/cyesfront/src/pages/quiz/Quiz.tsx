import React, { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import RoundCornerBtn from "../../components/RoundCornerBtn";
import "./Quiz.css";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { useSelector } from "react-redux";

interface ModalProps {
    showModal: boolean;
    showContent: boolean;
    toggleContent: () => void;
    memberList: Array<any>;
    myScore?: number;
    totalProblemLength?: number;
}

function Modal(props: ModalProps) {
    const {
        showModal,
        showContent,
        toggleContent,
        memberList,
        myScore,
        totalProblemLength,
    } = props;
    const navigate = useNavigate();

    const moveMain = () => {
        //메인페이지 이동
        navigate("/live");
    };

    if (!showModal) {
        return null;
    }

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
                                <div>
                                    {memberList.map((member, index) => (
                                        <div key={index}>
                                            {index + 1}위: {member.nickName}
                                        </div>
                                    ))}
                                </div>
                                내 점수 : {myScore} / {totalProblemLength}
                            </div>

                            <RoundCornerBtn
                                type="submit"
                                onClick={moveMain}
                                bghover="black"
                            >
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
                            <img
                                src="/img/loading.gif"
                                alt="로딩 중"
                                width={60}
                            ></img>
                        </div>
                    )}
                </div>
            </div>
        )
    );
}

const Quiz: React.FC = () => {
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

    const [progress, setProgress] = useState(0);
    const [submitted, setSubmitted] = useState(false);
    const [isTextareaEnabled, setIsTextareaEnabled] = useState(true);
    const [textareaValue, setTextareaValue] = useState("");
    const [showModalContent, setShowModalContent] = useState(false);
    const [isQuizStarted, setIsQuizStarted] = useState(false);
    const answerInput = useRef<HTMLTextAreaElement | null>(null);

    // 웹소켓 연결
    const [sock, setSock] = useState<WebSocket>();
    const [webSocket, setWebSocket] = useState<Stomp.Client>();

    // redux 에서 가져오기
    const quizState = useSelector((state: any) => state.quiz.quiz);
    const quizId = quizState.quizId;
    const memberState = useSelector((state: any) => state.member.member);
    const memberId = memberState.memberId;

    //문제리스트와 현재 문제 state
    const [problems, setProblems] = useState<ProblemMessage[]>([]);
    const [problem, setProblem] = useState<ProblemMessage>({
        quizId: 0,
        type: "PROBLEM",
        question: "문제를 기다리는 중",
        order: 0,
        selections: [],
    });

    //정답리스트와 현재 정답  state
    const [answers, setAnswers] = useState<Array<string>>([]);
    const [submits, setSubmits] = useState<Array<string>>([]);
    const [myScore, setMyScore] = useState<number>(0);
    const [thisAnswer, setThisAnswer] = useState<string>("");
    const [thisAnswerLength, setThisAnswerLength] = useState<number>(0);

    //결과 state
    const [memberList, setMemberList] = useState<Array<any>>([]);

    //모달
    const [showModal, setShowModal] = useState(false);

    // componentdidmount
    useEffect(() => {
        const sockjs = new SockJS(
            `${process.env.REACT_APP_SPRING_URI}/ws/quiz`
        );
        const ws = Stomp.over(sockjs);

        let reconnect = 0;

        ws.connect(
            {},
            (frame) => {
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
                        quizId: quizId.current,
                    })
                );
                setSock(sockjs);
                setWebSocket(ws);
            },
            (err) => {
                console.log(err);
                if (reconnect < 5) {
                    setTimeout(() => {});
                }
            }
        );

        return () => {
            webSocket?.disconnect(() => {
                console.log("socket disconnected");
            });
            sock?.close();
        };
    }, []);

    // timer effect
    useEffect(() => {
        if (isQuizStarted && progress < 100) {
            setTimeout(() => {
                console.log("타이머 돈다");
                setProgress((prev) => prev + 0.5);
            }, 100);
        }
    }, [progress, isQuizStarted]);

    useEffect(() => {
        if (submitted) {
            sendSubmit(textareaValue);

            setIsTextareaEnabled(false); //textarea 비활성화
        } else {
            setIsTextareaEnabled(true);
        }
    }, [submitted]);

    useEffect(() => {
        if (thisAnswer) {
            setAnswers((prevAnswers) => [...prevAnswers, thisAnswer]);
            setSubmits((prevSubmits) => [...prevSubmits, textareaValue]);
        }
    }, [thisAnswer]);

    useEffect(() => {
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

    const openModal = () => {
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
            case "START":
                startQuiz();
                return;

            case "PROBLEM":
                addProblem(recv);
                setThisAnswerLength(recv.answerLength);
                setSubmitted(false);
                setTextareaValue("");
                setProgress(0);
                setThisAnswer("");
                return;

            case "ANSWER":
                setThisAnswer(recv.answer);
                return;

            case "END":
                // 모든 제출 정답에 대해 총 점수 계산해서 점수를 state 에 저장

                // 계산만 해놓고 기다리기 모달 띄우기
                openModal();
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
                                    problem?.question
                                ) : (
                                    <div>
                                        <div>타이머 띄우기</div>
                                    </div>
                                )}
                            </div>
                        </div>
                    </div>

                    <div>
                        {isQuizStarted ? (
                            <div
                                className="answer-box"
                                style={{ display: "flex" }}
                            >
                                {Array.from({
                                    length: thisAnswerLength
                                        ? thisAnswerLength
                                        : 0,
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
                                        backgroundColor: isTextareaEnabled
                                            ? "white"
                                            : "lightgray", // 배경색 제어
                                        color: isTextareaEnabled
                                            ? "black"
                                            : "gray", // 텍스트 색상 제어
                                    }}
                                    placeholder={
                                        isTextareaEnabled ? "입력하세요" : " "
                                    } // placeholder 설정
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
                            <textarea
                                placeholder="퀴즈가 곧 시작합니다!"
                                disabled
                            />
                        </div>
                    )}
                </div>

                <div>
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
