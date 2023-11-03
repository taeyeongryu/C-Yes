package com.cyes.webserver.domain.adminQuiz.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class Usage {
    public String prompt_tokens;
    public String completion_tokens;
    public String total_tokens;
}