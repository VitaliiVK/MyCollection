package myCollectionService.dataBaseEntitys;

import javax.persistence.*;

//лайк
@Entity //сущность из базы данных
public class DbLike {
    @Id
    @GeneratedValue
    private long id; // колонка id с автогенерацией

    @JoinColumn(name="instance_id") //имя колонки связанной колонки
    @ManyToOne(fetch = FetchType.LAZY) //тип связи ManyToOne, загрузка LAZY
    private Instance instance;//коллекционер

    @JoinColumn(name="collection_id")
    @ManyToOne(fetch = FetchType.LAZY) //тип связи ManyToOne, загрузка LAZY
    private MyCollection collection;//коллекция

    @JoinColumn(name="collector_id") //имя колонки связанной колонки
    @ManyToOne(fetch = FetchType.LAZY) //тип связи ManyToOne, загрузка LAZY
    private Collector collector; //коллекционер

    public DbLike() {
    }

    public DbLike(Collector collector, EntityForLikeAndComment instanceOrCollection) {
        this.collector = collector;
        if(instanceOrCollection instanceof Instance){
            instance = (Instance) instanceOrCollection;
        }
        else{
            collection = (MyCollection) instanceOrCollection;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Instance getInstance() {
        return instance;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    public MyCollection getCollection() {
        return collection;
    }

    public void setCollection(MyCollection collection) {
        this.collection = collection;
    }

    public Collector getCollector() {
        return collector;
    }

    public void setCollector(Collector collector) {
        this.collector = collector;
    }


}
