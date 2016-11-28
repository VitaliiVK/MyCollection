package myCollectionService.dataBaseService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import myCollectionService.dataBaseEntitys.Instance;

import java.util.List;

//methods for work with Instance table
public interface InstanceRepository extends JpaRepository<Instance, Long>{

    // for search form on "view collection page"
    // get Instance list(from Instance table) from collection with requested id,
    // where field "name" like requested String
    @Query("SELECT c FROM Instance c where c.name like:search and c.myCollection.id =:collection_id")
    List<Instance> findCurrentCollectionsInstancesBySearchName(@Param("search")String search, @Param("collection_id")Long collection_id);
}
