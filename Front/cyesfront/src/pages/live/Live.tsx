import React, { useEffect, useState } from "react";
import BottomNav from "../../components/bottomnav/BottomNav";
import "./Live.css";
import CountdownTimer from "../../components/CountdownTimer";
import RoundCornerBtn from "../../components/RoundCornerBtn";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { getMainQuizInfo } from "../../api/QuizAPI";
import { saveQuizId } from "../../redux/actions/QuizAction";

type Props = {};

type Quiz = {
    quizId: number;
    quizTitle: string;
    quizStartDate: Date;
};

const Live = (props: Props) => {
    const [mainQuiz, setMainQuiz] = useState<Quiz>({
        quizId: -1,
        quizTitle: "퀴즈 일정이 없습니다",
        quizStartDate: new Date(),
    });

    const [joinable, setJoinable] = useState<boolean>(false);

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const memberInfo = useSelector((state: any) => state.member);

    const getLiveInfo = async () => {
        const mainQuizInfo = await getMainQuizInfo();

        if (mainQuizInfo == null) {
            setMainQuiz((prev) => {
                return { ...prev, quizStartDate: new Date() };
            });
            return;
        }

        setMainQuiz({
            ...mainQuizInfo,
            quizStartDate: new Date(mainQuizInfo.quizStartDate),
        });
    };

    useEffect(() => {
        getLiveInfo();
        const requestMainQuizInterval = setInterval(() => {
            getLiveInfo();
        }, 60000);

        return () => {
            clearInterval(requestMainQuizInterval);
        };
    }, []);

    const enterRoom = () => {
        // 다른 페이지로 이동
        dispatch(saveQuizId(mainQuiz.quizId));

        navigate("/quiz");
    };

    return (
        <div className="live-container">
            <div className="content">
                <div className="title-text">
                    <p>SSAFY</p>
                    <img src="/img/live_logo.png" alt=""></img>
                </div>
                <CountdownTimer
                    targetHour={mainQuiz.quizStartDate.getHours()}
                    targetMin={mainQuiz.quizStartDate.getMinutes()}
                    setJoinable={setJoinable}
                />
                <p style={{ fontSize: "26px" }}>{mainQuiz.quizTitle}</p>
                <RoundCornerBtn
                    width="150px"
                    height="45px"
                    bgcolor={joinable ? "#FF5733" : "#868686"}
                    bghover="#853828"
                    fontSize="16px"
                    fontcolor="#FFFFFF"
                    onClick={enterRoom}
                    disabled={!joinable}
                >
                    대기실 입장
                </RoundCornerBtn>
            </div>
            <BottomNav checkCS={false} checkLive={true} checkGroup={false} />
        </div>
    );
};

export default Live;
