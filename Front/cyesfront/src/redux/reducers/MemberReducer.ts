interface MemberState {
    member: {
        memberId: number;
        memberNickname: string;
        memberAuthority: number;
    };
}

const initialMemberState: MemberState = {
    member: {
        memberId: -1,
        memberNickname: "",
        memberAuthority: 0,
    },
};

const memberReducer = (state = initialMemberState, action: any) => {
    switch (action.type) {
        case "MEMBER_SAVE":
            return { ...state, member: { ...action.payload } };

        default:
            return state;
    }
};

export default memberReducer;
