package seungmi.myblog.dto.request;

import seungmi.myblog.dto.CategoryDto;

public record CategoryRequest (
        String id,
        String categoryName,
        String priority
) {

    public CategoryDto toDto(
            String id,
            String categoryName,
            String priority
    ) {
        return CategoryDto.of(Long.valueOf(id), categoryName, Integer.valueOf(priority));
    }

}
