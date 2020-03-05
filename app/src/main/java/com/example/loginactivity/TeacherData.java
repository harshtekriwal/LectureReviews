package com.example.loginactivity;

public class TeacherData {
    private int teacherid;
    private String teacherpassword;
    private  String teachername;

    @Override
    public String toString() {
        return "TeacherData{" +
                "teacherid=" + teacherid +
                ", teacherpassword='" + teacherpassword + '\'' +
                ", teachername='" + teachername + '\'' +
                '}';
    }

    public int getTeacherid() {
        return teacherid;
    }
    public void setTeacherid(int teacherid) {
        this.teacherid = teacherid;
    }

    public String getTeacherpassword() {
        return teacherpassword;
    }
    public void setTeacherpassword(String teacherpassword) {
        this.teacherpassword = teacherpassword;
    }

    public String getTeachername() {
        return teachername;
    }
    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }
}
