import React from "react";
import BottomNav from "../../components/bottomnav/BottomNav";

type Props = {};

const GroupQuiz = (props: Props) => {
  return (
    <div className="live-container">
      <div className="content">
        <div className="cs-text-container">Group 퀴즈</div>
      </div>
      <BottomNav checkCS={false} checkLive={false} checkGroup={true} />
    </div>
  );
};
export default GroupQuiz;
