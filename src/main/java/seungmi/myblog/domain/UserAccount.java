package seungmi.myblog.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@ToString(callSuper = true)
public class UserAccount extends CommonAttributes {

    @Id
    @Column(name = "user_id", length = 50, nullable = false)
    private String userId;

    @Setter
    @Column(nullable = false)
    private String userPassword;

    protected UserAccount() {
    }

    private UserAccount(String userId, String userPassword, String createdBy) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.createdBy = createdBy;
        this.lastModifiedBy = createdBy;
    }

    public static UserAccount of(String userId, String userPassword, String createdBy) {
        return new UserAccount(userId, userPassword, createdBy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount account = (UserAccount) o;
        return Objects.equals(getUserId(), account.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }
}
