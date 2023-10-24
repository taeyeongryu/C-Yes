import React from 'react';
import { BrowserRouter, Routes, Route, Navigate  } from 'react-router-dom';
import './App.css';
import Quiz from './pages/quiz/Quiz';
import Login from './pages/login/Login'
import Live from './pages/live/Live';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Navigate to="/login" />} />
          <Route path='/login' element={<Login />}/>
<<<<<<< HEAD
          <Route path='/live' element={<Live />}/>
=======
          <Route path='/quiz' element={<Quiz />}/>
>>>>>>> ba1c385989855cab81632473c31ea793d892f36f
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
