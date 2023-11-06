import React from "react";
import BottomNav from "../../components/bottomnav/BottomNav";

import "./ComputerScience.css";
type Props = {};

const ComputerScience = (props: Props) => {
  return (
    <div className="live-container">
      <div className="content">ComputerScience</div>

      <BottomNav checkCS={true} checkLive={false} checkGroup={false} />
    </div>
  );
};

export default ComputerScience;
