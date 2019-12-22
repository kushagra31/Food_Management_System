package com.example.symbibro;

class User {
public String name;
public String email;
public String cart;
public int money;

    public User(){

    }
    public User(String name, String email, String cart, int money) {
       this.email=email;
       this.name=name;
       this.cart=cart;
this.money=money;
    }
}
