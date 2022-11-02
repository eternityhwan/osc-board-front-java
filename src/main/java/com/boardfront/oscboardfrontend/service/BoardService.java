package com.boardfront.oscboardfrontend.service;

import com.boardfront.oscboardfrontend.dto.BoardDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
@Slf4j
public class BoardService {

    @Value("${custom.api.baseurl}") private String BASE_URL;

    // Post 기능
    public BoardDto insertNewArticle(BoardDto boardDto) {

        return WebClient.create().post().uri(BASE_URL + "/api/boards/posts")
            .contentType(MediaType.APPLICATION_JSON).bodyValue(boardDto).retrieve()
            .bodyToMono(new ParameterizedTypeReference<BoardDto>() {
            }).block();
    }


    // 전체 글 조회
    public List<BoardDto> showArticleList() {

        //        WebClient client = WebClient
        //            .builder()
        //            .baseUrl(BASE_URL)
        //            .build();

        return WebClient.create().get().uri(BASE_URL + "/api/boards").exchangeToFlux(response -> {
            return response.bodyToFlux(BoardDto.class);
        }).collectList().block();
    }

    // 개별 글 조회
    public BoardDto receiveArticleDetail(Long id) {

        URI juri;
        try {
            juri = new URI(BASE_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return WebClient.create().get().uri(uriBuilder -> uriBuilder.scheme(juri.getScheme()).host(juri.getHost())
                    .port(juri.getPort()).path("/api/boards/{id}").build(id)).retrieve().bodyToMono(BoardDto.class).block();
    }

    //  수정 기능
    public BoardDto updateArticle(BoardDto boardDto) {

        return WebClient.create()
            .patch()
            .uri(BASE_URL + "/api/boardsR/{id}", boardDto.getId())
            .bodyValue(boardDto)
            .retrieve()
            .bodyToMono(BoardDto.class)
            .block();
    }

    // 삭제 기능
    public void deleteDbData(Long id) {

        System.out.println("id service = " + id);

        WebClient.create()
//            .method(HttpMethod.DELETE)
            .delete()
            .uri(BASE_URL + "/api/boardsD/" + id)
            .retrieve()
            .bodyToMono(Void.class)
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
