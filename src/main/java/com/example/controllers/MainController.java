package com.example.controllers;

import java.util.List;

import com.example.models.Employee;
import com.example.models.EmployeeService;
import com.example.models.Sqlite;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {

    @FXML
    private TableColumn<Employee, String> cityCol;

    @FXML
    private TableColumn<Employee, Integer> idCol;

    @FXML
    private TableColumn<Employee, String> nameCol;

    @FXML
    private TableColumn<Employee, Integer> salaryCol;

    @FXML
    private TableView<Employee> table;

    @FXML 
    void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        EmployeeService employeeService = new EmployeeService(new Sqlite());
        List<Employee> empList = employeeService.getEmployees();
        table.getItems().addAll(empList);

    }

    @FXML 
    void goButton(ActionEvent event) {
        System.out.println("Működik...");

    }

}
