import React, { useState, useEffect } from "react";
import "./TorfStudy.css";
import RoundCornerBtn from "../../../components/RoundCornerBtn";
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
  const [submit, setSubmit] = useState<string>();

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
      { keyword: "T", description: "유혜빈은 도토리이다." },
      { keyword: "F", description: "우수인은 외계인이다." },
    ];
    setKeywords(mockKeywords);
  }, []);

  const [modalMessage, setModalMessage] = useState<string | null>(null);

  const openModal = (message: string) => {
    setModalMessage(message);
  };

  const closeModal = () => {
    setModalMessage(null);
    setSubmit("");
  };

  useEffect(() => {
    if (submit === "T") {
      if (keywords[currentIndex]?.keyword === "T") {
        openModal("정답입니다!");
      } else {
        openModal("오답입니다!");
      }
    } else if (submit === "F") {
      if (keywords[currentIndex]?.keyword === "F") {
        openModal("정답입니다!");
      } else {
        openModal("오답입니다!");
      }
    }
  }, [submit, currentIndex, keywords]);

  return (
    <div>
      <div className="cs-title-container">
        <div className="x-image-container">
          <IconButton onClick={goCsPage} iconUrl="/icon/close.png" />
        </div>
        <div className="title-text-container">T or F</div>
      </div>

      <div>
        <div className="card-tf">
          <div className="card-tf-content">
            {keywords[currentIndex]?.description}
          </div>
        </div>
      </div>
      <div className="submit-button-content">
        <RoundCornerBtn
          onClick={() => setSubmit("T")}
          bgcolor="#57FF5E"
          bghover="#39A63D"
          height="200px"
          width="100px"
          fontSize="40px"
        >
          T
        </RoundCornerBtn>
        <div className="box"></div>
        <RoundCornerBtn
          onClick={() => setSubmit("F")}
          bgcolor="#FF2A2A"
          bghover="#A61B1B"
          height="200px"
          width="100px"
          fontSize="40px"
        >
          F
        </RoundCornerBtn>
      </div>

      <div className="bottom-next">
        <IconButton onClick={prevStudy} iconUrl="/icon/left-arrow.png" />
        {currentIndex + 1}/{keywords.length}
        <IconButton onClick={nextStudy} iconUrl="/icon/right-arrow.png" />
      </div>
      {modalMessage && (
        <div className="modal-tf">
          <div className="modal-content-tf">
            <p>{modalMessage}</p>
            <RoundCornerBtn onClick={closeModal} fontSize="15px">
              확인
            </RoundCornerBtn>
          </div>
        </div>
      )}
    </div>
  );
};

export default TorfStudy;
