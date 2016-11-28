package myCollectionService.dataBaseEntitys;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//Instance
@Entity //Entity from data base
public class Instance implements EntityForLikeAndComment {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String subtype;
    private String information;

    //Instance Photo
    @Column(columnDefinition = "LONGBLOB")
    private byte[] photo;

    //Collection of this Instance
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="collection_id") //Column name in table DbLike is "Instance_id"
    private MyCollection myCollection; //Collection

    //Lice list of this Instance
    @OneToMany(mappedBy = "instance", cascade = CascadeType.ALL)//field name in DbLike Class is "instance"
    private List<DbLike> likeList = new ArrayList<>(); //Like List

    //Comment list of this Instance
    @OneToMany(mappedBy = "instance", cascade = CascadeType.ALL)//field name in Comment Class is "instance"
    private List<Comment> commentsList = new ArrayList<>(); //Comments list

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
