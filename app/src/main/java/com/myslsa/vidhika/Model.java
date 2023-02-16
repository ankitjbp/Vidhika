package com.myslsa.vidhika;

public class Model {
    String id,uid,name,phone,address,details,status;

    public Model(String id , String uid, String name, String phone, String address,String details,String status ) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.details = details;
        this.status = status;
    }

    public String getId(){
        return id;
    }
    public String getUId(){
        return uid;
    }
    public String getName(){
        return name;
    }

    public String getPhone(){
        return phone;
    }

    public String getAddress(){
        return address;
    }
    public String getDetails(){
        return details;
    }
    public String getStatus(){
        return status;
    }

}
