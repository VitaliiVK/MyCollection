package myCollectionService.dataBaseService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import myCollectionService.dataBaseEntitys.DbLike;

//methods for work with DbLike table
public interface LikeRepository extends JpaRepository<DbLike, Long> {
    //from DbLike table, Like for Instance_id from Collector_id
    @Query("SELECT c FROM DbLike c where c.collector.id =:collector_id and c.instance.id =:instance_id")
    DbLike findInstanceLikeByInstanceIdAndUserId(@Param("collector_id")long collector_id,@Param("instance_id")long instance_id);

    //from DbLike table, Like for MyCollection_id from Collector_id
    @Query("SELECT c FROM DbLike c where c.collector.id =:collector_id and c.collection.id =:collection_id")
    DbLike findCollectionLikeByInstanceIdAndUserId(@Param("collector_id")long collector_id,@Param("collection_id")long collection_id);
}
