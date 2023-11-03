package com.cyes.webserver.domain.adminQuiz.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class OpenAIPostRequestService {

        private static final String OPENAI_API_KEY = "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";

        public String sendPostword(String word) {
            System.out.println("들어옴?");
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-Type", "application/json;charset=UTF-8");
            headers.set("Content-Type", "application/json;charset=UTF-8");
//            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + OPENAI_API_KEY);

            String requestBody = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\",\"content\": \" " +
                   "안녕" + "\"}] }";
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            String apiUrl = "https://api.openai.com/v1/chat/completions";
            System.out.println("여긴 왔니");
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

            log.info(response.getBody());

            System.out.println("여긴?");
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("여긴???");
                return response.getBody();
            } else {
                // Handle error here
                return "API Request Failed";
            }
        }

    public String makeShortProblem(String searchWord) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + OPENAI_API_KEY);

        String requestBody = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"system\",\"content\": \" " +
                searchWord + "에 대해 두줄로 설명해줘" + "\"}] }";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        String apiUrl = "https://api.openai.com/v1/chat/completions";
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            // Handle error here
            return "API Request Failed";
        }
    }

}
