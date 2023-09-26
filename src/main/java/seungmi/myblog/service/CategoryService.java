package seungmi.myblog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seungmi.myblog.domain.Article;
import seungmi.myblog.domain.Category;
import seungmi.myblog.dto.CategoryDto;
import seungmi.myblog.repository.ArticleRepository;
import seungmi.myblog.repository.CategoryRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;

    //이름으로 카테고리 조회
    public CategoryDto getCategoryByName(String categoryName) {
        Category findCategory = categoryRepository.findByCategoryName(categoryName);
        return CategoryDto.from(findCategory);
    }

    //모든 카테고리 이름 조회
    public List<String> getAllCategoryNames() {
        List<String> list = new ArrayList<>();

        List<CategoryDto> allCategories = getAllCategories();
        for (CategoryDto allCategory : allCategories) {
            list.add(allCategory.categoryName());
        }
        return list;
    }

    //모든 카테고리 id 조회
    public List<Long> getCategoryIdList(List<CategoryDto> dtoList) {
        List<Long> list = new ArrayList<>();

        for (CategoryDto categoryDto : dtoList) {
            list.add(categoryDto.id().longValue());
        }

        return list;
    }

    //카테고리 단건 조회
    public CategoryDto getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(CategoryDto::from)
                .orElseThrow(()->new EntityNotFoundException("카테고리를 찾을 수 없습니다. categoryId = " + categoryId));
    }

    //모든 카테고리 조회
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream().map(CategoryDto::from)
                .toList();
    }

    //카테고리 저장
    public void saveCategory(CategoryDto categoryDto) {
        categoryRepository.save(categoryDto.toEntity());
    }

    //카테고리 수정
    public void updateCategory(CategoryDto categoryDto) {

        try {
            Category category = categoryRepository.getReferenceById(categoryDto.id());

            category.setCategoryName(categoryDto.categoryName());
            category.setPriority(categoryDto.priority());

        } catch (EntityNotFoundException e) {
            saveCategory(categoryDto);
        }
    }

    //특정 카테고리의 모든 Article의 카테고리를 전체글로 변경
    public void changeCategoryToBase(CategoryDto categoryDto) {

        Category baseCategory = categoryRepository.findByCategoryName("전체글");
        Category entity = categoryRepository.getReferenceById(categoryDto.id());

        List<Article> articles = entity.getArticles();

        if (articles.size() != 0) {
            for (Article article : articles) {
                article.setCategory(baseCategory);
            }
        }
    }

    //특정 카테고리의 모든 Article과 카테고리를 삭제
    public void deleteCategoryWithArticle(CategoryDto categoryDto) {
        Category entity = categoryRepository.getReferenceById(categoryDto.id());

        List<Article> articles = entity.getArticles();

        if (articles.size() != 0) {
            for (Article article : articles) {
                articleRepository.deleteById(article.getId());
            }
        }

        deleteCategory(categoryDto);

    }

    //카테고리 삭제
    public void deleteCategory(CategoryDto categoryDto) {
        categoryRepository.deleteById(categoryDto.id());
    }
    
    //카테고리 리스트 수정
    public void updateCategoryList(List<CategoryDto> categoryDtos) {

        List<Long> preCategoryIdList = getCategoryIdList(getAllCategories());
        List<Long> newcategoryIdList = getCategoryIdList(categoryDtos);

        //기존 DB에 있던 카테고리 중에 삭제된 카테고리 확인하여 DB에서 삭제
        for (Long preCategoryId : preCategoryIdList) {

            boolean isContains = newcategoryIdList.contains(preCategoryId);

            if (!isContains) {
                CategoryDto categoryDto = getCategory(preCategoryId);
                deleteCategoryWithArticle(categoryDto);
            }
        }

        //기존 카테고리 정보 수정, 추가 카테고리 저장
        for (CategoryDto categoryDto : categoryDtos) {

            updateCategory(categoryDto);
        }
    }


        

}
