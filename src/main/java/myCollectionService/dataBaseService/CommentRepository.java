package myCollectionService.dataBaseService;

import org.springframework.data.jpa.repository.JpaRepository;
import myCollectionService.dataBaseEntitys.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
