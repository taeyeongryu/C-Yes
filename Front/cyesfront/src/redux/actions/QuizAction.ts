import { Quiz } from "../ReduxStateInterface";

export const saveQuizId = (quiz: Quiz) => {
    return {
        type: "QUIZ_SAVE",
        payload: {
            ...quiz,
        },
    };
};
