import { combineReducers } from "@reduxjs/toolkit";
import oAuthReducer from "./OAuthReducer";
import memberReducer from "./MemberReducer";
import quizReducer from "./QuizReducer";

// Redux는 하나의 Reducer만 가질 수 있다. 따라서 여러개의 리듀서가 있다면 이를 하나로 합친 rootReducer를 만들면 된다.
const rootReducer = combineReducers({
    oauth: oAuthReducer,
    member: memberReducer,
    quiz: quizReducer
});

export default rootReducer;
