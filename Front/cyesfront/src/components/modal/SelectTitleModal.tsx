import React, { useState, useEffect } from "react";
import axios from "axios";
import "./SelectTitleModal.css";
import { getCsCategory } from "../../api/CsAPI";

interface SelectTitleModalProps {
  isOpen: boolean;
  close: () => void;
  type: string;
}

const SelectTitleModal: React.FC<SelectTitleModalProps> = ({
  isOpen,
  close,
  type
}) => {
  const [categories, setCategories] = useState<string[]>([]);
  const [selectedCategory, setSelectedCategory] = useState<string | null>(null);

  // 시작 버튼 함수
  // todo: 선택된 주제 probs로 넘기기
  const startStudy = () => {
    console.log("시작 버튼");
  };

  // 카테고리 선택 핸들러
  const handleCategorySelect = (category: string) => {
    setSelectedCategory(category);
  };

  // 서버로부터 카테고리 데이터를 가져오는 함수
  useEffect(() => {
    const fetchCategories = async () => {
      const data = await getCsCategory(); // API 호출
      if (data) {
        setCategories(data);
      }
    };

    fetchCategories();
  }, []);

  if (!isOpen) return null;

  return (
    <div className="modal-overlay" onClick={close}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <p>주제 선택</p>
        <div className="category-container">
          {categories.map((category, index) => (
            <div
              key={index}
              className={`category-item ${
                selectedCategory === category ? "selected-category" : ""
              }`}
              onClick={() => handleCategorySelect(category)}
            >
              {category}
            </div>
          ))}
        </div>
        <button className="st-btn" onClick={startStudy}>
          시작
        </button>
      </div>
    </div>
  );
};

export default SelectTitleModal;
