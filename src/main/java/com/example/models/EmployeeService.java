package com.example.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    
    Database database;
    Connection con;

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
        con = database.connect();
        if (con == null) {
            System.err.println("No connection");
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

    public void createEmployee(Employee employee) {
        try {
            tryCreateEmployee(employee);
        } catch (SQLException e) {
            // e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    private void tryCreateEmployee(Employee employee) throws SQLException {
        con = database.connect();
        if (con == null) {
            System.err.println("No connection");
            
        }
        String sql = "INSERT INTO employees" + 
        "(name, city, salary)" + 
        " VALUES " + 
        "(?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, employee.getName());
        ps.setString(2, employee.getCity());
        ps.setInt(3, employee.getSalary());
        ps.executeUpdate();
        con.close();
    }

    public void updateEmployee(Employee employee) {
        try {
            tryUpdateEmployee(employee);
        } catch (SQLException e) {
            // e.printStackTrace();
            System.err.println(e.getMessage());}
    }

    private void tryUpdateEmployee(Employee employee) throws SQLException {
        con = database.connect();
        if (con == null) {
            System.err.println("No connection");
        }
        String sql = "UPDATE employees SET" + 
        " name = ?, city = ?, salary = ?" +
        " WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, employee.getName());
        ps.setString(2, employee.getCity());
        ps.setInt(3, employee.getSalary());
        ps.setInt(4, employee.getId());
        ps.executeUpdate();
        con.close();
    }

    public void deleteEmployee(int id) {
        try {
            tryDeleteEmployee(id);
        } catch (SQLException e) {
            // e.printStackTrace();
            System.err.println(e.getMessage());}
    }

    private void tryDeleteEmployee(int id) throws SQLException {
        con = database.connect();
        if (con == null) {
            System.err.println("No connection");
            
        }
        String sql = "DELETE FROM employees WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        con.close();
    }

    
}
