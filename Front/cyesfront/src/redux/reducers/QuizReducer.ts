
interface QuizState {
    quizId: number;
}

const initialQuizState: QuizState = {
    quizId: -1,
};

const QuizReducer = (state = initialQuizState, action: any) => {
    switch (action.type) {
        case "OAUTH_KAKAO":
            return { ...state, quizId: action.payload };

        default:
            return state;
    }
};

export default QuizReducer;
