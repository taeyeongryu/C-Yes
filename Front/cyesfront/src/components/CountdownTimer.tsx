import React, { useState, useEffect } from "react";
import CircleProgress from "./CircleProgress";

type CountdownTimerProps = {
    targetHour: number; // 시간
    targetMin: number; // 분
    setJoinable?: Function;
};

const CountdownTimer: React.FC<CountdownTimerProps> = ({
    targetHour,
    targetMin,
    setJoinable,
}) => {
    const [remainingTime, setRemainingTime] = useState<number | null>(null);
    const RADIUS = 150;

    useEffect(() => {
        const updateRemainingTime = () => {
            const koreanTime = new Date().toLocaleString("en-US", {
                timeZone: "Asia/Seoul",
            });
            const now = new Date(koreanTime);
            const todayTime = new Date(now);
            todayTime.setHours(targetHour, targetMin, 0, 0);

            const diff = (todayTime.getTime() - now.getTime()) / 1000;

            if (diff > 3600) {
                setRemainingTime(null); // 5시 이전
            } else if (diff <= 0) {
                setRemainingTime(null); // 6시 이후 (or you can handle this differently if needed)
            } else {
                setRemainingTime(diff); // 5시부터 6시 사이
            }

            if (0 <= diff && diff < 600) {
                if (setJoinable) setJoinable(true);
            } else {
                if (setJoinable) setJoinable(false);
            }
        };

        updateRemainingTime();
        const intervalId = setInterval(updateRemainingTime, 1000);

        return () => clearInterval(intervalId);
    }, [targetHour, targetMin]);

    const progress = remainingTime !== null ? 1 - remainingTime / 3600 : 0;
    const color =
        remainingTime !== null && remainingTime <= 300 ? "red" : "blue";

    return (
        <div style={{ textAlign: "center" }}>
            <svg
                width={2 * (RADIUS + 10)}
                height={2 * (RADIUS + 20)}
                viewBox={`0 0 ${2 * (RADIUS + 10)} ${2 * (RADIUS + 20)}`}
            >
                <CircleProgress
                    radius={RADIUS}
                    stroke={color}
                    progress={progress}
                />
                {remainingTime !== null ? (
                    <text
                        x={RADIUS + 10}
                        y={RADIUS + 15}
                        fontSize="50"
                        textAnchor="middle"
                    >
                        {`00 : ${Math.floor(remainingTime / 60)
                            .toString()
                            .padStart(2, "0")} : ${Math.floor(
                            remainingTime % 60
                        )
                            .toString()
                            .padStart(2, "0")}`}
                    </text>
                ) : (
                    <text
                        x={RADIUS + 10}
                        y={RADIUS - 50}
                        fontSize="50"
                        textAnchor="middle"
                    >
                        <tspan x={RADIUS + 10} dy="1.2em">
                            퀴즈 일정
                        </tspan>
                        <tspan
                            x={RADIUS + 10}
                            dy="1.2em"
                        >{`${targetHour} : ${targetMin
                            .toString()
                            .padStart(2, "0")}`}</tspan>
                    </text>
                )}
            </svg>
        </div>
    );
};

export default CountdownTimer;
