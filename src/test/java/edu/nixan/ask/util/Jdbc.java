package edu.qa.ask.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Jdbc {

    public static void deleteUser(String email) {
        String url = "jdbc:mysql://44.205.92.189:3308/application";
        String user = "testuser"; // replace with your DB username
        String password = "password"; // replace with your DB password

        String query = "DELETE FROM users WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the email in the prepared statement
            statement.setString(1, email);
            // Execute the update
            int rowsAffected = statement.executeUpdate();
            // Check if the user was deleted
            if (rowsAffected > 0) {
                System.out.println("User with email: " + email + " was deleted.");
            } else {
                System.out.println("No user found with email: " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
