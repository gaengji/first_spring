package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j // Simple Logging Facade for Java 로깅을 위한 어노테이션
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        // 로그 println 으로 하면 병목현상 ㅈ댄다 하면
        // System.out.println(form.toString());
        log.info(form.toString());

        // 1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        log.info(article.toString());
        // 2. 레포지토리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);

        // 1. id로 데이터를 가져옴
        // Optional<Article> articleEntity = articleRepository.findById(id);
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2. 가져온 데이터를 모델에 등록!
        model.addAttribute("article", articleEntity);

        // 3. 보여줄 페이지를 설정!
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        // 1. 모든 Article 을 가져온다
        // List<Article> articleEntityList = (List<Article>)articleRepository.findAll();
        // Iterable<Article> articleEntityList = articleRepository.findAll();
        List<Article> articleEntityList = articleRepository.findAll();

        // 2. 가져온 Article 묶음을 모델에 전달
        model.addAttribute("articleList", articleEntityList);

        // 3. 뷰 페이지를 설정
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 1. 수정돨 데이터 가져오가
        Article articleEntity = articleRepository.findById(id).orElse(null);
        // 2. 모델에 데이터를 등록
        model.addAttribute("article", articleEntity);
        // 3. 뷰 페이지 등록
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());

        // 1. DTO를 엔티티로 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 2. 엔티티를 DB에 저장
        // 2-1. DB에서 기존 데이터를 가져온다!!
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        // 2-2. 기존 데이터가 있다면 값을 갱신한다
        if (target != null) {
            articleRepository.save(articleEntity);
        }

        // 3. 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다");
        // 1. 삭제 대상을 가져온다
        Article target = articleRepository.findById(id).orElse(null);
        // 2. 대상을 삭제한다
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다");
        }
        // 3, 결과 페이지로 리다이렉트 한다
        return "redirect:/articles";
    }
}
