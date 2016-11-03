package myCollectionService.dataBaseService;

import myCollectionService.dataBaseEntitys.*;

import java.util.List;

//инетрфейс в котором описавны все методы для работы с нашей базой данных
public interface DbService {

    //методы для работы с коллекционером
    Collector getCollectorByLogin(String login);
    Collector getCollectorById(long id);
    boolean loginIsExist(String login);
    void addCollector(Collector collector);
    void updateCollector(Collector collector);
    List<Collector> getCollectorsList();
    List<Collector> findCollectorsBySearchLogin(String search);

    //методы для работы с коллекцией
    void addCollection(MyCollection collection);
    void updateCollection(MyCollection collection);
    void removeCollection(MyCollection collection);
    MyCollection getCollectionById(long id);
    List<MyCollection> getCollectionsList();
    List<MyCollection> findCollectionsBySearchName(String search);
    List<MyCollection> findCurrentUserCollectionsBySearchName(String search, long id);

    //методы для работы с экземплярами
    void addInstance(Instance instance);
    void updateInstance(Instance instance);
    void removeInstance(Instance instance);
    Instance getInstanceById(long id);
    List<Instance> findCurrentCollectionsInstancesBySearchName(String search, long id);

    //методы для работы с лайками
    void addLike(DbLike like);
    void removeLike(Collector collector, String instance_or_collection, long id);
    //методы для работы с комментариями

    //методы для работы с лайками
    void addComment(Comment comment);
}
