package seungmi.myblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import seungmi.myblog.dto.ArticleDto;
import seungmi.myblog.dto.CategoryDto;
import seungmi.myblog.dto.UserAccountDto;
import seungmi.myblog.dto.request.ArticleRequest;
import seungmi.myblog.dto.response.ArticleResponse;
import seungmi.myblog.dto.security.BoardPrincipal;
import seungmi.myblog.service.ArticleService;
import seungmi.myblog.service.CategoryService;
import seungmi.myblog.service.PaginationService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final PaginationService paginationService;

    //게시글 목록
    @GetMapping
    public String list(@PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                       Model model,
                       @Param(value = "category") String category) {

        //카테고리
        List<CategoryDto> allCategories = categoryService.getAllCategories();
        List<CategoryDto> sortedAllCategories = allCategories.stream().sorted(Comparator.comparing(CategoryDto::priority)).collect(Collectors.toList());

        long countArticle = articleService.countArticle();

        model.addAttribute("allCategories", sortedAllCategories);
        model.addAttribute("totalArticle", countArticle);

        //게시글
        if (category == null || category.equals("전체글")) {

            Page<ArticleDto> articleList = articleService.getAllArticle(pageable);
            List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articleList.getTotalPages());

            long totalCountArticle = articleService.countArticle();

            model.addAttribute("category", "전체글");
            model.addAttribute("totalCountArticle", totalCountArticle);
            model.addAttribute("articleList", articleList);
            model.addAttribute("paginationBarNumbers", barNumbers);
        }else {

            long totalCountArticle = articleService.countAllCategoryArticle(category);

            Page<ArticleDto> articleList = articleService.getAllCategoryArticle(category, pageable);
            List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articleList.getTotalPages());

            model.addAttribute("category", category);
            model.addAttribute("totalCountArticle", totalCountArticle);
            model.addAttribute("articleList", articleList);
            model.addAttribute("paginationBarNumbers", barNumbers);
        }

        return "/articles/articleList";

    }

    //새로운 게시글 저장
    @PostMapping
    public ResponseEntity<Long> newArticle(
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            @RequestBody ArticleRequest articleRequest
    ) {
        try {
            UserAccountDto userAccountDto = boardPrincipal.toDto();
            Long categoryId = Long.valueOf(articleRequest.categoryId());
            CategoryDto categoryDto = categoryService.getCategory(categoryId);

            ArticleDto articleDto = articleRequest.toDto(categoryDto, userAccountDto, articleRequest.title(), articleRequest.content());
            ArticleDto savedArticleDto = articleService.saveArticle(articleDto);

            return new ResponseEntity<>(savedArticleDto.id(), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //게시글 작성 폼
    @GetMapping("/form")
    public String articleForm(Model model, @Param(value = "category") String category) {
        List<CategoryDto> allCategories = categoryService.getAllCategories();
        List<CategoryDto> sortedAllCategories = allCategories.stream().sorted(Comparator.comparing(CategoryDto::priority)).collect(Collectors.toList());

        model.addAttribute("currentCategory", category);
        model.addAttribute("allCategories", sortedAllCategories);
        model.addAttribute("formStatus", "게시글 신규");
        return "/articles/articleForm";
    }

    //게시글 보기
    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId, Model model) {

        ArticleDto article = articleService.getArticle(articleId);

        model.addAttribute("article", article);

        return "/articles/articleView";
    }

    //게시글 수정 폼
    @GetMapping("/{articleId}/form")
    public String editForm(@PathVariable Long articleId, Model model) {
        List<CategoryDto> allCategories = categoryService.getAllCategories();
        List<CategoryDto> sortedAllCategories = allCategories.stream().sorted(Comparator.comparing(CategoryDto::priority)).collect(Collectors.toList());

        ArticleDto articleDto = articleService.getArticle(articleId);
        ArticleResponse response = ArticleResponse.from(articleDto);

        model.addAttribute("currentCategory", articleDto.categoryDto().categoryName());
        model.addAttribute("allCategories", sortedAllCategories);
        model.addAttribute("response", response);
        model.addAttribute("formStatus", "게시글 수정");

        return "/articles/articleForm";
    }

    //게시글 수정
    @PatchMapping("/{articleId}")
    public ResponseEntity<Long> editArticle(
            @PathVariable Long articleId,
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            @RequestBody ArticleRequest articleRequest
            ) {

        try {
            UserAccountDto userAccountDto = boardPrincipal.toDto();
            Long categoryId = Long.valueOf(articleRequest.categoryId());
            CategoryDto categoryDto = categoryService.getCategory(categoryId);

            ArticleDto articleDto = articleRequest.toDto(categoryDto, userAccountDto, articleRequest.title(), articleRequest.content());
            articleService.updateArticle(articleId, articleDto);
            return new ResponseEntity<>(articleId, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //게시글 삭제
    @DeleteMapping("/{articleId}")
    public ResponseEntity<String> delete(@PathVariable Long articleId) {

        try{
            ArticleDto article = articleService.getArticle(articleId);
            String categoryName = article.categoryDto().categoryName();
            articleService.deleteArticle(articleId);

            return new ResponseEntity<>(categoryName, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
