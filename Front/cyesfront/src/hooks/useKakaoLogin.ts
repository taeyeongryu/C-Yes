import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { exchangeCodeForToken, fetchUserProfile } from "../services/OAuthAPI";

export const useKakaoLogin = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const code = new URL(window.location.href).searchParams.get("code");

        if (!code) {
            console.log("코드가 없습니다.");
            return;
        } else {
            console.log("코드가 있습니다: ", code);
        }

        const authenticateWithKakao = async () => {
            try {
                const token = await exchangeCodeForToken(code);
                window.localStorage.setItem("token", token);

                const userProfile = await fetchUserProfile(token);
                window.localStorage.setItem("profile", JSON.stringify(userProfile));

                if (userProfile) {
                    navigate("../myPage");
                }
            } catch (e) {
                console.error(e);
            }
        };

        authenticateWithKakao();
    }, [navigate]);
};
