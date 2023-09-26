package seungmi.myblog.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ToString(callSuper = true)
@Entity
@Getter
public class Category extends CommonAttributes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Setter
    @Column(nullable = false)
    private String categoryName;    //카테고리 이름

    @ToString.Exclude
    @Setter
    @OneToMany(mappedBy = "category")
    private List<Article> articles = new ArrayList<>();

    @Setter
    @Column(nullable = false)
    private int priority;   //우선순위

    protected Category() {
    }

    private Category(String categoryName, int priority) {
        this.categoryName = categoryName;
        this.priority = priority;
    }

    public static Category of(String categoryName, int priority) {
        return new Category(categoryName, priority);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
