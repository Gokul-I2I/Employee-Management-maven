package com.ideas2it.ems.employee.service;

import java.time.LocalDate;
import java.util.List;

import com.ideas2it.ems.exception.MyException;
import com.ideas2it.ems.model.Department;
import com.ideas2it.ems.model.Employee;
import com.ideas2it.ems.model.Laptop;

/**
 * Interface that has the following methods
 */
public interface EmployeeService {

    /**
     * Get the list of all departments details
     *
     * @return List<Department> : list of all departments deatils
     * @throws MyException;
     */
    List<Department> retrieveDepartments() throws MyException;

    /**
     * Used to create employee with name, dateOfBirth, department id, laptop details
     *
     * @param employeeName         name
     * @param dateOfBirth          dateOfBirth
     * @param employeeDepartmentId departmentId
     * @param laptop               laptop details
     * @return Employee : details of the employee
     * @throws MyException'
     */
    Employee createEmployee(String employeeName, LocalDate dateOfBirth, int employeeDepartmentId, Laptop laptop) throws MyException;

    /**
     * Delete the employee by its id
     *
     * @param id : delete the employee details by its id
     * @return boolean : employee is removed or not
     * @throws MyException;
     */
    boolean deleteEmployee(int id) throws MyException;

    /**
     * Get the all employees from database by department id wise
     *
     * @param departmentId : id of the department
     * @return list<Employee> : list of the all employees deatils from department
     * @throws MyException;
     */
    List<Employee> retrieveEmployeeByDepartment(int departmentId) throws MyException;

    /**
     * Get the all employees from database
     *
     * @return List<Employee> : list of the employees details
     * @throws MyException;
     */
    List<Employee> retrieveEmployees() throws MyException;

    /**
     * Update employee name details
     *
     * @param employee : employee with new changes
     * @throws MyException;
     */
    void updateEmployee(Employee employee) throws MyException;

    /**
     * Update employee Date of birth details
     *
     * @param employee : employee with new dateOfBirth changes
     * @throws MyException;
     */
    void updateEmployeeAge(Employee employee, LocalDate localDate) throws MyException;

    /**
     * Update employee laptop model details
     *
     * @param employee    : employee details
     * @param laptopModel : laptop new model details
     * @throws MyException;
     */
    void updateEmployeeLaptop(Employee employee, String laptopModel) throws MyException;

    /**
     * Update employee department  details
     *
     * @param employee       : employee details
     * @param departmentName : new department name details
     * @throws MyException;
     */
    void updateEmployeeDepartment(Employee employee, String departmentName) throws MyException;

    /**
     * Get the employee details by its id
     *
     * @param id : id of the employee
     * @return Employee : details of the employee
     * @throws MyException;
     */
    Employee retrieveEmployeeId(int id) throws MyException;

}