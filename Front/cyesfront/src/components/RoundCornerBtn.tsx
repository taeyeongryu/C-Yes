import React from "react";
import styled from "styled-components";

type Props = {
    children?: React.ReactNode;
    width?: string;
    height?: string;
    bgcolor?: string;
    bghover?: string;
    fontSize?: string;
    fontColor?: string;
    type?: "button" | "submit" | "reset"; // type 속성 추가
    onClick?: () => void;
    disabled?: boolean;
};

const StyledButton = styled.button<Props>`
    width: ${(props) => props.width || "100px"};
    height: ${(props) => props.height || `40px`};
    background-color: ${(props) => props.bgcolor || `#31a0eb`};
    &:active {
        background-color: ${(props) => props.bghover || `#32237d`};
    }
    font-size: ${(props) => props.fontSize || "12px"};
    color: ${(props) => props.fontColor || `#ffffff`};
    border-radius: 18px;

    border: none;
`;

const RoundCornerBtn = ({ children, ...style }: Props) => {
    return <StyledButton {...style}>{children}</StyledButton>;
};

export default RoundCornerBtn;