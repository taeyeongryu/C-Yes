import React from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import "./App.css";
import Quiz from "./pages/quiz/Quiz";
import Login from "./pages/login/Login";
import Live from "./pages/live/Live";
import Answer from "./pages/quiz/ConfirmQuiz";
import LoginRedir from "./pages/login/LoginRedir";


import ComputerScience from "./pages/cs/ComputerScience";
import AdminQuizCreate from "./pages/adminquiz/quizwordcreate";
import AdminQuizCheck from "./pages/adminquiz/quizCheck";
import AdminQuizInsert from "./pages/adminquiz/quizYesCheck";


function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Navigate to="/login" />} />
          <Route path="/login" element={<Login />} />
          <Route path="/login/kakao/callback" element={<LoginRedir />} />
          <Route path="/live" element={<Live />} />
          <Route path="/quiz" element={<Quiz />} />
          <Route path="/answer" element={<Answer />} />


          <Route path="/cs" element={<ComputerScience />} />
          <Route path="/adminquiz/creat" element={<AdminQuizCreate />} />
          <Route path="/adminquiz/check" element={<AdminQuizCheck />} />
          <Route path="/adminquiz/insert" element={<AdminQuizInsert />} />

        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
