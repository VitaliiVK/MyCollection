package myCollectionService.dataBaseEntitys;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//коллекция
@Entity //сущность из базы данных
public class MyCollection implements EntityForLikeAndComment {
    @Id
    @GeneratedValue
    private long id; // колонка id с автогенерацией

    private String name;
    private String type;
    private String information;

    @Column(columnDefinition = "LONGBLOB")
    private byte[] photo;

    @ManyToOne(fetch = FetchType.LAZY) //тип связи ManyToOne, загрузка LAZY
    @JoinColumn(name="collector_id") //имя колонки связанной колонки
    private Collector collector; //коллекционер

    //тип связи OneToMany, сопоставляется с полем по имени "myCollectio"
    @OneToMany(mappedBy = "myCollection", cascade = CascadeType.ALL)
    private List<Instance> instances = new ArrayList<>(); //список экземпляров коллекции
    //обратная свять - указано имя поля

    //тип связи OneToMany, сопоставляется с полем по имени "collection"
    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL)
    private List<DbLike> likeList = new ArrayList<>(); //список лайков к коллекции
    //обратная свять - указано имя поля

    //тип связи OneToMany, сопоставляется с полем по имени "collection"
    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL)
    private List<Comment> commentsList = new ArrayList<>(); //список комментариев к коллекции
    //обратная свять - указано имя поля

    public MyCollection() {
    }

    public MyCollection(String name, String type, Collector collector) {
        this.name = name;
        this.type = type;
        this.collector = collector;
    }

    public void addInstance(Instance instance) {
        instance.setMyCollection(this);
        instances.add(instance);
    }

    public void addLike(DbLike like) {
        like.setCollection(this);
        likeList.add(like);
    }

    public void addComment(Comment comment) {
        comment.setCollection(this);
        commentsList.add(comment);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Collector getCollector() {
        return collector;
    }

    public void setCollector(Collector collector) {
        this.collector = collector;
    }

    public List<Instance> getInstances() {
        return instances;
    }

    public void setInstances(List<Instance> instances) {
        this.instances = instances;
    }

    public List<DbLike> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<DbLike> likeList) {
        this.likeList = likeList;
    }

    public List<Comment> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comment> commentsList) {
        this.commentsList = commentsList;
    }
}
