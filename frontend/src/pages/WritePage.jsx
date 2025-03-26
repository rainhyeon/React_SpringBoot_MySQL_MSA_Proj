import React, { useState } from 'react';
import axios from 'axios';

function WritePage() {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  const handleSubmit = async () => {
    try {
      const userId = 1; // 테스트용 사용자 ID, 실제론 로그인된 사용자 ID
      await axios.post('/api/board/write', { title, content, userId });
      alert('게시글 작성 완료!');
    } catch (err) {
      alert('작성 실패: ' + err.response?.data);
    }
  };

  return (
    <div className="container">
      <h2>게시글 작성</h2>
      <input className="form-control" placeholder="제목" value={title} onChange={(e) => setTitle(e.target.value)} />
      <textarea className="form-control mt-2" placeholder="내용" value={content} onChange={(e) => setContent(e.target.value)} />
      <button className="btn btn-primary mt-3" onClick={handleSubmit}>작성 완료</button>
    </div>
  );
}

export default WritePage;
