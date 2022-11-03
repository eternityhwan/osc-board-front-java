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

        // create()가 반복됨. 인스턴스 위로 올리기(인스턴스 재활용할것)
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

        // 객체 생성이 자원을 많이 소모한다는 걸 알아야해.
        // webClient bean을 만들어라 WebClientConfig 클래스를 만들어서 할 것.
        // 인스턴스가 하나만 생긴다 리소스 효율이 좋아진다.
        // 인스턴스가 너무 생긴다

        return WebClient.create()
            .patch()
            .uri(BASE_URL + "/api/boards/{id}", boardDto.getId())
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
            .uri(BASE_URL + "/api/boards/" + id)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }
}
