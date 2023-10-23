import React from 'react';
import { BrowserRouter, Routes, Route, Navigate  } from 'react-router-dom';
import './App.css';
import Login from './pages/Login';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Navigate to="/login" />} />
          <Route path='/login' element={<Login />}/>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
