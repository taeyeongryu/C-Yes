import React from "react";

type Props = {
    answer: string;
    submit: string;
    correctNumber?: number;
    totalNumber?: number;
};

const AnswerResultComponent = ({
    answer,
    submit,
    correctNumber,
    totalNumber,
}: Props) => {
    return (
        <div className="answer-result-body">
            <div>정답은!</div>
            <div>{answer}</div>
            <div>{answer === submit ? "맞았습니다!" : "틀렸습니다!"}</div>
            <div>{`총 ${totalNumber}명 중 ${correctNumber}명이 맞췄습니다`}</div>
        </div>
    );
};

export default AnswerResultComponent;
