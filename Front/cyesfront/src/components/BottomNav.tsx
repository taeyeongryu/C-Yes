import React, { useState } from 'react';

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
    <div style={{ display: 'flex', justifyContent: 'space-around', padding: '10px', borderTop: '1px solid #ccc', position: 'fixed', bottom: 0, width: '100%', backgroundColor: '#f9f9f9' }}>
      <button onClick={() => handleClick('cs공부')} style={{ backgroundColor: selected === 'cs공부' ? '#ddd' : 'transparent' }}>cs공부</button>
      <button onClick={() => handleClick('라이브')} style={{ backgroundColor: selected === '라이브' ? '#ddd' : 'transparent' }}>라이브</button>
      <button onClick={() => handleClick('그룹')} style={{ backgroundColor: selected === '그룹' ? '#ddd' : 'transparent' }}>그룹</button>
    </div>
  );
};

export default BottomNav;
