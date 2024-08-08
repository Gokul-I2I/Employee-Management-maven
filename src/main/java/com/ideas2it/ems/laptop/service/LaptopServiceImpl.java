package com.ideas2it.ems.laptop.service;

import java.util.List;

import com.ideas2it.ems.exception.MyException;
import com.ideas2it.ems.model.Employee;
import com.ideas2it.ems.model.Laptop;
import com.ideas2it.ems.laptop.dao.LaptopDao;
import com.ideas2it.ems.laptop.dao.LaptopDaoImpl;

/**
 * This class handle the all operation related to Laptop CURD based on user request
 *
 * @author Gokul
 */
public class LaptopServiceImpl implements LaptopService {
    LaptopDao laptopDao = new LaptopDaoImpl();

    @Override
    public Laptop createLaptop(String model) throws MyException {
        return laptopDao.createLaptop(new Laptop(model));
    }

    @Override
    public boolean removeLaptop(int laptopId) throws MyException {
        return laptopDao.removeLaptop(laptopId);
    }

    @Override
    public List<Laptop> retrieveLaptops() throws MyException {
        return laptopDao.retrieveLaptops();
    }

    @Override
    public boolean isValidLaptopId(int laptopId) throws MyException {
        return laptopDao.isValidLaptopId(laptopId);
    }

    @Override
    public Employee retrieveLaptop(int laptopId) throws MyException {
        return laptopDao.retrieveLaptop(laptopId);
    }

    @Override
    public void updateLaptopName(String modelId, int laptopId) throws MyException {
        laptopDao.updateLaptopName(modelId, laptopId);
    }
}