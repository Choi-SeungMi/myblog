package seungmi.myblog.dto;

import seungmi.myblog.domain.UserAccount;

import java.time.LocalDateTime;

public record UserAccountDto (
        String userId,
        String userPassword,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime lastModifiedAt,
        String lastModifiedBy
){
    public static UserAccountDto from(UserAccount entity) {
        return new UserAccountDto(
                entity.getUserId(),
                entity.getUserPassword(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getLastModifiedAt(),
                entity.getLastModifiedBy()
        );
    }

    public static UserAccountDto of(String userId, String userPassword) {
        return new UserAccountDto(userId, userPassword,null, null, null, null);
    }

    public static UserAccountDto of(String userId, String userPassword, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new UserAccountDto(userId, userPassword, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public UserAccount toEntity(String userId, String userPassword, String createdBy) {
        return UserAccount.of(userId, userPassword, createdBy);
    }

}
