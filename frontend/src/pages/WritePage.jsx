import React, { useState } from 'react';
import axios from 'axios';

function WritePage() {
  const [userId, setUserId] = useState('');
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  const handleSubmit = async () => {
    try {
      if (!userId || !title || !content) {
        alert('모든 항목을 입력해주세요.');
        return;
      }

      await axios.post('/api/board/write', {
        userId,
        title,
        content
      });

      alert(`회원 정보 확인: ${userId} 게시글 작성 완료!`);
    } catch (err) {
      alert('작성 실패: ' + (err.response?.data || err.message));
    }
  };

  return (
    <div className="container">
      <h2>게시글 작성</h2>

      <input
        type="text"
        className="form-control mb-2"
        placeholder="사용자 ID"
        value={userId}
        onChange={(e) => setUserId(e.target.value)}
      />
      <input
        type="text"
        className="form-control mb-2"
        placeholder="제목"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      />
      <textarea
        className="form-control mb-2"
        placeholder="내용"
        value={content}
        onChange={(e) => setContent(e.target.value)}
      />
      <button
        type="button"
        className="btn btn-primary"
        onClick={handleSubmit}
      >
        작성 완료
      </button>
    </div>
  );
}

export default WritePage;
