import React, { useEffect } from "react";
import BottomNav from "../../components/bottomnav/BottomNav";
import "./Live.css";
import CountdownTimer from "../../components/CountdownTimer";
import RoundCornerBtn from "../../components/RoundCornerBtn";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux"; 

type Props = {};

const Live = (props: Props) => {
  const navigate = useNavigate();
  const memberInfo = useSelector((state: any) => state.member);

  useEffect(() => {
    console.log("redux체크", memberInfo)
  })

  const enterRoom = () => {
    // 다른 페이지로 이동
    navigate("/quiz");
  };

  return (
    <div className="live-container">
      <div className="content">
        <div className="title-text">
          <p>SSAFY</p>
          <img src="/img/live_logo.png" alt=""></img>
        </div>
        <CountdownTimer targetHour={16} targetMin={0} />
        <p style={{ fontSize: '26px' }}>기술면접 대비 CS 퀴즈</p>
        <RoundCornerBtn
          width="150px"
          height="45px"
          bgColor="#FF5733"
          bgHover="#853828"
          fontSize="16px"
          fontColor="#FFFFFF"
          onClick={enterRoom}
        >
          대기실 입장
        </RoundCornerBtn>
      </div>
      <BottomNav checkCS={false} checkLive={true} checkGroup={false} />
    </div>
  );
};

export default Live;
