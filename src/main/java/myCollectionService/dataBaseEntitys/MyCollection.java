package myCollectionService.dataBaseEntitys;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//Collection
@Entity //Entity from data base, table name is "MyCollection"
public class MyCollection implements EntityForLikeAndComment {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String type;
    private String information;

    @Column(columnDefinition = "LONGBLOB")
    private byte[] photo;

    //Collector of this Collection
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="collector_id") // Column name in table
    private Collector collector;

    //Instances List of this Collection
    @OneToMany(mappedBy = "myCollection", cascade = CascadeType.ALL) //field name in Instance Class is "myCollection"
    private List<Instance> instances = new ArrayList<>(); // Instances List

    //тип связи OneToMany, сопоставляется с полем по имени "collection"
    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL) //field name in DbLike Class is "collection"
    private List<DbLike> likeList = new ArrayList<>(); // Like List

    //тип связи OneToMany, сопоставляется с полем по имени "collection"
    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL) //field name in DbLike Comment is "collection"
    private List<Comment> commentsList = new ArrayList<>(); // Comment List

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
