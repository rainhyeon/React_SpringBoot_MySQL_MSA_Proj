import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';

function LoginPage({ setIsLoggedIn }) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  // ✅ 로그인 상태 확인 (선택적)
  useEffect(() => {
    const checkLoginStatus = async () => {
      try {
        const res = await axios.get('/api/users/status');
        if (res.data.loggedIn) {
          localStorage.setItem('isLoggedIn', 'true');
          setIsLoggedIn(true);
        }
      } catch (err) {
        localStorage.setItem('isLoggedIn', 'false');
        setIsLoggedIn(false);
      }
    };

    checkLoginStatus();
  }, []);

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      await axios.post('/api/users/login', { username, password });
      alert('로그인 성공!');
      localStorage.setItem('isLoggedIn', 'true');
      setIsLoggedIn(true);
      navigate('/');
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
          <input
            className="form-control"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label>비밀번호</label>
          <input
            type="password"
            className="form-control"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <button className="btn btn-primary mt-3">로그인</button>
      </form>

      {/* ✅ 회원가입 버튼 추가 */}
      <div className="mt-3">
        <p>계정이 없으신가요?</p>
        <Link to="/register" className="btn btn-secondary">
          회원가입
        </Link>
      </div>
    </div>
  );
}

export default LoginPage;
