package myCollectionService.dataBaseEntitys;

import javax.persistence.*;

//Like
@Entity //Entity from data base
public class DbLike {
    @Id
    @GeneratedValue
    private long id;

    //Instance of this Like
    @JoinColumn(name="instance_id") //Column name in table DbLike is "instance_id"
    @ManyToOne(fetch = FetchType.LAZY)
    private Instance instance;//Instance

    //Collection of this Like
    @JoinColumn(name="collection_id")
    @ManyToOne(fetch = FetchType.LAZY) //Column name in table DbLike is "collection_id"
    private MyCollection collection;//Collection

    //Collector of this Like
    @JoinColumn(name="collector_id")
    @ManyToOne(fetch = FetchType.LAZY) //Column name in table DbLike is "collector_id"
    private Collector collector; //Collector

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
