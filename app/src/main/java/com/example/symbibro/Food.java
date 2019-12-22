package com.example.symbibro;

public class Food {

    public String discount;
    public String menuId;
    public String name;
    public String price;

    public Food(){

    }

    public Food (String name, String price, String discount,String menuId){

        this.discount=discount;
        this.name=name;
        this.menuId=menuId;
        this.price=price;

    }

}
