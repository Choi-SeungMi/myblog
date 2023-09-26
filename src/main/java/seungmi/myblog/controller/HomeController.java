package seungmi.myblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import seungmi.myblog.dto.ArticleDto;
import seungmi.myblog.dto.CategoryDto;
import seungmi.myblog.service.ArticleService;
import seungmi.myblog.service.CategoryService;

import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class HomeController {

    private final ArticleService articleService;
    private final CategoryService categoryService;

    @GetMapping
    public String home(@PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        //카테고리
        List<CategoryDto> allCategories = categoryService.getAllCategories();
        List<CategoryDto> sortedAllCategories = allCategories.stream().sorted(Comparator.comparing(CategoryDto::priority)).collect(Collectors.toList());
        long countArticle = articleService.countArticle();

        model.addAttribute("allCategories", sortedAllCategories);
        model.addAttribute("totalCountArticle", countArticle);

        //게시글
        Page<ArticleDto> allArticle = articleService.getAllArticle(pageable);
        Map<Long, String> subTitle = new HashMap<>();


        for (ArticleDto articleDto : allArticle) {
            String editedSubTitle;
            //HTML 태그 삭제 정규식
            String withoutTagContent = articleDto.content().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");

            //subTitle을 content의 50자 이내로 편집
            if (withoutTagContent.length() > 50) {

                if (withoutTagContent.indexOf(".") < 50) {
                    editedSubTitle = withoutTagContent.substring(0, withoutTagContent.indexOf(".") + 1);
                    subTitle.put(articleDto.id(), editedSubTitle);
                } else {
                    editedSubTitle = withoutTagContent.substring(0, 50) + "...";
                    subTitle.put(articleDto.id(), editedSubTitle);
                }
            } else {
                subTitle.put(articleDto.id(), withoutTagContent);
            }
        }

        model.addAttribute("articles", allArticle);
        model.addAttribute("subTitle", subTitle);

        return "index";
    }


}
