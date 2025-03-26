import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post('/api/users/login', { username, password });
      alert('로그인 성공!');
      navigate('/'); // 홈으로 이동
    } catch (err) {
      alert('로그인 실패');
    }
  };

  return (
    <div className="container">
      <h2>로그인</h2>
      <form onSubmit={handleLogin}>
        <div className="form-group">
          <label>아이디</label>
          <input className="form-control" value={username} onChange={(e) => setUsername(e.target.value)} />
        </div>
        <div className="form-group">
          <label>비밀번호</label>
          <input type="password" className="form-control" value={password} onChange={(e) => setPassword(e.target.value)} />
        </div>
        <button className="btn btn-primary mt-3">로그인</button>
      </form>
      <button className="btn btn-link mt-2" onClick={() => navigate('/register')}>
        회원가입 하러가기
      </button>
    </div>
  );
}

export default LoginPage;
