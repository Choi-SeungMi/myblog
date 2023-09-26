package seungmi.myblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import seungmi.myblog.dto.CategoryDto;
import seungmi.myblog.dto.request.CategoryRequest;
import seungmi.myblog.service.CategoryService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    //카테고리 수정 폼
    @GetMapping
    public String categories(Model model) {

        List<CategoryDto> allCategories = categoryService.getAllCategories();
        List<CategoryDto> sortedAllCategories = allCategories.stream().sorted(Comparator.comparing(CategoryDto::priority)).collect(Collectors.toList());

        model.addAttribute("allCategories", sortedAllCategories);

        return "/category/category-edit";
    }

    //카테고리 수정
    @PostMapping
    public ResponseEntity<String> edit(@RequestBody CategoryRequest[] CategoryRequests) {

        List<CategoryDto> list = new ArrayList<>();

        try {
            for (CategoryRequest request : CategoryRequests) {
                CategoryDto categoryDto = request.toDto(request.id(), request.categoryName(), request.priority());
                list.add(categoryDto);
            }

            List<CategoryDto> sortedList = list.stream().sorted(Comparator.comparing(CategoryDto::id)).collect(Collectors.toList());
            categoryService.updateCategoryList(sortedList);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
        }

    }




}
