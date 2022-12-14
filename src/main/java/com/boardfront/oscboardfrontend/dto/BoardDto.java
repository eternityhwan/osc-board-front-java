package com.boardfront.oscboardfrontend.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class BoardDto {

    private Long id;

    private String title;

    private String content;
 // Dto 금지 model로 변경
}

