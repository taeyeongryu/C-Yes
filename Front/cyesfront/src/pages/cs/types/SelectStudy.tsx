import React, { useState, useEffect } from "react";
import "./SelectStudy.css";
import "./Common.css";
import IconButton from "../../../components/button/IconButton";
import { useNavigate, useLocation } from "react-router-dom";

type SelectStudyProps = {};

interface QuestionContent {
  answer: string;
  choices: string[];
  description: string;
  id: string;
  problemOrder: number;
  question: string;
  type: string;
}

const SelectStudy = (props: SelectStudyProps) => {
  const navigate = useNavigate();
  const location = useLocation();
  const [currentIndex, setCurrentIndex] = useState(0);
  const [questions, setQuestions] = useState<QuestionContent[]>([]);

  const goCsPage = () => {
    navigate("/cs");
  };

  const prevStudy = () => {
    if (currentIndex > 0) {
      setCurrentIndex((prevIndex) => prevIndex - 1);
    }
  };

  const nextStudy = () => {
    if (currentIndex < questions.length - 1) {
      setCurrentIndex((prevIndex) => prevIndex + 1);
    }
  };

  useEffect(() => {
    if (location.state && location.state.questions) {
      console.log("setQuestions: ", location.state.questions);
      setQuestions(location.state.questions);
    }
  }, [location]);

  return (
    <div>
      <div className="cs-title-container">
        <div className="x-image-container">
          <IconButton onClick={goCsPage} iconUrl="/icon/close.png" />
        </div>
        <div className="title-text-container">객관식</div>
      </div>

      <div className="question-container">
        {/* 현재 질문 내용을 출력합니다. */}
        <div className="question-content">
          {questions[currentIndex]?.question || "해당 문제가 없습니다."}
        </div>
      </div>

      <div className="answer-container">
        <div className="answer-one-two">{/* 안에 객관식 가로 1,2 추가 */}</div>
        <div className="answer-three-four">
          {/* 안에 객관식 가로 3,4 추가 */}
        </div>
      </div>

      <div className="bottom-next">
        <div className="left-arrow-container">
          <IconButton onClick={prevStudy} iconUrl="/icon/left-arrow.png" />
        </div>
        <div className="page-arrow-container">
          {currentIndex + 1}/{questions.length}
        </div>
        <div className="right-arrow-container">
          <IconButton onClick={nextStudy} iconUrl="/icon/right-arrow.png" />
        </div>
      </div>
    </div>
  );
};
export default SelectStudy;