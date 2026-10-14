package com.example.controllers;

import java.util.List;

import com.example.models.Config;
import com.example.models.Employee;
import com.example.models.EmployeeService;
import com.example.models.Mariadb;
import com.example.models.Sqlite;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField salaryField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button modifyButton;

    EmployeeService employeeService;

    @FXML 
    void initialize() {
        initCols();
        Config config = new Config();
        String dialect = config.getProperty("dialect");
        if (dialect.equals("mariadb")) {
            employeeService = new EmployeeService(new Mariadb());
        }else {
            employeeService = new EmployeeService(new Sqlite());
        }
        // EmployeeService employeeService = new EmployeeService(new Mariadb());
        employeeService = new EmployeeService(new Sqlite());
        List<Employee> empList = employeeService.getEmployees();
        table.getItems().addAll(empList);

    }

    void initCols() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    @FXML
    void onClickAddButton(ActionEvent event) {
        System.out.println("Hozzáadás....");
        employeeService.createEmployee(makeNewEmployee());
        refreshEmployees();
        clearFields();

    }

    Employee makeNewEmployee() {
        Employee newEmployee = new Employee();
        newEmployee.setName(nameField.getText());
        newEmployee.setCity(cityField.getText());
        newEmployee.setSalary(Integer.parseInt(salaryField.getText()));
        return newEmployee;
    }


    void clearFields() {
        idField.clear();
        nameField.clear();
        cityField.clear();
        salaryField.clear();
    }

    @FXML
    void onClickDeleteButton(ActionEvent event) {
        System.out.println("Törlés....");
        int selected = table.getSelectionModel().getSelectedIndex();
        if(selected >= 0) {
            Employee employeeForDelete = table.getItems().get(selected);
            employeeService.deleteEmployee(employeeForDelete.getId());
            refreshEmployees();
        }
    }

    void refreshEmployees() {
        table.getItems().clear();
        table.getItems().addAll(employeeService.getEmployees());
    }

    @FXML
    void onClickModifyButton(ActionEvent event) {
        System.out.println("Módosítás....");
        Employee employeeForUpdate = makeUpdatedEmployee();
        employeeService.updateEmployee(employeeForUpdate);
        refreshEmployees();
        clearFields();
        unsetEditMode();
    }

    Employee makeUpdatedEmployee() {
        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(Integer.parseInt(idField.getText()));
        updatedEmployee.setName(nameField.getText());
        updatedEmployee.setCity(cityField.getText());
        updatedEmployee.setSalary(Integer.parseInt(salaryField.getText()));
        return updatedEmployee;
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            System.out.println("Dupla Kattintás....");
            int selected = table.getSelectionModel().getSelectedIndex();
            Employee employeeForEdit = table.getItems().get(selected);
            fillFields(employeeForEdit);
            setEditMode();
        }
        
    }

    void fillFields(Employee employee) {
        idField.setText(Integer.toString(employee.getId()));
        nameField.setText(employee.getName());
        cityField.setText(employee.getCity());
        salaryField.setText(Integer.toString(employee.getSalary()));
    }

    void setEditMode() {
        deleteButton.setDisable(true);
        addButton.setDisable(true);
    }

    void unsetEditMode() {
        deleteButton.setDisable(false);
        addButton.setDisable(false);
    }
}
