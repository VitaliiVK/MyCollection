package myCollectionService.dataBaseEntitys;

public enum UserRole {
    ADMIN, USER;

    @Override //переопределение необходимо для взаимодействия со Spring security
    public String toString() {
        return "ROLE_" + name();
    }
}
