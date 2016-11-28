package myCollectionService.dataBaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import myCollectionService.dataBaseEntitys.*;

import java.util.List;

// this class implements all method for work with data base
// use objects Class extends JpaRepository for each table in the database
// this Class contain standard methods for work with data base

@Service // Spring annotation,  derived from @Component
// Spring will automatically create the bin DbServiceImpl
public class DbServiceImpl implements DbService {

    @Autowired
    private CollectorRepository collectorRepository;
    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private InstanceRepository instanceRepository;
    @Autowired
    private LikeRepository  likeRepository;
    @Autowired
    private CommentRepository  commentRepository;

    //methods for work with Collector table

    @Override
    @Transactional(readOnly = true) //make transaction for read
    public Collector getCollectorByLogin(String login) {
        return collectorRepository.findByLogin(login); //custom method in CollectorRepository
    }

    @Override
    @Transactional(readOnly = true)
    public Collector getCollectorById(long id){
        return  collectorRepository.getOne(id);
        // .getOne() standard methods for work with data base inherited CollectorRepository from JpaRepository
    }

    @Override
    @Transactional(readOnly = true)
    public boolean loginIsExist(String login) {
        return collectorRepository.findByLogin(login)!=null;
    }

    @Override
    @Transactional //make transaction
    public void addCollector(Collector collector) {
        collectorRepository.save(collector);
    }

    @Override
    @Transactional
    public void updateCollector(Collector collector) {
        collectorRepository.save(collector);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Collector> getCollectorsList() {
        return collectorRepository.findAll();
    }

    //methods for work with MyCollection table

    @Override
    @Transactional
    public void addCollection(MyCollection collection) {
        collectionRepository.save(collection);
    }

    @Override
    @Transactional
    public void updateCollection(MyCollection collection){
        collectionRepository.save(collection);
    }

    @Override
    @Transactional
    public void removeCollection(MyCollection collection){
        collectionRepository.delete(collection);
    }

    @Override
    @Transactional
    public MyCollection getCollectionById(long id){
        return collectionRepository.getOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MyCollection> getCollectionsList() {
        return collectionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Collector> findCollectorsBySearchLogin(String search) {
        return collectorRepository.findCollectorsBySearchLogin("%" +search+ "%");
    }

    @Override
    @Transactional(readOnly = true)
    public List<MyCollection> findCollectionsBySearchName(String search) {
        return collectionRepository.findCollectionsBySearchName("%" +search+ "%");
    }

    @Override
    @Transactional(readOnly = true)
    public List<MyCollection> findCurrentUserCollectionsBySearchName(String search, long id) {
        return collectionRepository.findCurrentUserCollectionsBySearchName("%" +search+ "%", id);
    }

    //methods for work with Instance table

    @Override
    @Transactional
    public void addInstance(Instance instance) {
        instanceRepository.save(instance);
    }

    @Override
    @Transactional
    public void updateInstance(Instance instance){
        instanceRepository.save(instance);
    }

    @Override
    @Transactional
    public void removeInstance(Instance instance){
        instanceRepository.delete(instance);
    }

    @Override
    @Transactional
    public Instance getInstanceById(long id){
        return instanceRepository.getOne(id);
    }

    @Override
    @Transactional
    public List<Instance> findCurrentCollectionsInstancesBySearchName(String search, long id){
        return instanceRepository.findCurrentCollectionsInstancesBySearchName("%" +search+ "%", id);
    }

    //methods for work with DbLike table

    @Override
    @Transactional
    public void addLike(DbLike like){
        likeRepository.save(like);
    }

    @Override
    @Transactional
    public void removeLike(Collector collector, String instance_or_collection, long id){
        long collector_id = collector.getId();
        if(instance_or_collection.equals("instance")){
            DbLike like = likeRepository.findInstanceLikeByInstanceIdAndUserId(collector_id, id);
            likeRepository.delete(like);
        }
        else{
            DbLike like = likeRepository.findCollectionLikeByInstanceIdAndUserId(collector_id, id);
            likeRepository.delete(like);
        }
    }

    @Override
    @Transactional
    public boolean isExistCollectionLike(Collector collector, long collection_id){
        long collector_id = collector.getId();
        DbLike like = likeRepository.findCollectionLikeByInstanceIdAndUserId(collector_id, collection_id);
        return like != null;
    }

    @Override
    @Transactional
    public boolean isExistInstanceLike(Collector collector, long instance_id){
        long collector_id = collector.getId();
        DbLike like = likeRepository.findInstanceLikeByInstanceIdAndUserId(collector_id, instance_id);
        return like != null;
    }

    //methods for work with Comment table

    @Override
    @Transactional
    public void addComment(Comment comment){
        commentRepository.save(comment);
    }

}
