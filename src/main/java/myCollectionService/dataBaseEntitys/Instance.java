package myCollectionService.dataBaseEntitys;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//экземпляр в коллекции
@Entity //сущность из базы данных
public class Instance implements EntityForLikeAndComment {
    @Id
    @GeneratedValue
    private long id; // колонка id с автогенерацией

    private String name;
    private String subtype;
    private String information;

    @Column(columnDefinition = "LONGBLOB")
    private byte[] photo;

    @ManyToOne(fetch = FetchType.LAZY) //тип связи ManyToOne, загрузка LAZY
    @JoinColumn(name="collection_id") //имя колонки связанной колонки
    private MyCollection myCollection; //коллекция к которой принадлежит экземпляр

    //тип связи OneToMany, сопоставляется с полем по имени "instance"
    @OneToMany(mappedBy = "instance", cascade = CascadeType.ALL)
    private List<DbLike> likeList = new ArrayList<>(); //список лайков экземпляра
    //обратная свять - указано имя поля

    //тип связи OneToMany, сопоставляется с полем по имени "instance"
    @OneToMany(mappedBy = "instance", cascade = CascadeType.ALL)
    private List<Comment> commentsList = new ArrayList<>(); //список коментариев к экземпляру
    //обратная свять - указано имя поля

    public Instance() {
    }

    public Instance(String name, MyCollection myCollection) {
        this.name = name;
        this.myCollection = myCollection;
    }

    public void addLike(DbLike like) {
        like.setInstance(this);
        likeList.add(like);
    }

    public void addComment(Comment comment) {
        comment.setInstance(this);
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

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
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

    public MyCollection getMyCollection() {
        return myCollection;
    }

    public void setMyCollection(MyCollection myCollection) {
        this.myCollection = myCollection;
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
