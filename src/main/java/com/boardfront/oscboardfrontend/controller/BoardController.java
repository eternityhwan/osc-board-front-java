package com.boardfront.oscboardfrontend.controller;

import com.boardfront.oscboardfrontend.dto.BoardDto;
import com.boardfront.oscboardfrontend.entity.Board;
import com.boardfront.oscboardfrontend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

// view page 출력 controller

@Controller
@RequiredArgsConstructor
@Slf4j // 로깅을 위한 어노테이션 log를 사용할 수있다.
public class BoardController {

    private final BoardService boardService;

    // 신규 게시글 작성 페이지 이동
    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    // 전체 글 리스트 보기
    @GetMapping("/articles")
    public String showAritcleList(Model model) {

        // 1. 출력할 리스트 DB로부터 가져오기
        List<BoardDto> entireList = boardService.showAritcleList();
        model.addAttribute("articleList", entireList);
        return "index";
    }

//    // 게시글 상세 보기
//    @GetMapping(value = "/articles/{id}") public String read(@PathVariable Long id, Model model) {
//        log.info("id :" + id);
//
//        // 1. id로 데이터 가져옴
//        ArticleEntity articleEntity = articleRepository.findById(id).orElse(null);
//        // 만약 해당 아이디가 없다면 null을 반환해라 .orElse(null)
//        // java8 부터 Optional<>로 감싸도돼.
//
//        // 2. 가져온 데이터를 모델에 등록
//        model.addAttribute("article", articleEntity);
//        // 3. 보여줄 페이지 선택
//        return "articles/show";
//    }
//
//    // 전체 글 조회
//    @GetMapping(value = "/articles") public String getAll(Model model) {
//        // 1. 모든 Articles을 가져온다
//        List<ArticleEntity> articleEntityList = articleRepository.findAll();
//
//        // 2. 가져온 Article 묶음을 리스트를 뷰로 전달할 때는 모델을 슴 뷰로 전달한다.
//        model.addAttribute("articleList", articleEntityList);
//
//        // 3. 뷰 페이지를 설정
//        return "articles/index";
//    }
//
//    // 수정 폼으로 이동
//    @GetMapping(value = "articles/{id}/edit")
//    public String edit(@PathVariable Long id, Model model) {
//        // view pasge 설정
//        // articles/edit 페이지가 보여짐
//
//        // 1. DB 데이터를 가져온다.(레파지토리에게)
//        ArticleEntity articlesEntity = articleRepository.findById(id).orElse(null);
//
//        // 2. model에 데이터를 담아서 페이지로 넘긴다
//        model.addAttribute("article", articlesEntity);
//        // 3. 페이지 반환
//        return "articles/edit";
//    }
//
//    // 수정 폼에서 입력한 데이터를 DB에 수정정
//    @PostMapping(vlue = "/articles/update") public String update(ArticleDto dto) {
//        log.info(dto.toString());
//        // 1. dto를 entity로 변환한다
//        ArticleEntity articleEntity = dto.toEntity();
//        log.info(articleEntity.toString());
//
//        // 2. 엔티티를 DB로 저장한다!
//        // 2-1: DB에서 기존 데이터를 가져온다. 대상을 리턴 받아온다
//        // Optional<>을 써도된다
//        ArticleEntity targetEntity = articleRepository.findById(articleEntity.getId()).orElse(null);
//
//        // 2-2: 기존 데이터가 있다면 값을 변경한다.
//        if (targetEntity != null) {
//            articleRepository.save(articleEntity);
//        }
//        // 3. 수정 결과 페이지로 리다이렉트 한다!
//        return "redirect:/articles";
//    }
//
//    // delete 요청을 html에서 지원을 안함 DeleteMapping 못씀
//    // id는 url에서 가져오는 것.
//    @GetMapping(value = "/articles/{id}/delete") public String delete(@PathVariable Long id,
//        RedirectAttributes rtt) {
//        log.info("삭제요청 들어옴");
//        // RedirectAttributes 클래스
//
//        // 1. 삭제 대상 찾기(가져와야 삭제를 하지)
//        ArticleEntity deleteArticleEntity = articleRepository.findById(id).orElse(null);
//
//        // 2. 삭제 대상 삭제
//        // 삭제대상이 있으면 삭제한다
//        // 리파지토리야.delete 명령 실행하고 (deleteAricle[대상])을 지워.
//        if (deleteArticleEntity != null) {
//            articleRepository.delete(deleteArticleEntity);
//            rtt.addFlashAttribute("message", "삭제 성공");
//            // addFlashAttribute 는 페이지에서 딱 한 번 만 쓸 수 있는 휘발성 데이터 등록됨.
//            // articles 페이지로 리다이렉트 하니까
//            // articles 페이지에
//        }
//
//        // 3. 결과 페이지로 리다이렉트(화이트라벨 페이지 보기 싫음)
//        return "redirect:/articles";
//    }
}
