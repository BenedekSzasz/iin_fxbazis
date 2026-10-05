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
            e.printStackTrace();
            return null;
        }
    }
    public List<Employee> tryGetEmployees() throws SQLException {
        Connection con = database.connect();
        if (con == null) {
            System.out.println("No connection");
            return null;
        }

        String sql = "SELECT * FROM employees";
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        List<Employee> empList = new ArrayList<>();

        while(resultSet.next()) {
            Employee employee = new Employee(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("city"),
                resultSet.getInt("salary")
            );
            empList.add(employee);
        }
        con.close();
        return empList;
    }
}