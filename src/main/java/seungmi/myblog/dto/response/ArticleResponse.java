package seungmi.myblog.dto.response;

import seungmi.myblog.dto.ArticleDto;

public record ArticleResponse (
        Long id,
        String categoryName,
        String title,
        String content
){

    public static ArticleResponse from(ArticleDto articleDto) {
        return new ArticleResponse(articleDto.id(), articleDto.categoryDto().categoryName(), articleDto.title(), articleDto.content());
    }

}
