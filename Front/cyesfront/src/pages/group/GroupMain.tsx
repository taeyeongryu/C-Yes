import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./GroupMain.css";
import BottomNav from "../../components/bottomnav/BottomNav";
import { getGroupQuiz } from "../../api/Group";
import FloatingButton from "../../components/FloatingButton";

type Props = {};

interface QuizInfo {
  quizId: number;
  quizTitle: string;
  quizStartDate: string;
}

const GroupMain = (props: Props) => {
  const [quizzes, setQuizzes] = useState<QuizInfo[]>([]);
  const navigate = useNavigate();
  const [currentTime, setCurrentTime] = useState(
    new Date().toLocaleTimeString()
  );

  const handleButtonClick = () => {
    navigate("/group/create");
  };

  useEffect(() => {
    const fetchQuizData = async () => {
      const data = await getGroupQuiz(); // API 호출
      if (data) {
        setQuizzes(data);
      }
    };

    fetchQuizData();
  }, []);

  return (
    <div className="">
      <div className="group-main-container">
        <div className="group-title-container">그룹퀴즈</div>
        <div className="group-description-container">
          <span>그룹을 결성해 퀴즈를 만들고{"\n"}</span>
          <span>방에 접속해 라이브 퀴즈를 풀어보아요!</span>
        </div>
        <div className="group-search-container">
          <img src="/icon/search.png" alt=""></img>
          <input placeholder="퀴즈 방 검색"></input>
        </div>
        <div className="fast-quiz-container">
          <div className="quiz-text-container">빠른 퀴즈</div>
          <div className="fast-quiz-lineup-container">
            {quizzes.length === 0 && (
              <div className="fast-quiz-box">퀴즈가 없습니다!</div>
            )}
            {quizzes.length > 0 && (
              <div className="fast-quiz-box">
                <div className="quiz-box-title">{quizzes[0].quizTitle}</div>
                <div className="quiz-box-text">{quizzes[0].quizTitle}</div>
                <div className="quiz-box-text">
                  문제수: {quizzes[0].quizTitle}
                </div>
                <div className="quiz-box-text">
                  출제자: {quizzes[0].quizTitle}
                </div>
                <div className="quiz-box-text">{quizzes[0].quizTitle}</div>
              </div>
            )}
            {quizzes.length > 1 && (
              <div className="fast-quiz-box">
                <div className="quiz-box-title">{quizzes[1].quizTitle}</div>
                <div className="quiz-box-text">{quizzes[1].quizTitle}</div>
                <div className="quiz-box-text">
                  문제수: {quizzes[1].quizTitle}
                </div>
                <div className="quiz-box-text">
                  출제자: {quizzes[1].quizTitle}
                </div>
                <div className="quiz-box-text">{quizzes[1].quizTitle}</div>
              </div>
            )}
          </div>
          <div className="group-result-container">
            <div className="quiz-text-container">검색된 퀴즈</div>
            <div className="result-quiz-box">
              {quizzes.length > 0 ? (
                quizzes.map((quiz, index) => (
                  <div key={index} className="each-result-quiz-box">
                    <div className="search-box-title">{quiz.quizTitle}</div>
                    <div className="search-box-text">
                      시작 시간: {new Date(quiz.quizStartDate).toLocaleString()}
                    </div>
                  </div>
                ))
              ) : (
                <div className="no-quiz-message">검색된 퀴즈가 없습니다!</div>
              )}
            </div>
          </div>
        </div>
        <div>
          <FloatingButton onClick={handleButtonClick} />
        </div>
      </div>
      <div className="bottom-nav">
        <BottomNav checkCS={false} checkLive={false} checkGroup={true} />
      </div>
    </div>
  );
};

export default GroupMain;
