package com.example.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    
    Database database;

    public EmployeeService(Database database) {
        this.database = database;
    }

    public List<Employee> getEmployees() {
        try {
            return tryGetEmployees();
        } catch (SQLException e) {
            return null;
        }
    }
    public List<Employee> tryGetEmployees() throws SQLException {
        List<Employee> empList;
        try (Connection con = database.connect()) {
            if (con == null) {
                System.out.println("Connection failed.");
                return null;
            }   String sql = "SELECT * FROM employees";
            Statement statment = con.createStatement();
            ResultSet resultSet = statment.executeQuery(sql);
            empList = new ArrayList<>();
            while (resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("city"),
                        resultSet.getInt("salary")
                );
                empList.add(employee);
            }
        }
        return empList; 
    }
}
