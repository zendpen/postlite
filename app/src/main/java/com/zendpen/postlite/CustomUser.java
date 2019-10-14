package com.zendpen.postlite;

public class CustomUser {

    private String name;
    private String email;
    private int age;

    public CustomUser(){

    }

    public CustomUser(String n, int a, String email){
        this.name = n;
        this.age = a;
        this.email = email;
    }

    public void setEmail(String e){
        this.email = e;
    }

    public String getEmail(){
        return this.email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setAge(int a){
        this.age = a;
    }

    public int getAge(){
        return this.age;
    }
}
