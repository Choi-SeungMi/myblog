package seungmi.myblog.dto;

import seungmi.myblog.domain.Article;
import seungmi.myblog.domain.Category;
import seungmi.myblog.domain.UserAccount;

import java.time.LocalDateTime;

public record ArticleDto (
        Long id,
        CategoryDto categoryDto,
        UserAccountDto userAccountDto,
        String title,
        String content,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime lastModifiedAt,
        String lastModifiedBy
){
    public static ArticleDto from(Article entity) {
        return new ArticleDto(
                entity.getId(),
                CategoryDto.from(entity.getCategory()),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getTitle(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getLastModifiedAt(),
                entity.getLastModifiedBy()
                );
    }

    public static ArticleDto of(
            CategoryDto categoryDto,
            UserAccountDto userAccountDto,
            String title,
            String content
    ) {
        return new ArticleDto(
                null,
                categoryDto,
                userAccountDto,
                title,
                content,
                null,
                null,
                null,
                null
        );
    }

    public Article toEntity(Category category, UserAccount userAccount) {
        return Article.of(category, userAccount, title, content);
    }


}
