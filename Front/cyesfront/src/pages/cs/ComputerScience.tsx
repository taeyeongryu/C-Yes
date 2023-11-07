import React, { useState } from "react";
import BottomNav from "../../components/bottomnav/BottomNav";

import "./ComputerScience.css";
import SelectTitleModal from "../../components/dropdownmodal/SelectTitleModal";
type Props = {};

const ComputerScience = (props: Props) => {
  const [isModalOpen, setModalOpen] = useState(false);

  const openModal = () => setModalOpen(true);
  const closeModal = () => setModalOpen(false);

  return (
    <div className="live-container">
      <div className="content">
        ComputerScience
        <button onClick={openModal}>Open Dropdown Modal</button>
        <SelectTitleModal isOpen={isModalOpen} onClose={closeModal} />
      </div>

      <BottomNav checkCS={true} checkLive={false} checkGroup={false} />
    </div>
  );
};

export default ComputerScience;
