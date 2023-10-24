import React from 'react';
import { BrowserRouter, Routes, Route, Navigate  } from 'react-router-dom';
import './App.css';
import Login from './pages/Login';
import Quiz from './pages/quiz/Quiz';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Navigate to="/login" />} />
          <Route path='/login' element={<Login />}/>
          <Route path='/quiz' element={<Quiz />}/>
          
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
