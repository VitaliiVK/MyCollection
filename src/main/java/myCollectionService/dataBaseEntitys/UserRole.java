package myCollectionService.dataBaseEntitys;

public enum UserRole {
    ADMIN, USER;

    @Override //for interaction with Spring security
    public String toString() {
        return "ROLE_" + name();
    }
}
