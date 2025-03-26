// src/pages/HomePage.jsx
import React from 'react';
import { Link } from 'react-router-dom';

function HomePage({ isLoggedIn }) {
  return (
    <div className="container">
      <h1>홈페이지입니다</h1>
      {isLoggedIn ? (
        <>
          <p>로그인 중입니다.</p>
          <Link to="/write" className="btn btn-success">
            글쓰기
          </Link>
        </>
      ) : (
        <>
          <p>로그인 후 글쓰기 가능합니다.</p>
          <Link to="/login" className="btn btn-primary">
            로그인하러 가기
          </Link>
        </>
      )}
    </div>
  );
}

export default HomePage;
