export const saveQuizId = (quizId: number) => {
    return {
        type: "QUIZ_SAVEID",
        payload: {
            quizId,
        },
    };
};
