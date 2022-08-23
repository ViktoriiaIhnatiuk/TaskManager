package com.example.taskmanager.dto.request;

public class RoleRequestDto {
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "RoleRequestDto{"
                + "roleName='" + roleName + '\''
                + '}';
    }
}
