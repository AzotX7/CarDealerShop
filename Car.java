package ru.relex;

public class Car {
    private String ID;
    private String brand;
    private String model;
    private String engine;
    private String category;
    private int price;

    public Car(String ID,String brand, String model, String engine, String category,int price) {
        this.ID = ID;
        this.brand = brand;
        this.model = model;
        this.engine = engine;
        this.category = category;
        this.price = price;
    }
    public Car(){

    }

    @Override
    public String toString() {
        return  "ID " + ID + "\n" +
                "Бренд " + brand + "\n" +
                "Модель " + model + "\n" +
                "Объем двигателя " + engine + "\n" +
                "Категория " + category + "\n" +
                "Цена " + price + "\n---------" + "\n";

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
