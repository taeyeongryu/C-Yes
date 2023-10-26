package com.cyes.webserver.scheduler;

import com.cyes.webserver.scheduler.Job.LiveQuizJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuizScheduler {

    public void createQuizSchedule(String quizId) {
        try {
            JobDetail job = JobBuilder.newJob(LiveQuizJob.class)
                    .withIdentity(quizId)
                    .usingJobData("quizId", quizId)
                    .build();


            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(quizId+"trigger")
                    .startAt(new Date(System.currentTimeMillis()+5000))
                    .withSchedule(SimpleScheduleBuilder
                            .simpleSchedule())
                    .build();

            SchedulerFactory schedFact = new StdSchedulerFactory();
            Scheduler sched = schedFact.getScheduler();
            sched.start();
            sched.scheduleJob(job, trigger);
        } catch(Exception e) {
            e.printStackTrace();
            log.error("마 스케줄 터졌다이");
        }

    }

    public void updateQuizSchedule() throws SchedulerException {

    }

}
