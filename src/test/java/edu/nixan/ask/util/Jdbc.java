package edu.nixan.ask.util;

import java.sql.*;

public class Jdbc {

    private static final String URL = "jdbc:mysql://44.205.92.189:3308/application";
    private static final String USER = "testuser";
    private static final String PASSWORD = "password";

    public static String fetchUserActivationCode(String email) {
        final String query = "SELECT activation_code FROM users WHERE email = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("activationCode");
                } else {
                    System.out.println("No user found with email " + email + ".");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer fetchUserId(String email) {
        final String query = "SELECT id FROM users WHERE email = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    System.out.println("No user found with email " + email + ".");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
