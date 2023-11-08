import React, { useState, useEffect } from "react";
import "./SelectStudy.css";
import "./Common.css";
import IconButton from "../../../components/button/IconButton";
import { useNavigate } from "react-router-dom";

type SelectStudyProps = {};

const SelectStudy = (props: SelectStudyProps) => {
  const navigate = useNavigate();

  const [currentIndex, setCurrentIndex] = useState(0);

  const goCsPage = () => {
    navigate("/cs");
  };

  const prevStudy = () => {
    if (currentIndex > 0) {
      setCurrentIndex((prevIndex) => prevIndex - 1);
    }
  };

  const nextStudy = () => {
    // if (currentIndex < keywords.length - 1) {
    //   setCurrentIndex((prevIndex) => prevIndex + 1);
    // }
  };

  useEffect(() => {}, []);

  return (
    <div>
      <div className="cs-title-container">
        <div className="x-image-container">
          <IconButton onClick={goCsPage} iconUrl="/icon/close.png" />
        </div>
        <div className="title-text-container">객관식</div>
      </div>

      {/* <div className="card-component">카드</div> */}

      <div className="bottom-next">
        <div className="left-arrow-container">
          <IconButton onClick={prevStudy} iconUrl="/icon/left-arrow.png" />
        </div>
        <div className="page-arrow-container">
          {/* {currentIndex + 1}/{keywords.length} */}
        </div>
        <div className="right-arrow-container">
          <IconButton onClick={nextStudy} iconUrl="/icon/right-arrow.png" />
        </div>
      </div>
    </div>
  );
};
export default SelectStudy;
