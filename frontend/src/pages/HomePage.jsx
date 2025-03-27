// src/pages/HomePage.jsx
import React from 'react';
import { Link } from 'react-router-dom';

function HomePage() {
  return (
    <div className="container">
      <h1>홈페이지입니다</h1>

      <div className="d-flex gap-2 mt-3">
        <Link to="/write" className="btn btn-success">
          글쓰기
        </Link>
        <Link to="/login" className="btn btn-primary">
          로그인
        </Link>
      </div>
    </div>
  );
}

export default HomePage;
