import React, { useState, useEffect, useRef } from "react";
import "./SelectTitleModal.css"; // CSS 파일을 임포트하세요.

type SelectTitleProps = {
  isOpen: boolean;
  onClose: () => void;
};

const SelectTitleModal: React.FC<SelectTitleProps> = ({ isOpen, onClose }) => {
  // 카테고리 받아오는 axios (axios 로직은 여기에 추가)

  // 모달 외부 클릭 시 닫는 로직
  const modalRef = useRef<HTMLDivElement>(null);
  const [isDropdownActive, setDropdownActive] = useState(false);

  const toggleDropdown = () => {
    setDropdownActive(!isDropdownActive);
  };

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (
        modalRef.current &&
        !modalRef.current.contains(event.target as Node)
      ) {
        onClose();
      }
    };

    if (isOpen) {
      document.addEventListener("mousedown", handleClickOutside);
    }

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [isOpen, onClose]);

  if (!isOpen) return null;

  return (
    <>
      {isOpen && <div className="modal-overlay" onClick={onClose} />}
      <div ref={modalRef} className="modal-container">
        <div className="modal-content" onClick={toggleDropdown}>
          <ul className={isDropdownActive ? "active" : ""}>
            <li>Option 1</li>
            <li>Option 2</li>
            <li>Option 3</li>
          </ul>
        </div>
      </div>
    </>
  );
};

export default SelectTitleModal;
