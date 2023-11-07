package com.cyes.webserver.domain.problem.controller;

import com.cyes.webserver.domain.Answer.service.AnswerService;
import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.service.ProblemService;
import com.cyes.webserver.interceptor.AuthInterceptor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProblemController.class)
class ProblemControllerTest {
    @Autowired
    private ProblemController problemController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProblemService problemService;
    @MockBean
    private AuthInterceptor authInterceptor;

    @Test
    @DisplayName("problemcategory를 조회할 수 있다.")
    void findAllProblemCategory() throws Exception {

        // given
        List<ProblemCategory> categoryList = List.of(ProblemCategory.DB, ProblemCategory.DESIGNPATTERN, ProblemCategory.DATASTRUCTURE, ProblemCategory.COMPUTERARCHITECTURE, ProblemCategory.ALGORITHM, ProblemCategory.NETWORK, ProblemCategory.OS);
        // when// then
        mockMvc.perform(get("/api/problem/category"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(categoryList)));
    }
}