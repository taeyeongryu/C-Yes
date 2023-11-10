import React from "react";
import RoundCornerBtn from "../../components/RoundCornerBtn";
import "./Quiz.css";

type Props = {
    answer: string;
    answerLength: number;
    textareaValue: string;
    isTextareaEnabled: boolean;
    isSubmitted: boolean;
    onTextAreaChanged: (event: React.ChangeEvent<HTMLTextAreaElement>) => void;
    onSubmit: React.Dispatch<React.SetStateAction<boolean>>;
};

const SubmitShort = ({
    answer,
    answerLength,
    textareaValue,
    isTextareaEnabled,
    isSubmitted,
    onSubmit,
    onTextAreaChanged,
}: Props) => {
    return (
        <div>
            <div className="answer-box" style={{ display: "flex" }}>
                {Array.from({
                    length: answerLength ? answerLength : 0,
                }).map((_, index) => (
                    <div key={index} className="square">
                        {answer ? answer[index] : null}
                    </div>
                ))}
            </div>
            <div className="input-content">
                <div>
                    <textarea
                        id="answer-input"
                        name="content"
                        value={textareaValue}
                        onChange={onTextAreaChanged}
                        disabled={!isTextareaEnabled} // 비활성화 상태 조절
                        style={{
                            backgroundColor: isTextareaEnabled ? "white" : "lightgray", // 배경색 제어
                            color: isTextareaEnabled ? "black" : "gray", // 텍스트 색상 제어
                        }}
                        placeholder={isTextareaEnabled ? "입력하세요" : " "} // placeholder 설정
                    />
                </div>

                <div>
                    <RoundCornerBtn
                        type="submit"
                        onClick={() => onSubmit(true)}
                        bgcolor={isSubmitted ? "#265587" : undefined}
                        bghover="#265587"
                        disabled={isSubmitted}
                    >
                        {isSubmitted ? "제출 완료" : "제출"}
                    </RoundCornerBtn>
                </div>
            </div>
        </div>
    );
};

export default SubmitShort;
