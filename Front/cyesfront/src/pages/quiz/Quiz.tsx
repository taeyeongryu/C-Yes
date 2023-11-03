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
    memberList: string[];
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
                                    {memberList.map((nickname, index) => (
                                        <div key={index}>
                                            {index + 1}위: {nickname}
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
                            <button onClick={toggleContent}>산정 완료</button>
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
    const [memberList, setMemberList] = useState<string[]>([]);

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

    // problem effect
    useEffect(() => {
        if (isQuizStarted) {
            setProgress(0);
            setThisAnswer("");
        }
    }, [problem]);

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

            sendSubmit(textareaValue);

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

    // "PROBLEM" 메시지를 받았을 때 문제를 state에 추가
    const addProblem = (message: ProblemMessage) => {
        setProblem(message);
        setProblems((prevProblems) => [...prevProblems, message]);
    };
    // "PROBLEM" 메시지를 받았을 때 문제를 state에 추가
    const addAnswer = (answer: string) => {
        setThisAnswer(answer);
        console.log("thisAnswer : " + thisAnswer);
        setAnswers((prevAnswers) => [...prevAnswers, answer]);
        setSubmits((prevSubmits) => [...prevSubmits, textareaValue]);
    };

    const calcMyScore = (): number => {
        let score: number = 0;

        answers.forEach((answer, idx) => {
            score += answer === submits[idx] ? 1 : 0;
        });

        return score;
    };

    // 메세지 받았을 시 컨트롤 함수
    const messageHandler = (recv: any) => {
        console.log("받은 msg", recv);

        switch (recv.type) {
            case "START":
                // 문제 받을 준비
                // 대기실 -> 문제페이지 입성
                startQuiz();
                return;

            case "PROBLEM":
                // 문제랑 답 숫자를 state에 저장
                addProblem(recv);
                setThisAnswerLength(recv.answerLength);
                // 문제 출력'
                return;

            case "ANSWER":
                // 답을 answer redux state에 저장

                // 내가 제출한 답 submit과, answer의 같은 인덱스를 비교해서 정답인지 출력
                // 정답 보냄

                //나중에 쓸 정답 리스트
                addAnswer(recv.answer);
                return;

            case "END":
                // 모든 제출 정답에 대해 총 점수 계산해서 점수를 state 에 저장
                // sendSubmit(`${answers}`);

                // 계산만 해놓고 기다리기 모달 띄우기
                setMyScore(calcMyScore());
                openModal();
                return;

            case "RESULT":
                // 결과를 받아와서 띄우기
                // 내 총점도 띄우기
                // 모든 처리 완료 하면
                toggleContent();
                setMemberList(recv.gradingResultPresentResponseList);

                console.log(recv.gradingResultPresentResponseList);

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
                                    onClick={() => toggleSubmit()}
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
