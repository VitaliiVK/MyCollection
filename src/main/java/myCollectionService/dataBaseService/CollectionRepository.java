package myCollectionService.dataBaseService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import myCollectionService.dataBaseEntitys.MyCollection;

import java.util.List;

//methods for work with MyCollection table
public interface CollectionRepository extends JpaRepository<MyCollection, Long> {

    // for search form on "view collection list page"
    //get Collection List(from MyCollection table) where name like required String
    @Query("SELECT c FROM MyCollection c where c.name like:search")
    List<MyCollection> findCollectionsBySearchName(@Param("search")String search);

    // for search form on "view collection list page"
    // get Collection list(from MyCollection table) from collector with requested id,
    // where field "name" like requested String
    @Query("SELECT c FROM MyCollection c where c.name like:search and c.collector.id =:user_id")
    List<MyCollection> findCurrentUserCollectionsBySearchName(@Param("search")String search, @Param("user_id")Long user_id);

}