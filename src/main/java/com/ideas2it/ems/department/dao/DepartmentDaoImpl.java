package com.ideas2it.ems.department.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

import com.ideas2it.ems.exception.MyException;
import com.ideas2it.ems.helper.HibernateConnection;
import com.ideas2it.ems.model.Department;

/**
 * This class handle the all operation related to Department database
 *
 * @author Gokul
 */
public class DepartmentDaoImpl implements DepartmentDao {
    private static final Logger LOGGER = LogManager.getLogger(DepartmentDaoImpl.class);

    @Override
    public Department createDepartment(Department department) throws MyException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.connection()) {
            transaction = session.beginTransaction();
            session.save(department);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error in department adding in DataBase {}", department.getDepartmentId() + " " + department.getDepartmentName());
            throw new MyException("Error in department adding in DataBase " + department.getDepartmentId() + " " + department.getDepartmentName(), e);
        }
        return department;
    }

    @Override
    public boolean isDepartmentIdPresent(int departmentId) throws MyException {
        boolean flag = false;
        try (Session session = HibernateConnection.connection()) {
            Department department = session.get(Department.class, departmentId);
            if (!department.getIsDeleted()) {
                flag = true;
            }
        } catch (HibernateException e) {
            LOGGER.error("Error in department id in present or not {}", departmentId);
            throw new MyException("Error in department id in present or not" + departmentId, e);
        }
        return flag;
    }

    @Override
    public Department retrieveDepartment(int departmentId) throws MyException {
        try (Session session = HibernateConnection.connection()) {
            return session.get(Department.class, departmentId);
        } catch (HibernateException e) {
            LOGGER.error("Error in get department details in database by its Id {} ", departmentId);
            throw new MyException("Error in get department details in database by its Id : " + departmentId, e);
        }
    }

    @Override
    public boolean deleteDepartment(int id) throws MyException {
        Transaction transaction = null;
        boolean flag = false;
        try (Session session = HibernateConnection.connection()) {
            transaction = session.beginTransaction();
            Department department = session.get(Department.class, id);
            if (!department.getIsDeleted()) {
                department.setIsDeleted(true);
                flag = true;
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error in removing department in DataBase {}", id);
            throw new MyException("Error in removing department in DataBase " + id, e);
        }
        return flag;
    }

    @Override
    public List<Department> retrieveDepartments() throws MyException {
        try (Session session = HibernateConnection.connection()) {
            return session.createQuery("FROM Department", Department.class).list();
        } catch (HibernateException e) {
            LOGGER.error("Error in View All Department in DataBase ");
            throw new MyException("Error in View All Department in DataBase ", e);
        }
    }

    @Override
    public void updateDepartmentName(String departmentName, int departmentId) throws MyException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.connection()) {
            transaction = session.beginTransaction();
            Department department = session.get(Department.class, departmentId);
            department.setDepartmentName(departmentName);
            session.update(department);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error in update Department Name in DataBase {}", departmentName + departmentId);
            throw new MyException("Error in update Department Name in DataBase " + departmentName + departmentId, e);
        }
    }
}