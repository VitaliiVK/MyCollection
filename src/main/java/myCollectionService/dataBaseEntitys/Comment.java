package myCollectionService.dataBaseEntitys;

import javax.persistence.*;

//Comment
@Entity //Entity from data base
public class Comment {
    @Id
    @GeneratedValue
    private long id;

    //Instance of this comment
    @JoinColumn(name="instance_id") //Column name in table Comment is "instance_id"
    @ManyToOne(fetch = FetchType.LAZY)
    private Instance instance;//Instance

    //Collection of this comment
    @JoinColumn(name="collection_id") //Column name in table MyCollection is "collection_id"
    @ManyToOne(fetch = FetchType.LAZY)
    private MyCollection collection;//Collection

    //Collector of this comment
    @JoinColumn(name="collector_id") //Column name in table Collector is "collector_id"
    @ManyToOne(fetch = FetchType.LAZY)
    private Collector collector; //Collector

    private String text; // text of comment

    public Comment() {
    }

    public Comment(Collector collector, String text, EntityForLikeAndComment instanceOrCollection) {
        this.collector = collector;
        this.text = text;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
