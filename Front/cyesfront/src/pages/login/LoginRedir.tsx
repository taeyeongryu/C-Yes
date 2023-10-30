import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { requestKakaoLoginInfo } from "../../api/LoginAPI";

const LoginRedir = () => {
  const memberState = useSelector((state: any) => state.member);
  const oAuthState = useSelector((state: any) => state.oauth);

  const dispatch = useDispatch();

  useEffect(() => {
    console.log("메롱")
    const Login = async () => {
      let data;
      if (oAuthState.oAuthType === 0) {
        console.log("여기???");
        const code = new URL(window.location.href).searchParams.get("code");
        data = await requestKakaoLoginInfo(code);
      } else {
        console.log("여기?");
        // TODO : NAVER LOGIN
      }

      console.log(data);
    };

    Login();
  }, []);

  return <div>로그인 중 입니다!</div>;
};

export default LoginRedir;
