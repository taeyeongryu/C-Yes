import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
// import { saveAnswer } from './redux/actions'; // Redux 액션 import
import './Quiz.css';

const Quiz: React.FC = () => {
    // const dispatch = useDispatch();
  const [questions, setQuestions] = useState([
    { question: '첫 번째 질문', answer: '애자일' },
    { question: '두 번째 질문', answer: '아보카도' },
    { question: '세 번째 질문', answer: '답3' },
  ]);

  const [currentQuestion, setCurrentQuestion] = useState(0);
  const [progress, setProgress] = useState(0);
  const [showEndPage, setShowEndPage] = useState(false);
  const [submitted, setSubmitted] = useState(false);

  // const handleAnswerSubmit = (userAnswer: string) => {
  //   if (!submitted) {
  //     // 아직 제출되지 않았을 때만 처리
  //     // dispatch(saveAnswer(userAnswer)); // Redux에 사용자의 답변 저장
  //     setSubmitted(true); // 제출 완료 상태로 설정
  //   }
  // };

  useEffect(() => {
    const timer = setInterval(() => {
      if (progress >= 100) {
        if (currentQuestion < questions.length - 1) {
          setCurrentQuestion(currentQuestion + 1);
          setProgress(0);
        } else {
          setShowEndPage(true);
          clearInterval(timer);
        }
      } else {
        setProgress(progress + 10);
      }
    }, 500); // 10초마다 업데이트

    return () => {
      clearInterval(timer);
    };
  }, [progress, currentQuestion, questions]);

  return (
    <div className="container">
        <img className="live-logo-img" src='/img/live_logo.png' alt="" />
        <div className="head">SSA피드 퀴즈</div>


        {!showEndPage ? (
           <div className="form">
             <div className="form-group">         
                 <div className='quiz'>
                    {questions[currentQuestion].question}
                 </div>
                 <div className="answer-box"  style={{ display: 'flex' }}>
                    {Array.from({ length: questions[currentQuestion].answer.length }).map((_, index) => (
                            <div key={index} className="square"></div> 
                        ))}
                </div>

                <textarea id="content" name="content"/>      
            </div>
            <button
                type="submit"
                // onClick={() =>}
                //    handleAnswerSubmit('사용자 답')}
                disabled={submitted}
            >
                {submitted ? '제출 완료' : '제출'}
          </button>
            <div>
                <ProgressBar progress={progress} />
            
            </div>
        </div>
        ) : (
            <div className="end-page">퀴즈 종료 페이지</div>
          )}
    </div>
  );
};


const ProgressBar: React.FC<{ progress: number }> = ({ progress }) => (
    <div>
      <div style={{ width: `${progress}%`, backgroundColor: progress >= 80 ? 'red' : 'blue', height: '10px' }} />
    </div>
  );


export default Quiz;
