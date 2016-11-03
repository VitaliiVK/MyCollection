package myCollectionService.dataBaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import myCollectionService.dataBaseEntitys.*;

import java.util.List;

// в этом классе мы реализуем методы необходимые нам для работы с базой данных

// реализацию осуществляем с помощью интрефейса Repository реализованного для каждой таблицей с которой будем работать,
// и содержащего готовые методы для работы с таблицей и методы которые мы прописали и связали с конкретными jpql запросами

@Service // Spring анотация, производная от @component, спринг атоматически создаст бин DbServiceImpl и положит в контекст
public class DbServiceImpl implements DbService {

    //автоматическая инициализация репозиториев
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

    //методы для работы с коллекционером

    @Override
    @Transactional(readOnly = true) //заворачиваем все происходящее в методе в транзакцию, только чтение
    public Collector getCollectorByLogin(String login) {
        return collectorRepository.findByLogin(login); //метод который реазизовали мы
    }

    @Override
    @Transactional(readOnly = true) //заворачиваем все происходящее в методе в транзакцию, только чтение
    public Collector getCollectorById(long id){
        return  collectorRepository.getOne(id);
        // .getOne()готовый метод который достался CollectorRepository по наследству от JpaRepository
    }

    @Override
    @Transactional(readOnly = true)
    public boolean loginIsExist(String login) {
        return collectorRepository.findByLogin(login)!=null;
    }

    @Override
    @Transactional
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

    //методы для работы с коллекцией

    @Override
    @Transactional
    public void addCollection(MyCollection collection) { //добавсить коллекцию
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

    //методя для работы с экземплярами

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

    //методы для работы с лайками

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

    //методы для работы с комментариями

    @Override
    @Transactional
    public void addComment(Comment comment){
        commentRepository.save(comment);
    }

}
