package myCollectionService.dataBaseService;

import org.springframework.data.jpa.repository.JpaRepository;
import myCollectionService.dataBaseEntitys.Comment;

//methods for work with Comment table
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
