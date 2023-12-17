package com.example.carwash.repository.community;

import com.example.carwash.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * SELECT comment_id
     * FROM recipe.Comment
     * where commentList_recipe_id=1
     * order by comment_id desc
     * limit 1;
     *
     */
    @Query(value = "SELECT * \n" +
            "FROM carwash.Comment\n" +
            "where commentList_id=:id\n" +
            "and comment_id>:comment_id\n" +
            "order by comment_id\n" +
            "limit :count",nativeQuery = true)
    Optional<List<Comment>> paginate(@Param(value = "id")int id, @Param(value = "comment_id")int comment_id,
                                     @Param(value = "count")int count);

    @Query(value = "SELECT comment_id\n" +
            "FROM carwash.Comment\n" +
            "where commentList_id=:id\n" +
            "order by comment_id desc\n" +
            "limit 1;",nativeQuery = true)
    int getFinalId(@Param("id")int id);
}
