package com.ideas2it.ems.project.service;

import java.util.List;
import java.util.Set;

import com.ideas2it.ems.exception.MyException;
import com.ideas2it.ems.model.Employee;
import com.ideas2it.ems.model.Project;

/**
 * Interface that has the following methods
 */
public interface ProjectService {

    /**
     * Create the Project 
     *
     * @param projectName: name of the project
     *
     * @return Project : project details
     * @throws MyException
     */
    public Project createProject(String projectName) throws MyException;

    /**
     * Delete project by its id
     *
     * @param projectId : id of the project
     *
     * @return boolean project is soft deleted true or false
     * @throws MyException
     */
    public boolean removeProject(int projectId) throws MyException;

    /**
     * Get the all projects
     *
     * @return List<Project> : list of the all project details
     * @throws MyException
     */
    public List<Project> retrieveProjects() throws MyException;

    /**
     * Get the project id is valid or not
     *
     * @param projectId : project Id
     *
     * @return boolean : project id is contained true or false
     * @throws MyException
     */
    public boolean isValidProjectId(int projectId) throws MyException;

    /**
     * Get the employee details by its project id
     *
     * @param projectId : project id
     *
     * @return Set<Employee> : list of all employees details in Set
     * @throws MyException
     */
    public Set<Employee> retrieveProject(int projectId) throws MyException;

    /**
     * Update project name by its id
     *
     * @param projectName :  name of the project
     * @param projectId of the project
     * @throws MyException
     */
    public void updateProjectName(String projectName, int projectId) throws MyException;

    /**
     * Add employee details to the project
     *
     * @param projectId : id
     * @param employee: details of the employee
     * @throws MyException
     */
    public void addEmployee(Employee employee, int projectId) throws MyException; 
}