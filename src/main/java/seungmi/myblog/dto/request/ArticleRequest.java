package seungmi.myblog.dto.request;

import seungmi.myblog.dto.ArticleDto;
import seungmi.myblog.dto.CategoryDto;
import seungmi.myblog.dto.UserAccountDto;

public record ArticleRequest (
        String title,
        String content,
        String categoryId
) {

    public static ArticleRequest of(String title, String content, String category) {
        return new ArticleRequest(title, content, category);
    }

    public ArticleDto toDto(
            CategoryDto categoryDto,
            UserAccountDto userAccountDto,
            String title,
            String content

    ) {
        return ArticleDto.of(categoryDto, userAccountDto, title, content);
    }



}
