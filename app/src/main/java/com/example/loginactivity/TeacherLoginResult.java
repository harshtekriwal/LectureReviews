package com.example.loginactivity;

public class TeacherLoginResult {
    private Boolean isloginsuccessful;

    public Boolean getIsloginsuccessful() {
        return isloginsuccessful;
    }

    @Override
    public String toString() {
        return "TeacherLoginResult{" +
                "isloginsuccessful=" + isloginsuccessful +
                '}';
    }

    public void setIsloginsuccessful(Boolean isloginsuccessful) {
        this.isloginsuccessful = isloginsuccessful;
    }
}
