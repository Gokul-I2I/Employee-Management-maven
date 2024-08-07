package com.ideas2it.ems.project.dao;

import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

import com.ideas2it.ems.helper.HibernateConnection;
import com.ideas2it.ems.exception.MyException;
import com.ideas2it.ems.model.Employee;
import com.ideas2it.ems.model.Project;

/**
 * This class handle the all operation related to Project database
 * @author Gokul
 */
public class ProjectDaoImpl implements ProjectDao {
    private static final Logger logger = LogManager.getLogger(ProjectDaoImpl.class);

    @Override
    public Project createProject(Project project) throws MyException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.connection()) {
            transaction = session.beginTransaction();
            session.save(project);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error in Project adding in DataBase {}", project.getProjectId());
            throw new MyException("Error in Project adding in DataBase : " + project.getProjectId(), e);
        }
        return project;
    }

    @Override
    public boolean removeProject(int projectId) throws MyException {
        boolean flag = false;
        Transaction transaction = null;
        try (Session session = HibernateConnection.connection()) {
            transaction = session.beginTransaction(); 
            Project project = session.get(Project.class, projectId);
            if (!project.getIsDeleted()) {
                project.setIsDeleted(true);
                flag = true;
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error in removing project in DataBase {}", projectId);
            throw new MyException("Error in removing project in DataBase " + projectId, e);
        }
        return flag;
    }

    @Override
    public List<Project> retrieveProjects() throws MyException {
        try (Session session = HibernateConnection.connection()) {
            return session.createQuery("FROM Project WHERE project_is_deleted = false", Project.class).list();
        } catch (HibernateException e) {
            logger.error("Error in View All project in DataBase ");
            throw new MyException("Error in View All project in DataBase ", e);
        }
    }

    @Override
    public boolean isValidProjectId(int projectId) throws MyException {
        boolean flag = false;
        try (Session session = HibernateConnection.connection()) {
            Project project = session.get(Project.class, projectId);
            if (!project.getIsDeleted()) {
                flag = true;
            }
        } catch (HibernateException e) {
            logger.error("Error in project id in present or not {}", projectId);
            throw new MyException("Error in project id in present or not" + projectId, e);
        }
        return flag;
    }

    @Override
    public Set<Employee> retrieveProject(int projectId) throws MyException {
        try (Session session = HibernateConnection.connection()) {
            Project project = session.get(Project.class, projectId);
            if (!project.getIsDeleted()) {
                return project.getEmployees();
            }
        } catch (HibernateException e) {
            logger.error("Error in view employees form project {}", projectId);
            throw new MyException("Error in view employees form project" + projectId, e);
        }
        return null;
    }
   
    @Override
    public Project addEmployee(Employee employee, int projectId) throws MyException {
        Project project;
        Transaction transaction = null;
        try (Session session = HibernateConnection.connection()) {
            transaction = session.beginTransaction();
            project = session.get(Project.class, projectId);
            employee = session.get(Employee.class, employee.getId());
            if (!project.getIsDeleted()) {
                project.getEmployees().add(employee);
                session.saveOrUpdate(project);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error in add employee to project " + projectId + employee.getId());
            throw new MyException("Error in add employee to project " + projectId + employee.getId(), e);
        }
        return project;
    }

    @Override
    public void updateProjectName(Project project) throws MyException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.connection()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(project);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error in update project Name in DataBase {}", project.getProjectName() + project.getProjectId());
            throw new MyException("Error in update project Name in DataBase " + project.getProjectName() + project.getProjectId(), e);
        }
    }
}