package myCollectionService.dataBaseService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import myCollectionService.dataBaseEntitys.Instance;

import java.util.List;

public interface InstanceRepository extends JpaRepository<Instance, Long>{

    //из списка экземпляров коллекции, id которой передается
    // вернуть список экземпляров, у которых в назнании есть переденная строка
    @Query("SELECT c FROM Instance c where c.name like:search and c.myCollection.id =:collection_id")
    List<Instance> findCurrentCollectionsInstancesBySearchName(@Param("search")String search, @Param("collection_id")Long collection_id);
}
