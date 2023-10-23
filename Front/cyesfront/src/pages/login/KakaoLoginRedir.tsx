import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";
import { requestKakaoLoginInfo } from "../../services/OAuthAPI";

const KakaoLoginRedir = () => {
    const member = useSelector((state: any) => state.member);
    const dispatch = useDispatch();

    const kakaoLogin = () => {};

    useEffect(() => {
        const oAuthType = useSelector((state: any) => state.oauth);

        const code = new URL(window.location.href).searchParams.get("code");

        if (code != null) {
            requestKakaoLoginInfo(code);
        }
    }, []);

    return <div></div>;
};

export default KakaoLoginRedir;
