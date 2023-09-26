package seungmi.myblog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seungmi.myblog.domain.Article;
import seungmi.myblog.domain.Category;
import seungmi.myblog.domain.UserAccount;
import seungmi.myblog.dto.ArticleDto;
import seungmi.myblog.repository.ArticleRepository;
import seungmi.myblog.repository.CategoryRepository;
import seungmi.myblog.repository.UserAccountRepository;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;
    private final CategoryRepository categoryRepository;


    //Article 단건 조회
    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow(()->new EntityNotFoundException("게시글을 찾을 수 없습니다. Article id = " + articleId));
    }

    //모든 Article 조회
    public Page<ArticleDto> getAllArticle(Pageable pageable) {
        return articleRepository.findAll(pageable).map(ArticleDto::from);
    }

    //특정 카테고리의 Article 조회
    public Page<ArticleDto> getAllCategoryArticle(String categoryName, Pageable pageable) {
        Category findCategory = categoryRepository.findByCategoryName(categoryName);
        Long categoryId = findCategory.getId();

        return articleRepository.findByCategoryId(categoryId, pageable).map(ArticleDto::from);
    }

    //특정 카테고리의 Article 개수 조회
    public int countAllCategoryArticle(String categoryName) {
        Category findCategory = categoryRepository.findByCategoryName(categoryName);
        Long categoryId = findCategory.getId();

        return articleRepository.countAllByCategoryId(categoryId);
    }


    //Article 저장
    public ArticleDto saveArticle(ArticleDto articleDto) {
        Category category = categoryRepository.getReferenceById(articleDto.categoryDto().id());
        UserAccount userAccount = userAccountRepository.getReferenceById(articleDto.userAccountDto().userId());
        Article savedArticle = articleRepository.save(articleDto.toEntity(category, userAccount));
        return ArticleDto.from(savedArticle);
    }
    
    //Article 수정
    public void updateArticle(Long articleId, ArticleDto articleDto) {
        try {
            Article article = articleRepository.getReferenceById(articleId);
            Category category = categoryRepository.getReferenceById(articleDto.categoryDto().id());
            UserAccount userAccount = userAccountRepository.getReferenceById(articleDto.userAccountDto().userId());

                if (articleDto.title() != null) {
                    article.setTitle(articleDto.title());
                }
                if (articleDto.content() != null) {
                    article.setContent(articleDto.content());
                }
                article.setCategory(category);

        } catch (EntityNotFoundException e) {
            log.warn("게시글 수정 실패. 게시글을 수정하는데 필요한 정보를 찾을 수 없습니다 - {}", e.getLocalizedMessage());
        }
    }

    //Article 삭제
    public void deleteArticle(Long articleId) {
        Article article = articleRepository.getReferenceById(articleId);

            articleRepository.deleteById(articleId);
    }

    //모든 Article 개수 조회
    public long countArticle() {
        return articleRepository.count();
    }


}
