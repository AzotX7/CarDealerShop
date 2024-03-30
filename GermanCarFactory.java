package ru.relex;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GermanCarFactory {
    private List<Car> cars = new ArrayList<>();
    private Connection connection;

    GermanCarFactory(){
        try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://localhost:5432/Carlist";
            String username = "postgres";
            String password = "...";
            connection = DriverManager.getConnection(url,username,password);
        }
        catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }
    public Car machineMaking(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите ID машины");
        String ID = sc.nextLine();
        System.out.println("Введите название марки автомобиля,который хотите создать на заводе");
        String brand = sc.nextLine();
        System.out.println("Введите модель машины");
        String model = sc.nextLine();
        System.out.println("Введите объем двигателя машины");
        String engine = sc.nextLine();
        System.out.println("Введите категорию машины");
        String category = sc.nextLine();
        System.out.println("Введите цену для машины в рублях");
        int price = sc.nextInt();
        Car car = new Car(ID,brand,model,engine,category,price);
        cars.add(car);

        try {
        String sql = "INSERT INTO cars (brand, model, engine, category,price) VALUES (?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,brand);
        statement.setString(2,model);
        statement.setString(3,engine);
        statement.setString(4,category);
        statement.setInt(5,price);
        statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    return car;
    }
    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
