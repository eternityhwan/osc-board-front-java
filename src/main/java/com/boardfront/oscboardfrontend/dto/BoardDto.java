package com.boardfront.oscboardfrontend.dto;

import com.boardfront.oscboardfrontend.entity.Board;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@NoArgsConstructor
@Data
public class BoardDto {

    private String title;

    private String content;

    }

