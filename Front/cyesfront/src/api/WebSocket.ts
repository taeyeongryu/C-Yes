import SockJS from "sockjs-client";
import Stomp from "stompjs";

interface SessionMessage {
    quizId: number;
    type: string;
}

interface ChatMessage extends SessionMessage {
    memberId: number;
    message: string;
}

interface ProblemMessage extends SessionMessage {
    question: string;
    order: number;
    answerLength: number;
    selection: string[];
}

interface SubmitMessage extends SessionMessage {
    memberId: number;
    problemOrder: number;
    submitContent: string;
}

interface AnswerMessage extends SessionMessage {
    answer: string;
    correctNumber: number;
    totalNumber: number;
}

interface ResultMessage extends SessionMessage {
    gradingResultPresentResponseList: {
        memberId: number;
        nickName: string;
        correctProblemCount: number;
    }[];
}

class QuizWebSocket {
    socket: WebSocket;
    client: Stomp.Client;
    quizId: number;
    reconnect: number = 0;
    onChat: Function = () => {};
    onStart: Function = () => {};
    onProblem: Function = () => {};
    onAnswer: Function = () => {};
    onEnd: Function = () => {};
    onResult: Function = () => {};

    constructor(quizId: number) {
        this.socket = new SockJS(`${process.env.REACT_APP_SPRING_URI}/ws/quiz`);
        this.client = Stomp.over(this.socket);
        this.client.heartbeat.incoming = 0;
        this.client.heartbeat.incoming = 0;
        this.quizId = quizId;
    }

    connect = () => {
        this.client.connect({}, this.subscribe, (err) => {
            console.log(err);
            if (this.reconnect < 5) {
                this.reconnect++;
                setTimeout(() => {
                    this.connect();
                }, 1000);
            }
        });
    };

    private subscribe = () => {
        this.client.subscribe("/sub/quiz/session/" + this.quizId, (message) => {
            const recvData = JSON.parse(message.body);

            this.receiveHandler(recvData);
        });

        this.client.send(
            "/pub/session/message/enter",
            {},
            JSON.stringify({
                type: "ENTER",
                quizId: this.quizId,
            })
        );
    };

    private receiveHandler = (message: any) => {
        switch (message.type) {
            case "CHAT":
                this.onChat(message);
                return;

            case "START":
                this.onStart(message);
                return;

            case "PROBLEM":
                this.onProblem(message);
                return;

            case "ANSWER":
                this.onAnswer(message);
                return;

            case "END":
                this.onEnd(message);
                return;

            case "RESULT":
                this.onResult(message);
                return;

            default:
            // 이건 와서는 안됨. 에러 처리
        }
    };

    onChatCallback = (callback: (message: ChatMessage) => void) => {
        this.onChat = callback;
    };

    onStartCallback = (callback: (message: SessionMessage) => void) => {
        this.onStart = callback;
    };

    onProblemCallback = (callback: (message: ProblemMessage) => void) => {
        this.onProblem = callback;
    };

    onAnswerCallback = (callback: (message: AnswerMessage) => void) => {
        this.onAnswer = callback;
    };

    onEndCallback = (callback: (message: SessionMessage) => void) => {
        this.onEnd = callback;
    };

    onResultCallback = (callback: (message: ResultMessage) => void) => {
        this.onResult = callback;
    };

    sendSubmit = (data: SubmitMessage) => {
        this.client.send(
            "/pub/session/message/submit",
            {},
            JSON.stringify(data)
        );
    };

    sendChat = (data: ChatMessage) => {
        this.client.send("/pub/session/message/chat", {}, JSON.stringify(data));
    };
}

export default QuizWebSocket;
