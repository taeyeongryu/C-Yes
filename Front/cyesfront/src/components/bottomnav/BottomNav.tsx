import React, { useState } from 'react';
import './BottomNav.css';

interface BottomNavProps {
  onSelectionChange?: (selection: string) => void;
}

const BottomNav: React.FC<BottomNavProps> = ({ onSelectionChange }) => {
  const [selected, setSelected] = useState<string | null>(null);

  const handleClick = (item: string) => {
    setSelected(item);
    if (onSelectionChange) {
      onSelectionChange(item);
    }
  };

  return (
    <div className="bottom-nav-container">
      <button className={`button-cs ${selected === 'cs공부' ? 'selected' : ''}`} onClick={() => handleClick('cs공부')}>cs공부</button>
      <button className={`button-live ${selected === '라이브' ? 'selected' : ''}`} onClick={() => handleClick('라이브')}>라이브</button>
      <button className={`button-group ${selected === '그룹' ? 'selected' : ''}`} onClick={() => handleClick('그룹')}>그룹</button>
    </div>
  );
};

export default BottomNav;
