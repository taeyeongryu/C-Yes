import React, { useState, useEffect } from "react";
import "./TorfStudy.css";
import "./Common.css";
import IconButton from "../../../components/button/IconButton";
import { useNavigate } from "react-router-dom";
type Props = {};
type Keyword = {
  keyword: string;
  description: string;
};

const TorfStudy = (props: Props) => {
  const navigate = useNavigate();
  const [currentIndex, setCurrentIndex] = useState(0);
  const [keywords, setKeywords] = useState<Keyword[]>([]);
  const goCsPage = () => {
    navigate("/cs");
  };
  const prevStudy = () => {
    if (currentIndex > 0) {
      setCurrentIndex((prevIndex) => prevIndex - 1);
    }
  };

  const nextStudy = () => {
    if (currentIndex < keywords.length - 1) {
      setCurrentIndex((prevIndex) => prevIndex + 1);
    }
  };
  useEffect(() => {
    const mockKeywords: Keyword[] = [
      { keyword: "React", description: "React는 JavaScript 라이브러리입니다." },
      { keyword: "CSS", description: "CSS는 스타일 시트 언어입니다." },
    ];
    setKeywords(mockKeywords);
  }, []);

  return (
    <div>
      <div className="cs-title-container">
        <div className="x-image-container">
          <IconButton onClick={goCsPage} iconUrl="/icon/close.png" />
        </div>
        <div className="title-text-container">객관식</div>
      </div>

      {/* <div className="card-component">카드</div> */}

      <div>
        <div className="card-tf">
          {/* <div className="card-front">{keywords[currentIndex]?.keyword}</div> */}
        </div>
      </div>

      <div className="bottom-next">
        <IconButton onClick={prevStudy} iconUrl="/icon/left-arrow.png" />
        {currentIndex + 1}/{keywords.length}
        <IconButton onClick={nextStudy} iconUrl="/icon/right-arrow.png" />
      </div>
    </div>
  );
};

export default TorfStudy;
