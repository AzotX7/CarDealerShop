package ru.relex;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class CarDealerShop {
    private int BalanceDealerShop;

    private Connection connection;

    CarDealerShop(String url,String username,String password) throws SQLException {
        connection = DriverManager.getConnection(url,username,password);
    }
    CarDealerShop(){

    }

    public int getBalanceDealerShop() {
        return BalanceDealerShop;
    }

    public void setBalanceDealerShop(int balanceDealerShop) {
        this.BalanceDealerShop = balanceDealerShop;
    }

    public List<Car> getAvailableCars() throws SQLException {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM cars WHERE is_sold IS FALSE;";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                String engine = resultSet.getString("engine");
                String category = resultSet.getString("category");
                int price = resultSet.getInt("price");

                cars.add(new Car(id, brand, model, engine, category,price));
            }
        }

        return cars;
    }
    public Car chooseCarToBuy() throws SQLException {
        List<Car> availableCars = getAvailableCars();
        Scanner sc  = new Scanner(System.in);
        System.out.println("Ввести ID машины,которую автосалон желает купить. Сумма на счету " + getBalanceDealerShop());
        String boughtID = sc.nextLine();

        Iterator<Car> iterator = availableCars.iterator();
        Car chosenCar = null;
        while (iterator.hasNext()){
            Car car = iterator.next();
            if(car.getID().equals(boughtID)){
                chosenCar = car;
                break;
            }
        }
        if (chosenCar != null) {
          int carPrice = chosenCar.getPrice();
          int curBalance = getBalanceDealerShop();
          int newBalance = curBalance - carPrice;
          setBalanceDealerShop(newBalance);

        String updateIsSoldQuery = "UPDATE cars SET is_sold = TRUE WHERE id = ?;";
        try (PreparedStatement updateIsSoldStatement = connection.prepareStatement(updateIsSoldQuery)) {
            updateIsSoldStatement.setInt(1, Integer.parseInt(boughtID));
            updateIsSoldStatement.executeUpdate();
            }
        int newCarPrice = (int) Math.ceil(carPrice * 1.5);
        String insertQuery = "INSERT INTO car_dealer_cars (brand, model, engine, category, price) VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            insertStatement.setString(1, chosenCar.getBrand());
            insertStatement.setString(2, chosenCar.getModel());
            insertStatement.setString(3, chosenCar.getEngine());
            insertStatement.setString(4, chosenCar.getCategory());
            insertStatement.setInt(5, newCarPrice);

            insertStatement.executeUpdate();

        }
            setBalanceDealerShop(getBalanceDealerShop() - chosenCar.getPrice());
    }
        else{
            System.out.println("Машины с таким ID не найдено либо продана");
        }
        return chosenCar;
    }
}
