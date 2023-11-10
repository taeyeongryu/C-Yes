import React from "react";
import BottomNav from "../../components/bottomnav/BottomNav";
import Dropdown from "../../components/dropdown/Dropdown";
import RoundCornerBtn from "../../components/RoundCornerBtn";
import "./GroupQuiz.css";
import { useEffect, useState } from "react";
type Props = {};

const GroupQuiz = (props: Props) => {
  const [view, setView] = useState(false);
  const items = ["항목 1", "항목 2", "항목 3"];
  const [number, setNumber] = useState<number>(0);

  const handleIncrement = () => {
    setNumber(number + 1);
  };

  const handleDecrement = () => {
    if (number > 0) {
      setNumber(number - 1);
    }
  };

  const handleInputChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    const input = event.target.value;
    if (!isNaN(Number(input))) {
      setNumber(Number(input));
    }
  };
  return (
    <div className="live-container">
      <div className="content">
        <div className="group-content">
          <div className="group-title">그룹 퀴즈 생성 페이지</div>
        </div>
        <div>
          <div className="dropdown-content">
            <div>퀴즈이름</div>
            <div>
              <textarea className="textarea" name="" id=""></textarea>
            </div>
          </div>
          <div className="dropdown-content">
            <div>문제유형</div>
            <div>
              <Dropdown items={items} />
            </div>
          </div>

          <div className="dropdown-content">
            <div>퀴즈과목</div>
            <div>
              <Dropdown items={items} />
            </div>
          </div>
          <div className="dropdown-content">
            <div>문제 갯수</div>
            <div>
              <textarea
                className="textarea"
                value={number}
                onChange={handleInputChange}
              />
            </div>
          </div>

          <br />
          <br />
          <RoundCornerBtn>취소</RoundCornerBtn>
          <RoundCornerBtn>다음</RoundCornerBtn>
        </div>
      </div>
      <BottomNav checkCS={false} checkLive={false} checkGroup={true} />
    </div>
  );
};
export default GroupQuiz;
