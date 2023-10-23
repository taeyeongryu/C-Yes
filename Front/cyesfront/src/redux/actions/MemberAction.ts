export const saveMember = (memberId: number, memberNickname: string, memberAuthority: number) => {
    return {
        type: "MEMBER_SAVE",
        payload: {
            memberId,
            memberNickname,
            memberAuthority,
        },
    };
};
