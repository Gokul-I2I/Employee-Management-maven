package com.ideas2it.ems.project.controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;
import java.util.Scanner;

import com.ideas2it.ems.employee.service.EmployeeService;
import com.ideas2it.ems.employee.service.EmployeeServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.ideas2it.ems.exception.MyException;
import com.ideas2it.ems.model.Employee;
import com.ideas2it.ems.model.Project;
import com.ideas2it.ems.project.service.ProjectServiceImpl;

/**
 * This class handle the all operation related to Project based on user request
 *
 * @author Gokul
 */
public class ProjectController {
    private static final Logger LOGGER = LogManager.getLogger(ProjectController.class);
    Scanner scanner = new Scanner(System.in);
    ProjectServiceImpl projectService = new ProjectServiceImpl();
    EmployeeService employeeService = new EmployeeServiceImpl();

    /**
     * Create project based on user request
     *
     * @throws MyException
     */
    public void createProject() throws MyException {
        System.out.print("Enter Project Name : ");
        String projectName = scanner.nextLine();
        Project project = projectService.createProject(projectName);
        LOGGER.info("Project Added");
        System.out.println("--------------------------------");
        System.out.printf(" %-5s | %-10s %n", project.getProjectId(), project.getProjectName());
    }

    /**
     * Soft delete project by its id
     *
     * @throws MyException
     */
    public void deleteProject() throws MyException {
        System.out.print("Enter Project ID : ");
        int projectId;
        try {
            projectId = scanner.nextInt();
        } catch (Exception e) {
            throw new MyException("Invalid Input : ", e);
        }
        if (projectService.removeProject(projectId)) {
            LOGGER.info("Project Removed");
        } else {
            LOGGER.info("Project Not Found");
        }
    }

    /**
     * Get the List of all projects details and Employee Details
     *
     * @throws MyException
     */
    public void viewProject() throws MyException {
        System.out.println(" (1) View All Project" + '\n'
                + " (2) List Employees by Project" + '\n'
                + " Enter Option : ");
        int option;
        try {
            option = scanner.nextInt();
        } catch (Exception e) {
            throw new MyException("Invalid Input : ", e);
        }
        if (option == 1) {
            List<Project> projects = projectService.retrieveProjects();
            if (!projects.isEmpty()) {
                for (Project project : projects) {
                    if (!project.getIsDeleted()) {
                        System.out.println("--------------------------------");
                        System.out.printf(" %-5s | %-10s %n", project.getProjectId(), project.getProjectName());
                    }
                }
            } else {
                LOGGER.info("No project Found");
            }
        } else if (option == 2) {
            System.out.print("Enter Project Id : ");
            int projectId = scanner.nextInt();
            if (projectService.isValidProjectId(projectId)) {
                Set<Employee> employees = projectService.retrieveProject(projectId);
                if (employees.isEmpty()) {
                    LOGGER.info("employee not found");
                } else {
                    for (Employee employee : employees) {
                        if (!employee.getIsDeleted()) {
                            System.out.println("-----------------------------------------");
                            System.out.printf(" %-5s | %-10s | %-10s%n", employee.getId(), employee.getName(), employee.getDepartment().getDepartmentName());
                        }
                    }
                }
            } else {
                LOGGER.info("Project not found");
            }
        } else {
            LOGGER.info("Choose 1 or 2");
        }
    }

    /**
     * Update the project name and add the employees to project
     *
     * @throws MyException
     */
    public void updateProject() throws MyException {
        System.out.println(" (1) Change Project Name" + '\n'
                + " (2) Add Employee" + '\n'
                + " Enter Option : ");
        int option;
        try {
            option = scanner.nextInt();
        } catch (Exception e) {
            throw new MyException("Invalid Input : ", e);
        }
        if (option == 1) {
            System.out.print("Enter ProjectId : ");
            int projectId;
            try {
                projectId = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                throw new MyException("Invalid Input : ", e);
            }
            boolean isValidId = projectService.isValidProjectId(projectId);
            if (isValidId) {
                System.out.print("Enter new Project Name : ");
                String projectName = scanner.nextLine();
                projectService.updateProjectName(projectName, projectId);
                LOGGER.info("Project Name Changed");
            } else {
                LOGGER.info("Project Id not found");
            }
        } else if (option == 2) {
            System.out.print("Enter Employee Id : ");
            int employeeId;
            try {
                employeeId = scanner.nextInt();
            } catch (Exception e) {
                throw new MyException("Invalid Input : ", e);
            }
            Employee employee = employeeService.retrieveEmployeeId(employeeId);
            List<Project> projects = projectService.retrieveProjects();
            for (Project project : projects) {
                if (!project.getIsDeleted()) {
                    System.out.println("--------------------------------");
                    System.out.printf(" %-5s | %-10s %n", project.getProjectId(), project.getProjectName());
                }
            }
            if (employee != null) {
                System.out.print("Enter Project ID : ");
                int projectId;
                try {
                    projectId = scanner.nextInt();
                } catch (Exception e) {
                    throw new MyException("Invalid Input : ", e);
                }
                boolean isValidId = projectService.isValidProjectId(projectId);
                if (isValidId) {
                    projectService.addEmployee(employee, projectId);
                    System.out.println("Employee Project Added in Data Base");
                } else {
                    LOGGER.info("Project Id not Found");
                }
            } else {
                LOGGER.info("Employee Not Found");
            }
        } else {
            LOGGER.info("Choose 1 or 2");
        }

    }

    /**
     * Show Menu for Project CRUD Operation.
     */
    public void projectManagementMenu() {
        ProjectController projectController = new ProjectController();
        boolean flag = false;
        while (!flag) {
            System.out.println(" (1) Add project " + '\n'
                    + " (2) Remove project " + '\n'
                    + " (3) View project " + '\n'
                    + " (4) Edit project " + '\n'
                    + " (5) Back " + '\n'
                    + "************************************");
            System.out.print("Enter the Number : ");
            int option;
            try {
                option = scanner.nextInt();
                if (option == 1) {
                    projectController.createProject();
                } else if (option == 2) {
                    projectController.deleteProject();
                } else if (option == 3) {
                    projectController.viewProject();
                } else if (option == 4) {
                    projectController.updateProject();
                } else if (option == 5) {
                    flag = true;
                } else {
                    System.out.println(" Choose 1 - 5 Only ");
                }
            } catch (InputMismatchException e) {
                LOGGER.warn("Invalid Input Choose Number");
                projectController.projectManagementMenu();
            } catch (MyException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
}