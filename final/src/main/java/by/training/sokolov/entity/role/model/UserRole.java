package by.training.sokolov.entity.role.model;

import by.training.sokolov.core.dao.IdentifiedRow;

public class UserRole implements IdentifiedRow {

    private Long id;
    private String roleName;

    public UserRole() {

    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
