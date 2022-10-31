package com.boardfront.oscboardfrontend.service;

import com.boardfront.oscboardfrontend.dto.BoardDto;
import com.boardfront.oscboardfrontend.entity.Board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Slf4j
public class BoardService {

    @Autowired
    private WebClient client;


    public BoardDto insertArticle(BoardDto boardDto) {

        return client
            .post()
            .uri( "/articles/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(boardDto)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<BoardDto>() {})
            .block();
    }


    public List<BoardDto> showAritcleList() {

        return client
            .get()
            .uri( "/articles")
            .exchange()
            .flatMapMany(res -> res.bodyToFlux(BoardDto.class))
            .collectList()
            .block();
//            .accept(MediaType.APPLICATION_JSON)
//            .retrieve()
//            .bodyToMono(new ParameterizedTypeReference<List<BoardDto>>() {})
//            .block();
    }
}
