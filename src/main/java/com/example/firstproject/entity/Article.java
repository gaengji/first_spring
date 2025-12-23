package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity // JPA에서 제공하는 어노테이션으로 클래스를 기반으로 DP에 테이블을 생성해준다, 이떄 테이블 이름은 클래스 이름과 동일하다
@AllArgsConstructor
@NoArgsConstructor // 기본 생성자
@ToString
@Getter
public class Article {

    @Id // 대표값 지정!! DB에서 배운 primary key에 해당
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1, 2, 3 ... 자동생성
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private Integer views = 0;

    public void increaseViews() {
        if (this.views == null) {
            this.views = 0;
        }
        this.views++;
    }

    public void patch(Article article) {
        if (article.title != null) {
            this.title = article.title;
        }
        if (article.content != null) {
            this.content = article.content;
        }
    }
}

/* create table Article (
    id long primary key,
    title varchar2(),
    content varchar2()
);
*/