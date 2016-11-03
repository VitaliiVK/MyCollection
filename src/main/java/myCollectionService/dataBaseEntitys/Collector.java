package myCollectionService.dataBaseEntitys;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//коллекционер
@Entity //сущность из базы данных
public class Collector { //таблица будет называться Collector
    @Id
    @GeneratedValue
    private long id; // колонка id с автогенерацией

    @Column(nullable = false, unique = true)
    private String login;
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // анотация jpa - колонка типа enum, в таблице будут храниться записи в фотамете String
    private UserRole role; // значение из класса UserRole - роль пользователя (admin/user)

    private String name;
    private String surname;
    private String email;
    private String phone;
    private String country;
    private String city;
    private String specialization;
    private String information;

    @Column(columnDefinition = "LONGBLOB")
    private byte[] photo;

    //тип связи OneToMany, сопоставляется с полем по имени "collector"
    @OneToMany(mappedBy = "collector", cascade = CascadeType.ALL)
    private List<MyCollection> collectionsList = new ArrayList<>(); //список коллекций коллекционера
    //обратная свять - указано имя поля

    //тип связи OneToMany, сопоставляется с полем по имени "collector"
    @OneToMany(mappedBy = "collector", cascade = CascadeType.ALL)
    private List<DbLike> DbLikeList = new ArrayList<>(); //список лайков коллекционера
    //обратная свять - указано имя поля

    //тип связи OneToMany, сопоставляется с полем по имени "collector"
    @OneToMany(mappedBy = "collector", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>(); //список коментакиев коллекционера
    //обратная свять - указано имя поля

    public Collector() {}

    public Collector(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public void addCollection(MyCollection collection) {
        collection.setCollector(this);
        collectionsList.add(collection);
    }


    public void addLike(DbLike DbLike) {
        DbLike.setCollector(this);
        DbLikeList.add(DbLike);
    }

    public void addComment(Comment comment) {
        comment.setCollector(this);
        commentList.add(comment);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public List<MyCollection> getCollectionsList() {
        return collectionsList;
    }

    public void setCollectionsList(List<MyCollection> collectionsList) {
        this.collectionsList = collectionsList;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<DbLike> getDbLikeList() {
        return DbLikeList;
    }

    public void setDbLikeList(List<DbLike> dbLikeList) {
        this.DbLikeList = dbLikeList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
