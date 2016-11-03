package myCollectionService.dataBaseEntitys;

import javax.persistence.*;

//комментарий
@Entity //сущность из базы данных
public class Comment {
    @Id
    @GeneratedValue
    private long id; // колонка id с автогенерацией

    @JoinColumn(name="instance_id") //имя колонки связанной колонки
    @ManyToOne(fetch = FetchType.LAZY) //тип связи ManyToOne, загрузка LAZY
    private Instance instance;//экземпляр

    @JoinColumn(name="collection_id")
    @ManyToOne(fetch = FetchType.LAZY) //тип связи ManyToOne, загрузка LAZY
    private MyCollection collection;//коллекция

    @ManyToOne(fetch = FetchType.LAZY) //тип связи ManyToOne, загрузка LAZY
    @JoinColumn(name="collector_id") //имя колонки связанной колонки
    private Collector collector; //коллекционер

    private String text; // текст комментария

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
