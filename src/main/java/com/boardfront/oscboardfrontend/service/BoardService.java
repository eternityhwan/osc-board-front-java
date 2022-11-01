package com.boardfront.oscboardfrontend.service;

import com.boardfront.oscboardfrontend.dto.BoardDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
@Slf4j
public class BoardService {

    @Value("${custom.api.baseurl}")
    private String BASE_URL;

    // Post 기능
    public BoardDto insertNewArticle(BoardDto boardDto) {

        return WebClient.create()
            .post()
            .uri(BASE_URL + "/api/boards/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(boardDto)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<BoardDto>() { })
            .block();
    }


    // 전체 글 조회
    public List<BoardDto> showArticleList() {

//        WebClient client = WebClient
//            .builder()
//            .baseUrl(BASE_URL)
//            .build();

        return WebClient.create()
            .get()
            .uri(BASE_URL + "/api/boards")
            .exchangeToFlux(response ->
            {
            return response.bodyToFlux(BoardDto.class);
            }).collectList()
            .block();
    }
    // 개별 글 조회
    public BoardDto receiveArticleDetail(Long id) {

        URI juri;
        try {
            juri = new URI(BASE_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
            return WebClient.create()
                .get()
                .uri(uriBuilder -> uriBuilder
                    .scheme(juri.getScheme())
                    .host(juri.getHost())
                    .port(juri.getPort())
                    .path("/api/boards/{id}")
                    .build(id))
                .retrieve()
                .bodyToMono(BoardDto.class)
                .block();
    }
    // 수정 기능

    // 게시물 수정
    public BoardDto updateBoard(Long boardId, BoardDto board){

        URI juri;
        try {
            juri = new URI(BASE_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return  WebClient.create()
            .put()
            // .uri("/board/{boardId}", boardId)
            .uri(uriBuilder -> uriBuilder
                .scheme(juri.getScheme())
                .host(juri.getHost())
                .port(juri.getPort())
                .path("/api/boardsR/{id}")
                .build(boardId))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(board)
            .retrieve()
            .bodyToMono(BoardDto.class)
            .block();
    }

}


//    WebClient webClient = WebClient.builder()
//        .baseUrl("http://ec2-3-38-111-117.ap-northeast-2.compute.amazonaws.com:32574")
//        .build()
//        ;
//    List<BoardDto> fb = webClient
//        .get()
//        .uri("/api/boards")
//        .exchangeToFlux(response->{
//            System.out.println(response);
//            return response.bodyToFlux(BoardDto.class);
//        }).collectList().block();
//public BoardDto bringArticle(Long id) {
//
//    URI juri;
//    try {
//        juri = new URI(BASE_URL);
//    } catch (URISyntaxException e) {
//        throw new RuntimeException(e);
//    }
//
//    return WebClient.create()
//        .get()
//        .uri(uriBuilder -> uriBuilder
//            .scheme(juri.getScheme())
//            .host(juri.getHost())
//            .port(juri.getPort())
//            .path("/api/boardsR/{id}")
//            .build(id))
//        .retrieve()
//        .bodyToMono(BoardDto.class)
//        .block();
//}
