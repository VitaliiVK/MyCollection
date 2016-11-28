package myCollectionService.dataBaseService;

import myCollectionService.dataBaseEntitys.*;

import java.util.List;

//in this interface describe all methods for work with data base
public interface DbService {

    //methods for work with Collector table
    Collector getCollectorByLogin(String login);
    Collector getCollectorById(long id);
    boolean loginIsExist(String login);
    void addCollector(Collector collector);
    void updateCollector(Collector collector);
    List<Collector> getCollectorsList();
    List<Collector> findCollectorsBySearchLogin(String search);

    //methods for work with MyCollection table
    void addCollection(MyCollection collection);
    void updateCollection(MyCollection collection);
    void removeCollection(MyCollection collection);
    MyCollection getCollectionById(long id);
    List<MyCollection> getCollectionsList();
    List<MyCollection> findCollectionsBySearchName(String search);
    List<MyCollection> findCurrentUserCollectionsBySearchName(String search, long id);

    //methods for work with Instance table
    void addInstance(Instance instance);
    void updateInstance(Instance instance);
    void removeInstance(Instance instance);
    Instance getInstanceById(long id);
    List<Instance> findCurrentCollectionsInstancesBySearchName(String search, long id);

    //methods for work with DbLike table
    void addLike(DbLike like);
    void removeLike(Collector collector, String instance_or_collection, long id);
    boolean isExistCollectionLike(Collector collector, long collection_id);
    boolean isExistInstanceLike(Collector collector, long instance_id);

    //methods for work with Comment table
    void addComment(Comment comment);
}
