package seungmi.myblog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import seungmi.myblog.domain.Article;

@RepositoryRestResource
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findByCategoryId(Long categoryId, Pageable pageable);

    int countAllByCategoryId(long categoryId);
}
