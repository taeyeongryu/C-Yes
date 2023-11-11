import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import IconButton from "../../components/button/IconButton";
import Checkbox from "../../components/Checkbox";
import "../group/GroupQuizDetail.css";
import { createGroupQuiz } from "../../api/QuizCreate";

type Props = {};
type QuizInfo = {
  quizName: string;
  quizType: string;
  quizSubject: string;
  quizNumber: number;
  quizStartTime: string;
};

interface QuestionContent {
  answer: string;
  choices: string[];
  category: string;
  question: string;
  type: string;
}

// interface QuestionContent {
//   memberId: number;
//   quizTitle: string;
//   startDateTime: string;
//   problemByUserList: [
//     {
//       question: string;
//       answer: string;
//       choices: string[];
//       category: string;
//       type: string;
//     }
//   ];
// }

const GroupQuizDetail = (props: Props) => {
  const location = useLocation();
  const quizInfo = location.state as QuizInfo;
  const [currentIndex, setCurrentIndex] = useState(0);
  const [questions, setQuestions] = useState<QuestionContent[]>([]);
  const [currentQuestion, setCurrentQuestion] = useState<string>("");
  const [currentAnswer, setCurrentAnswer] = useState<string>("");
  const [submitted, setSubmitted] = useState<boolean>(false);
  const [currentChoices, setCurrentChoices] = useState<string[]>([]);

  const navigate = useNavigate();
  const handleTitleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCurrentQuestion(event.target.value);
  };

  const handleShortAnswerChange = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setCurrentAnswer(event.target.value);
  };

  const handleChioceAnswerChange = (index: number) => {
    // 업데이트된 정답 배열을 상태에 반영
    const stringIndex = index.toString();
    setCurrentAnswer(stringIndex);
  };

  const handleChoiceChange = (index: number, value: string) => {
    // 객관식 퀴즈의 경우 선택지를 배열에 저장
    const newChoices = [...currentChoices];
    newChoices[index] = value;
    setCurrentChoices(newChoices);

    //console.log(newChoices);
  };

  const handleTFChange = (value: string) => {
    setCurrentAnswer(value);
  };
  function determineQuestionType(quizType: string): string {
    switch (quizType) {
      case "객관식":
        return "MULTIPLECHOICE";
      case "TF퀴즈":
        return "TRUEORFALSE";
      case "단답형":
        return "SHORTANSWER";
      default:
        return "UNKNOWN";
    }
  }

  const handleNextButtonClick = () => {
    // 현재 입력한 문제와 정답을 배열에 추가
    const newQuestions = [
      ...questions,
      {
        answer: currentAnswer,
        choices: currentChoices, // 선택지가 있다면 해당 부분을 업데이트
        category: quizInfo.quizSubject, // 문제 설명이 있다면 해당 부분을 업데이트
        question: currentQuestion,
        type: determineQuestionType(quizInfo.quizType),
      },
    ];

    setCurrentIndex(currentIndex + 1);
    setQuestions(newQuestions);
    //setCurrentChoices(currentChoices);

    console.log("보낼 퀴즈 list: ", questions);

    setCurrentQuestion("");
    setCurrentAnswer("");
    setCurrentChoices([]);

    setSubmitted(false);
  };

  const handleSubmitButtonClick = async () => {
    setSubmitted(true);
    console.log("보낼 퀴즈 list: ", questions);
    const resultQuestions = {
      memberId: 20,
      quizTitle: "수빈 cs 퀴즈",
      startDateTime: "2023-11-11T06:49:22",
      problemByUserList: questions,
    };

    console.log("보내기:", resultQuestions);
    const result = await createGroupQuiz(resultQuestions);

    if (result) {
      console.log("퀴즈가 성공적으로 생성되었습니다.", result);
      navigate("/group");
    } else {
      console.log("퀴즈 생성에 실패했습니다.");
      alert("퀴즈 생성 실패");
      navigate("/group");
    }
  };

  useEffect(() => {
    console.log(quizInfo);
  }, []);
  useEffect(() => {
    console.log(questions);
  }, [questions]);

  return (
    <div className="live-container">
      <div className="content">
        <div className="group-content">
          <div className="group-title">문제 생성</div>
        </div>

        <div>
          <div>
            <div>주제 : {quizInfo.quizSubject}</div>
            <div className="dropdown-content">
              <div>문제</div>
              <div>
                <input
                  className="textarea"
                  value={currentQuestion}
                  onChange={handleTitleChange}
                />
              </div>
            </div>
            {quizInfo.quizType === "TF 퀴즈" && (
              <div className="dropdown-content">
                <div>정답</div>
                <div className="checkbox-content">
                  <Checkbox
                    label="T"
                    checked={currentAnswer.includes("T")}
                    onChange={() => handleTFChange("T")}
                  />
                  <Checkbox
                    label="F"
                    checked={currentAnswer.includes("F")}
                    onChange={() => handleTFChange("F")}
                  />
                </div>
              </div>
            )}
            {quizInfo.quizType === "객관식" && (
              <div
                style={{
                  display: "flex",
                  flexDirection: "row",
                  flexWrap: "wrap",
                }}
              >
                {[0, 1, 2, 3].map((index) => (
                  <div
                    key={index}
                    className="detail-select-content"
                    style={{ flexBasis: "30%" }}
                  >
                    <div style={{ display: "flex", alignItems: "center" }}>
                      <label>
                        <input
                          type="checkbox"
                          checked={currentAnswer.includes(index.toString())}
                          onChange={() => handleChioceAnswerChange(index)}
                        />
                        <span>{`선택지 ${index + 1}`}</span>
                      </label>
                    </div>
                    <div>
                      <input
                        className="textarea"
                        value={currentChoices[index] || ""}
                        onChange={(e) =>
                          handleChoiceChange(index, e.target.value)
                        }
                      />
                    </div>
                  </div>
                ))}
              </div>
            )}

            {quizInfo.quizType === "단답형" && (
              <div className="dropdown-content">
                <div>정답</div>
                <div>
                  <input
                    className="textarea"
                    value={currentAnswer}
                    onChange={handleShortAnswerChange}
                  />
                </div>
              </div>
            )}
          </div>
          <div className="bottom-next">
            {currentIndex + 1}/{quizInfo.quizNumber}
            {currentIndex === quizInfo.quizNumber - 1 && !submitted ? (
              <button onClick={handleSubmitButtonClick}>제출</button>
            ) : (
              <IconButton
                iconUrl="/icon/right-arrow.png"
                onClick={handleNextButtonClick}
              />
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default GroupQuizDetail;
