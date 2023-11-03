package com.cyes.webserver.domain.adminQuiz.service;

import com.cyes.webserver.domain.adminQuiz.dto.Choices;
import com.cyes.webserver.domain.adminQuiz.dto.openAIDTO;
import com.cyes.webserver.domain.adminQuiz.entity.noCheckShortProblem;
import com.cyes.webserver.domain.adminQuiz.repository.noCheckProblemRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OpenAIPostRequestService {
    private final noCheckProblemRepository noCheckProblemRepository;
    private static final String OPENAI_API_KEY = "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";

        public List<String> sendPostword(String word) throws JsonProcessingException {

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-Type", "application/json;charset=UTF-8");
            headers.set("Content-Type", "application/json;charset=UTF-8");
//            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + OPENAI_API_KEY);

            String requestBody = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\",\"content\": \" " +
                  word + "에 관련된 설명말고 단어만 1개 뽑아줘" + "\"}] }";
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            String apiUrl = "https://api.openai.com/v1/chat/completions";

            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

            log.info(response.getBody());

            ObjectMapper objectMapper = new ObjectMapper();

            openAIDTO openaidto = null;


            if (response.getStatusCode().is2xxSuccessful()) {
                openaidto = objectMapper.readValue(response.getBody(), openAIDTO.class);
                log.info("openaidto = {}", openaidto);

                Choices[] choices = openaidto.getChoices();
                List<String> returnList = new ArrayList<>();
                for (Choices choice : choices) {

                    returnList.add(choice.getMessage().getContent());
                }
                return returnList;
            } else {
                // Handle error here
                return List.of("API Request Failed");
            }
        }

        @Transactional
    public String makeShortProblem(String searchWord) throws JsonProcessingException {

        System.out.println("설명할거" + searchWord);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + OPENAI_API_KEY);

        String requestBody = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\",\"content\": \"" +
                searchWord + "에 대해 두줄로 설명해줘" + "\"}] }";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        String apiUrl = "https://api.openai.com/v1/chat/completions";

        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        openAIDTO openaidto = null;

        if (response.getStatusCode().is2xxSuccessful()) {
            openaidto = objectMapper.readValue(response.getBody(), openAIDTO.class);

            Choices[] choices = openaidto.getChoices();
            List<String> returnList = new ArrayList<>();

            for (Choices choice : choices) {
                returnList.add(choice.getMessage().getContent());

                log.info("말해줘" + choice.getMessage().getContent());

                noCheckShortProblem ncst = noCheckShortProblem.builder()
                        .question(choice.getMessage().getContent())
                        .build();


                System.out.println("뭐임마" + ncst.getQuestion());

                if (ncst != null) {
                    log.info("널아니야");

                    noCheckProblemRepository.save(ncst);
                } else {
                    log.info("너 널이냐??");
                }

                noCheckShortProblem problem = noCheckProblemRepository.save(ncst);

                if(problem != null) return "Save Successful";

                log.info("성공"+ choice.getMessage().getContent());

            }
        } else {
            // Handle error here
            return "Save Failure";
        }
        return "Save Failure";
    }

}
