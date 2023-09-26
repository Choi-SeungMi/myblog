package seungmi.myblog.dto;

import seungmi.myblog.domain.Category;

import java.time.LocalDateTime;

public record CategoryDto (
        Long id,
        String categoryName,
        int priority,
        int countArticle,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime lastModifiedAt,
        String lastModifiedBy
){
    public static CategoryDto from(Category entity) {

            return new CategoryDto(
                    entity.getId(),
                    entity.getCategoryName(),
                    entity.getPriority(),
                    entity.getArticles().size(),
                    entity.getCreatedAt(),
                    entity.getCreatedBy(),
                    entity.getLastModifiedAt(),
                    entity.getLastModifiedBy()
            );
    }

    public static CategoryDto of(
            Long id,
            String categoryName,
            int priority,
            int countArticle,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime lastModifiedAt,
            String lastModifiedBy
    ) {
        return new CategoryDto(
                id,
                categoryName,
                priority,
                countArticle,
                createdAt,
                createdBy,
                lastModifiedAt,
                lastModifiedBy
        );
    }

    public static CategoryDto of(
            Long id,
            String categoryName,
            int priority

    ) {
        return new CategoryDto(
                id,
                categoryName,
                priority,
                0,
                null,
                null,
                null,
                null
        );
    }

    public Category toEntity() {
        return Category.of(categoryName, priority);
    }

}
