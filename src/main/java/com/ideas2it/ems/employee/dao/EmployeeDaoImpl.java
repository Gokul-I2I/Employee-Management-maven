package com.ideas2it.ems.employee.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session; 
import org.hibernate.Transaction; 
import org.hibernate.HibernateException;

import com.ideas2it.ems.helper.HibernateConnection;
import com.ideas2it.ems.exception.MyException;
import com.ideas2it.ems.model.Employee;

/**
 * This class handle the employee storing operation related based on user request
 * @author Gokul
 */
public class EmployeeDaoImpl implements EmployeeDao {
    private static final Logger LOGGER = LogManager.getLogger(EmployeeDaoImpl.class);

    @Override
    public Employee createEmployee(Employee employee) throws MyException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.connection()) {
            transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.info("Error in Employee adding in DataBase{} ", employee.getId() + employee.getName());
            throw new MyException("Error in Employee adding in DataBase " + employee.getId() + employee.getName(), e);
        }
        return employee;
    }
  
    @Override
    public boolean deleteEmployee(int id) throws MyException {
        boolean flag = false;
        Transaction transaction = null;
        try (Session session = HibernateConnection.connection()) {
            transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, id);
            if (!employee.getIsDeleted()) {
                employee.setIsDeleted(true);
                flag = true;
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error in delete department by its id {}", id);
            throw new MyException("Error in delete department by its id : " + id, e);
        }
        return flag;
    }

    @Override
    public Employee retrieveEmployeeId(int id) throws MyException {
        try (Session session = HibernateConnection.connection()) {
            return session.get(Employee.class, id);
        } catch (HibernateException e) {
            LOGGER.error("Error in get employee by id {} ", id);
            throw new MyException("Error in get employee by id : " + id, e);
        }
    }

    @Override
    public List<Employee> retrieveEmployees() throws MyException  {
        try (Session session = HibernateConnection.connection()) {
            return session.createQuery("FROM Employee WHERE employee_is_deleted = false", Employee.class).list();
        } catch (HibernateException e) {
            LOGGER.error("Error in View All employees");
            throw new MyException("Error in View All employees ", e);
        }
    }

    @Override
    public List<Employee> retrieveEmployeeByDepartment(int departmentId) throws MyException {
        try (Session session = HibernateConnection.connection()) {
            return session.createQuery("FROM Employee WHERE department_id  = " + departmentId, Employee.class).list();
        } catch (HibernateException e) {
            LOGGER.error("Error in view employees form department {} ", departmentId);
            throw new MyException("Error in view employees form department : " + departmentId, e);
        }
    }

    @Override
    public void updateEmployee(Employee employee) throws MyException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.connection()) {
            transaction = session.beginTransaction();
            session.update(employee);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error in update employee in DataBase {} ",employee.getName() + employee.getId());
            throw new MyException("Error in update employee in DataBase " + employee.getName() + employee.getId(), e);
        }        
    }
}