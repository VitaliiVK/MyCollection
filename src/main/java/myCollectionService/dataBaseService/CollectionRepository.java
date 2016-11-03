package myCollectionService.dataBaseService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import myCollectionService.dataBaseEntitys.MyCollection;

import java.util.List;

public interface CollectionRepository extends JpaRepository<MyCollection, Long> {

    //вернуть список коллекций, у которых в назнании есть переденная строка
    @Query("SELECT c FROM MyCollection c where c.name like:search")
    List<MyCollection> findCollectionsBySearchName(@Param("search")String search);

    //из списка поллекций пользователя, id котогоро передается
    // вернуть список коллекций, у которых в назнании есть переденная строка
    @Query("SELECT c FROM MyCollection c where c.name like:search and c.collector.id =:user_id")
    List<MyCollection> findCurrentUserCollectionsBySearchName(@Param("search")String search, @Param("user_id")Long user_id);

}