interface QuizState {
  quiz: {
    quizId: number;
    submit: Array<string>;
    answer: Array<string>;
  };
}

const initialQuizState: QuizState = {
  quiz: {
    quizId: -1,
    submit: [],
    answer: [],
  },
};

const quizReducer = (state = initialQuizState, action: any) => {
  switch (action.type) {
    case "OAUTH_KAKAO":
      return { ...state, quiz: { ...action.payload } };

    default:
      return state;
  }
};

export default quizReducer;
