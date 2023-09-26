package seungmi.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import seungmi.myblog.domain.UserAccount;

@RepositoryRestResource
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
}
