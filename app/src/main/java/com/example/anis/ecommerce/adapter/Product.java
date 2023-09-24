package com.example.anis.ecommerce.adapter;

import android.widget.Toast;

import java.util.List;

public class Product {
    private int id;
    String title, titles ,desc,fav;
   double  price;
    int image,productid;
    String allImage;
    String color, size;
    String cart,checkID;
    String typeid,typename,categoryid,categoryname,status,notid;

    public String getCheckID() {
        return checkID;
    }

    public void setCheckID(String checkID) {
        this.checkID = checkID;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotid() {
        return notid;
    }

    public void setNotid(String notid) {
        this.notid = notid;
    }

    String username,contact, email , firstname, midname,lastname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMidname() {
        return midname;
    }

    public void setMidname(String midname) {
        this.midname = midname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getQnty() {
        return qnty;
    }

    public double gettTotal() {
        return tTotal;
    }

    public void settTotal(double tTotal) {
        this.tTotal = tTotal;
    }

    List<String> colorList , sizeList;



    public List<String> getColorList() {
        return colorList;
    }

    public void setColorList(List<String> colorList) {
        this.colorList = colorList;
    }

    public List<String> getSizeList() {
        return sizeList;
    }

    public void setSizeList(List<String> sizeList) {
        this.sizeList = sizeList;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFav() {
        return fav;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAllImage() {
        return allImage;
    }

    public void setAllImage(String allImage) {
        this.allImage = allImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    String images;
     int qnty;
    double total,tTotal;

    public Product(){

    }



    public Product(int id) {
        this.id = id;
    }
    /* public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }*/

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getQnty(int count) {
        return qnty;
    }

    public void setQnty(int qnty) {
        this.qnty = qnty;
    }

    public Product(String titles , String images){
        this.titles= titles;
        this.images= images;
    }

    public Product(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public Product(String title, String allImage, double price ){
        this.title=title;
        this.allImage=allImage;
        this.price= price;
    }

    public Product(String title, double price, String allImage, int qnty, double total) {
        this.title = title;
        this.price = price;
        this.allImage = allImage;
        this.qnty = qnty;
        this.total = total;
    }

    public Product(int id, String title, double price, String allImage, int qnty) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.allImage = allImage;
        this.qnty = qnty;
    }

    public Product(int id, String title, double price, String allImage) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.allImage = allImage;
    }

    public Product(int id, String title, String desc, double price, String allImage) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.price = price;
        this.allImage = allImage;
    }

    public Product(int id, String title, String desc, String fav, double price, String allImage) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.fav = fav;
        this.price = price;
        this.allImage = allImage;
    }

  /*  public Product(int id, String title, double price, String allImage, String color, String size) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.allImage = allImage;
        this.color = color;
        this.size = size;
    }*/


    public Product(String allImage) {
        this.allImage = allImage;
    }

    public int getId() {
        return id;
    }


    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
