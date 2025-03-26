import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function RegisterPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      await axios.post('/api/users/register', { username, password });
      alert('회원가입 성공! 로그인해주세요.');
      navigate('/login');
    } catch (err) {
      alert('회원가입 실패: ' + err.response?.data || '에러');
    }
  };

  return (
    <div className="container">
      <h2>회원가입</h2>
      <form onSubmit={handleRegister}>
        <div className="form-group">
          <label>아이디</label>
          <input className="form-control" value={username} onChange={(e) => setUsername(e.target.value)} />
        </div>
        <div className="form-group">
          <label>비밀번호</label>
          <input type="password" className="form-control" value={password} onChange={(e) => setPassword(e.target.value)} />
        </div>
        <button className="btn btn-primary mt-3">회원가입</button>
      </form>
    </div>
  );
}

export default RegisterPage;
