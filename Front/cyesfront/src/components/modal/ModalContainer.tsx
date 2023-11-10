import { useNavigate } from "react-router-dom";
import LoadingModal from "../LoadingModal";
import RankingModal from "./RankingModal";
import MomentOfRank from "./MomentOfRank";
import { MemberScore } from "../../api/websocket/MessageInterface";

interface ModalProps {
    showModal: boolean;
    resultList: MemberScore[];
    myScore?: number;
    totalProblemLength?: number;
    modalType: string;
}

const ModalContainer: React.FC<ModalProps> = (props: ModalProps) => {
    const { showModal, resultList, myScore, totalProblemLength, modalType } = props;
    const navigate = useNavigate();

    const moveMain = () => {
        navigate("/live");
    };

    if (!showModal) {
        return null;
    }

    const renderModalContent = () => {
        if (!resultList) {
            return <LoadingModal />;
        }

        switch (modalType) {
            case "result":
                return (
                    <RankingModal
                        memberList={resultList}
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
