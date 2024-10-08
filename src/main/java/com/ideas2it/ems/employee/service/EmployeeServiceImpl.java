package com.ideas2it.ems.employee.service;

import java.time.LocalDate;
import java.util.List;

import com.ideas2it.ems.department.service.DepartmentService;
import com.ideas2it.ems.department.service.DepartmentServiceImpl;
import com.ideas2it.ems.employee.dao.EmployeeDao;
import com.ideas2it.ems.employee.dao.EmployeeDaoImpl;
import com.ideas2it.ems.exception.MyException;
import com.ideas2it.ems.laptop.service.LaptopService;
import com.ideas2it.ems.laptop.service.LaptopServiceImpl;
import com.ideas2it.ems.model.Department;
import com.ideas2it.ems.model.Employee;
import com.ideas2it.ems.model.Laptop;

/**
 * This class handle the all operation related to Employee CRUD based on user request
 * @author Gokul
 */
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeDao employeeDao = new EmployeeDaoImpl();
    DepartmentService departmentService = new DepartmentServiceImpl();
    LaptopService laptopService = new LaptopServiceImpl();

    @Override
    public List<Department> retrieveDepartments() throws MyException {
        return departmentService.retrieveDepartments();
    }

    @Override
    public Employee createEmployee(String employeeName, LocalDate dateOfBirth, int employeeDepartmentId, Laptop laptop) throws MyException {
        Department department = departmentService.retrieveDepartment(employeeDepartmentId); 
        return employeeDao.createEmployee(new Employee(employeeName, dateOfBirth, department, laptop));       
    }
    
    @Override
    public boolean deleteEmployee(int id) throws MyException {
        return employeeDao.deleteEmployee(id);
    }

    @Override
    public Employee retrieveEmployeeId(int id) throws MyException {
        return employeeDao.retrieveEmployeeId(id);     
    }
    
    @Override
    public void updateEmployee(Employee employee) throws MyException {
        employeeDao.updateEmployee(employee);
    }

    @Override
    public void updateEmployeeAge(Employee employee, LocalDate localDate) throws MyException {
        employee.setAge(localDate);
        employeeDao.updateEmployee(employee);
    }

    @Override
    public void updateEmployeeLaptop(Employee employee, String laptopModel) throws MyException {
        laptopService.updateLaptopName(laptopModel, employee.getLaptop().getId());
    }

    @Override
    public void updateEmployeeDepartment(Employee employee, String departmentName) throws MyException {
        departmentService.updateDepartmentName(departmentName, employee.getDepartment().getDepartmentId());
    }

    @Override
    public List<Employee> retrieveEmployees() throws MyException {
        return employeeDao.retrieveEmployees();        
    }

    @Override
    public List<Employee> retrieveEmployeeByDepartment(int departmentId) throws MyException {
         return employeeDao.retrieveEmployeeByDepartment(departmentId);
 
    }
}