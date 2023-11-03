package com.cyes.webserver.domain.adminQuiz.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown=true)
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class openAIDTO {

        // 아이디, 이메일, profileimg
        public String id;
        public String object;
        public String created;
        public String model;
        public Choices[] choices;
        public Usage usage;



}
