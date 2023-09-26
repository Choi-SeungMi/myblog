package seungmi.myblog.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CommonAttributes {

    //공통 메타데이터
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false, updatable = false)
    @CreatedDate
    protected LocalDateTime createdAt;    //생성일시


    @Column(nullable = false, updatable = false, length = 100)
    @CreatedBy
    protected String createdBy;   //생성자

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false)
    @LastModifiedDate
    protected LocalDateTime lastModifiedAt;   //수정일시


    @Column(nullable = false, length = 100)
    @LastModifiedBy
    protected String lastModifiedBy;  //수정자

}
