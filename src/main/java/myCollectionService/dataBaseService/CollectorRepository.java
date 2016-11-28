package myCollectionService.dataBaseService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import myCollectionService.dataBaseEntitys.Collector;

import java.util.List;

//methods for work with Collector table
public interface CollectorRepository extends JpaRepository<Collector, Long> {

    //get collector(from Collector table) with required name
    @Query("SELECT u FROM Collector u where u.login = :login")
    Collector findByLogin(@Param("login") String login);

    // for search form on "view collectors list page"
    //get collectors List(from Collector table) where name like required String
    @Query("SELECT c FROM Collector c where c.login like:search")
    List<Collector> findCollectorsBySearchLogin(@Param("search")String search);

}