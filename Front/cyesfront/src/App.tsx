import React from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import "./App.css";
import Login from "./pages/login/Login";
import KakaoLoginRedir from "./pages/login/LoginRedir";

function App() {
    return (
        <div className="App">
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<Navigate to="/login" />} />
                    <Route path="/login" element={<Login />} />
                    <Route
                        path="/login/kakao/callback"
                        element={<KakaoLoginRedir />}
                    />
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;
