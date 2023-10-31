import React, { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { requestKakaoLoginInfo } from "../../api/LoginAPI";
import { saveMember } from "../../redux/actions/MemberAction";
import { useNavigate } from "react-router";

const LoginRedir = () => {
  const oAuthState = useSelector((state: any) => state.oauth);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  useEffect(() => {
    const Login = async () => {
      let data;
      if (oAuthState.oAuthType === 0) {
        const code = new URL(window.location.href).searchParams.get("code");
        data = await requestKakaoLoginInfo(code);

        if (data) {
          dispatch(saveMember(data.memberId, data.memberNickname, "USER"));
          navigate('/live');
        } else {
          // 데이터가 없거나 에러 발생 시
          navigate('/');
        }

      } else {
        // TODO : NAVER LOGIN
      }
    };

    Login();
  }, [oAuthState, dispatch, navigate]);

  return <div>로그인 중 입니다!</div>;
};

export default LoginRedir;
