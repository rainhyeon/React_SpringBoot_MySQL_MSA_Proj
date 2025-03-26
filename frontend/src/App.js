// src/App.js
import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import RegisterPage from './pages/RegisterPage';
import LoginPage from './pages/LoginPage';
import WritePage from './pages/WritePage';
import HomePage from './pages/HomePage';
import axios from 'axios';



function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  // ✅ 새로고침 시에도 로그인 상태 유지
  //useEffect(() => {
  //  const savedLogin = localStorage.getItem('isLoggedIn') === 'true';
  //  setIsLoggedIn(savedLogin);
  //}, []);

  return (
    <Router>
      <div className="container">
        <h1>AWS</h1>
        <Routes>
          <Route path="/" element={<HomePage isLoggedIn={isLoggedIn} />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/login" element={<LoginPage setIsLoggedIn={setIsLoggedIn} />} />
          <Route path="/write" element={<WritePage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
