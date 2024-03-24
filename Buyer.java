package ru.relex;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Buyer {
    private int balance;
    private String name;
    private String surname;
    private Connection connection;

    public Buyer(String name, String surname, int balance, String url, String username, String password) throws SQLException {
        this.name = name;
        this.surname = surname;
        this.balance = balance;
        connection = DriverManager.getConnection(url, username, password);
    }

    public List<Car> getAvailableCarsFromDealer() throws SQLException {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM car_dealer_cars";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                String engine = resultSet.getString("engine");
                String category = resultSet.getString("category");
                int price = resultSet.getInt("price");

                cars.add(new Car(id, brand, model, engine, category, price));
            }
        }
        return cars;
    }

    public void buyCar() throws SQLException {
        Car chosenCar = null;
        List<Car> availableCars = getAvailableCarsFromDealer();
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите id машины которую желаете приобрести");
        String carId = sc.nextLine();
        for (Car car : availableCars) {
            if (car.getID().equals(carId)) {
                chosenCar = car;
                break;
            }
        }

        if (chosenCar != null && chosenCar.getPrice() <= balance) {
            balance =- chosenCar.getPrice();

            String deleteQuery = "DELETE FROM car_dealer_cars WHERE id = ?;";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                deleteStatement.setInt(1, Integer.parseInt(carId));
                deleteStatement.executeUpdate();
            }
            String insertQuery = "INSERT INTO buyer_cars (brand, model, engine, category, price,owner_name,owner_surname) VALUES (?, ?, ?, ?, ?, ?, ?);";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1, chosenCar.getBrand());
                insertStatement.setString(2, chosenCar.getModel());
                insertStatement.setString(3, chosenCar.getEngine());
                insertStatement.setString(4, chosenCar.getCategory());
                insertStatement.setInt(5, chosenCar.getPrice());
                insertStatement.setString(6, this.name);
                insertStatement.setString(7, this.surname);

                insertStatement.executeUpdate();
            }
        } else {
            System.out.println("Машины с таким ID не найдено или недостаточно средств на счету");
        }
    }

    public int getBalance() {
        return balance;
    }

}



