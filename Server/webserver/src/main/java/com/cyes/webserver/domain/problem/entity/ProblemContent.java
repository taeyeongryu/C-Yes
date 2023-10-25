package com.cyes.webserver.domain.problem.entity;

import com.cyes.webserver.domain.problem.dto.problemcontent.response.ProblemContentResponse;

public interface ProblemContent {
    ProblemContentResponse toProblemContentResponse();

}
