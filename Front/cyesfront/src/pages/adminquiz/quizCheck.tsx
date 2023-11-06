import React, { useState, useEffect } from "react";
import axios from "axios";
import Pagination from "./pageNation"; // Pagination 컴포넌트를 import

interface Quiz {
  content: Content[];
  pageable: Pageable;
  totalElements: number;
  totalPages: number;
}

interface Content {
  id: string;
  question: string;
  category: string;
}

interface Pageable {
  pageSize: number;
  pageNumber: number;
}

const QuizsList: React.FC = () => {
  const [quizs, setQuizs] = useState<Content[]>([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0); // 전체 페이지 수 상태 추가
  const itemsPerPage = 3; // 페이지당 아이템 수를 원하는 값으로 설정

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get<Quiz>(
          `http://localhost:5000/api/adminproblem/no-check-all?page=${currentPage}&size=${itemsPerPage}`
        );
        setQuizs(response.data.content);
        // API 응답에서 전체 페이지 수 정보 추출
        setTotalPages(response.data.totalPages);
        console.log(response.data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
  }, [currentPage]);

  const handlePageChange = (newPage: number) => {
    setCurrentPage(newPage);
  };

  return (
    <div>
      <h1>Quiz List</h1>
      <table className="quizCheck">
        <thead>
          <tr>
            <th>Question</th>
            <th>Category</th>
            <th>여부</th>
          </tr>
        </thead>
        <tbody>
          {quizs.map((quiz) => (
            <tr key={quiz.id}>
              <td>{quiz.question}</td>
              <td>{quiz.category}</td>
              <td>삭제</td>
            </tr>
          ))}
        </tbody>
      </table>

      <Pagination
        currentPage={currentPage}
        totalPages={totalPages}
        onPageChange={handlePageChange}
      />
    </div>
  );
};

export default QuizsList;
