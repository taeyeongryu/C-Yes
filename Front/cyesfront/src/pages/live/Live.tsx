import React, { useEffect, useState, useRef } from "react";
import BottomNav from "../../components/bottomnav/BottomNav";
import "./Live.css";
import CountdownTimer from "../../components/CountdownTimer";
import RoundCornerBtn from "../../components/RoundCornerBtn";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { getMainQuizInfo } from "../../api/QuizAPI";
import { saveQuizId } from "../../redux/actions/QuizAction";
import { Quiz } from "../../redux/ReduxStateInterface";

type Props = {};

const Live = (props: Props) => {
    const defaultQuiz = {
        quizId: -1,
        quizTitle: "예정된 퀴즈 일정이 없습니다",
        quizStartDate: new Date(),
    };

    const [mainQuiz, setMainQuiz] = useState<Quiz>(defaultQuiz);

    const [joinable, setJoinable] = useState<boolean>(false);
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const memberInfo = useSelector((state: any) => state.member);
    const intervalRef = useRef<NodeJS.Timer | null>(null);

    const getLiveInfo = async () => {
        const mainQuizInfo = await getMainQuizInfo();
        console.log(mainQuizInfo);

        if (mainQuizInfo.quizId == -1) {
            setMainQuiz(defaultQuiz);
            return;
        }

        setMainQuiz({
            ...mainQuizInfo,
            quizStartDate: new Date(mainQuizInfo.quizStartDate),
        });
    };

    const enterRoom = () => {
        // 다른 페이지로 이동
        dispatch(saveQuizId(mainQuiz));
        // navigate("/quiz");
        const targetHour = mainQuiz.quizStartDate.getHours();
        const targetMin = mainQuiz.quizStartDate.getMinutes();
        navigate(`/quiz?targetHour=${targetHour}&targetMin=${targetMin}`);
    };

    useEffect(() => {
        // 첫 번째 정보 가져오기 시도.
        getLiveInfo();

        // 첫 번째 setTimeout을 설정합니다.
        const timeoutId = setTimeout(() => {
            getLiveInfo(); // 첫 번째 getLiveInfo 호출 후에 바로 정보를 가져옵니다.
            // 그 다음 매 분마다 정보를 가져오는 인터벌을 설정합니다.
            intervalRef.current = setInterval(getLiveInfo, 60000);
        }, (60 - new Date().getSeconds()) * 1000);

        // useEffect의 cleanup 함수입니다.
        return () => {
            clearTimeout(timeoutId); // setTimeout도 취소합니다.
            if (intervalRef.current) {
                clearInterval(intervalRef.current); // 현재 설정된 인터벌을 취소합니다.
            }
        };
    }, []);

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
                    bghover={joinable ? "#853828" : "#868686"}
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
