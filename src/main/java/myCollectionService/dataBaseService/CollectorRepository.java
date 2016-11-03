package myCollectionService.dataBaseService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import myCollectionService.dataBaseEntitys.Collector;

import java.util.List;

public interface CollectorRepository extends JpaRepository<Collector, Long> {

    //метод который вернет обьект класса пользователь по переданному в параметрах логину
    @Query("SELECT u FROM Collector u where u.login = :login")
    Collector findByLogin(@Param("login") String login);

    //вернуть список коллекционеров, у которых в назнании есть переденная строка
    @Query("SELECT c FROM Collector c where c.login like:search")
    List<Collector> findCollectorsBySearchLogin(@Param("search")String search);

}