package com.example.adminapplication;

public class Manage_User {


    private int id;
    private String fullname;
    private String email;
    private String hpno;
    private String gender;
    private String pic;
    private String type;



    public Manage_User(int id, String fullname, String email, String hpno, String gender, String pic, String type) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.hpno = hpno;
        this.gender = gender;
        this.pic = pic;
        this.type = type;

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHpno() {
        return hpno;
    }

    public void setHpno(String hpno) {
        this.hpno = hpno;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }




}
