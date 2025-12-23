package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글의 모든 댓글 조회
    // 네이티브 쿼리 메서드를 만드는 방법1 = @Query 어노테이션 이용
    @Query(value = "SELECT * FROM comment WHERE article_id = :articleId", nativeQuery = true)
    List<Comment> findByArticleId(Long articleId);

    // 특정 게시글의 댓글 개수를 세어주는 쿼리 메서드
    int countByArticleId(Long articleId);

    // 특정 닉네임의 모든 댓글 조회
    // 네이티브 쿼리 메서드를 만드는 방법2 - orm.xml 파일 이용
    List<Comment> findByNickname(String nickname);
}
