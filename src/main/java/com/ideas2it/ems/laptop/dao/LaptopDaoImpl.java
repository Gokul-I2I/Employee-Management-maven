package com.ideas2it.ems.laptop.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

import com.ideas2it.ems.helper.HibernateConnection;
import com.ideas2it.ems.exception.MyException;
import com.ideas2it.ems.model.Employee;
import com.ideas2it.ems.model.Laptop;

import java.util.List;

/**
 * This class handle the all operation related to laptop database
 *
 * @author Gokul
 */
public class LaptopDaoImpl implements LaptopDao {
    private static final Logger logger = LogManager.getLogger(LaptopDaoImpl.class);

    @Override
    public Laptop createLaptop(Laptop laptop) throws MyException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.connection()) {
            transaction = session.beginTransaction();
            session.save(laptop);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.info("Error in laptop adding in DataBase{}", laptop.getId());
            throw new MyException("Error in laptop adding in DataBase : " + laptop.getId(), e);
        }
        return laptop;
    }

    @Override
    public boolean removeLaptop(int laptopId) throws MyException {
        boolean flag = false;
        Transaction transaction = null;
        try (Session session = HibernateConnection.connection()) {
            transaction = session.beginTransaction();
            Laptop laptop = session.get(Laptop.class, laptopId);
            session.remove(laptop);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.info("Error in removing laptop in DataBase{}", laptopId);
            throw new MyException("Error in removing laptop in DataBase " + laptopId, e);
        }
        return flag;
    }

    /**
     * Get the all laptops details
     *
     * @return List<laptop> : list of the all laptops details
     * @throws MyException
     */
    public List<Laptop> retrieveLaptops() throws MyException {
        try (Session session = HibernateConnection.connection()) {
            return session.createQuery("FROM Laptop ", Laptop.class).list();
        } catch (HibernateException e) {
            logger.error("Error in View All project in DataBase ");
            throw new MyException("Error in View All project in DataBase ", e);
        }
    }

    @Override
    public boolean isValidLaptopId(int laptopId) throws MyException {
        boolean flag = false;
        try (Session session = HibernateConnection.connection()) {
            Laptop laptop = session.get(Laptop.class, laptopId);
            if (laptop != null) {
                flag = true;
            }
        } catch (HibernateException e) {
            logger.info("Error in laptop id in present or not{}", laptopId);
            throw new MyException("Error in laptop id in present or not" + laptopId, e);
        }
        return flag;
    }

    @Override
    public Employee retrieveLaptop(int laptopId) throws MyException {
        try (Session session = HibernateConnection.connection()) {
            Laptop laptop = session.get(Laptop.class, laptopId);
            return laptop.getEmployee();
        } catch (HibernateException e) {
            logger.info("Error in view employees form laptop{}", laptopId);
            throw new MyException("Error in view employees form laptop" + laptopId, e);
        }
    }

    @Override
    public void updateLaptopName(String laptopModel, int laptopId) throws MyException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.connection()) {
            transaction = session.beginTransaction();
            Laptop laptop = session.get(Laptop.class, laptopId);
            laptop.setModel(laptopModel);
            session.update(laptop);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.info("Error in update laptop Name in DataBase{}", laptopId);
            throw new MyException("Error in update laptop Name in DataBase " + laptopId, e);
        }
    }
}