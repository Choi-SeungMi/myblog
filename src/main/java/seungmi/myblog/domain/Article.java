package seungmi.myblog.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.FetchType.*;

@ToString(callSuper = true)
@Entity
@Getter
public class Article extends CommonAttributes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    @Setter
    private Category category;  //카테고리

    @Setter
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;    //유저 정보

    @Setter
    @Column(nullable = false)
    private String title;   //제목

    @Setter
    @Column(nullable = false, length = 1000000)
    private String content; //본문

    protected Article() {
    }

    private Article(Category category, UserAccount userAccount, String title, String content) {
        this.category = category;
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
    }

    public static Article of(Category category, UserAccount userAccount, String title, String content) {
        return new Article(category, userAccount, title, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(getId(), article.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
