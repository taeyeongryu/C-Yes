import { useNavigate } from "react-router-dom";
import LoadingModal from "../LoadingModal";
import RankingModal from "./RankingModal";
import MomentOfRank from "./MomentOfRank";

interface ModalProps {
    showModal: boolean;
    showContent: boolean;
    toggleContent: () => void;
    memberList: Array<any>;
    myScore?: number;
    totalProblemLength?: number;
    modalType: string;
}

const ModalContainer: React.FC<ModalProps> = (props: ModalProps) => {
    const {
        showModal,
        showContent,
        toggleContent,
        memberList,
        myScore,
        totalProblemLength,
        modalType,
    } = props;
    const navigate = useNavigate();

    const moveMain = () => {
        navigate("/live");
    };

    if (!showModal) {
        return null;
    }

    const renderModalContent = () => {
        if (!showContent) {
            return <LoadingModal />;
        }

        switch (modalType) {
            case "result":
                return (
                    <RankingModal
                        memberList={memberList}
                        myScore={myScore}
                        totalProblemLength={totalProblemLength}
                        onNavigate={moveMain}
                    />
                );
            case "moment":
                return (
                    <MomentOfRank
                    // 여기에 필요한 props를 전달하세요.
                    />
                );
            default:
                return null;
        }
    };

    return (
        <div className="modal">
            <div className="modal-content">{renderModalContent()}</div>
        </div>
    );
};

export default ModalContainer;
