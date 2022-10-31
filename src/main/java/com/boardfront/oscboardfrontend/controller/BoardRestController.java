package com.boardfront.oscboardfrontend.controller;


import com.boardfront.oscboardfrontend.dto.BoardDto;
import com.boardfront.oscboardfrontend.entity.Board;
import com.boardfront.oscboardfrontend.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class BoardRestController {

    @Autowired
    private BoardService boardService;

    // 게시글 작성
    @PostMapping(value = "/articles/posts")
    public BoardDto createArticle(
        @Valid @RequestBody BoardDto dto) {
        return boardService.insertArticle(dto);
    }
}
