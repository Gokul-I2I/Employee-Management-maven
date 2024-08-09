package com.ideas2it.ems.laptop.dao;

import java.util.List;

import com.ideas2it.ems.exception.MyException;
import com.ideas2it.ems.model.Employee;
import com.ideas2it.ems.model.Laptop;

/**
 * This class handle the all operation related to laptop database
 *
 * @author Gokul
 */
public interface LaptopDao {

    /**
     * Add the laptop details to the database
     *
     * @param laptop : details of the laptop
     * @return laptop : details of the laptop
     * @throws MyException
     */
    Laptop createLaptop(Laptop laptop) throws MyException;

    /**
     * Delete the laptop by its id
     *
     * @param laptopId : id of the laptop
     * @return boolean laptop is removed or not
     * @throws MyException
     */
    boolean removeLaptop(int laptopId) throws MyException;

    /**
     * Get the all laptops details
     *
     * @return List<laptop> : list of the all laptops details
     * @throws MyException
     */
    List<Laptop> retrieveLaptops() throws MyException;

    /**
     * Get the laptop id is present or not
     *
     * @param laptopId : id of the laptop
     * @return boolean : laptop id is contained true or false
     * @throws MyException
     */
    boolean isValidLaptopId(int laptopId) throws MyException;

    /**
     * Get all employees by the laptop id
     *
     * @param laptopId of the laptop
     * @return Employee : details of employee
     * @throws MyException
     */
    Employee retrieveLaptop(int laptopId) throws MyException;

    /**
     * Update the laptop name by its id
     *
     * @param laptopId    : id of the laptop
     * @param laptopModel : new name of the laptop
     * @throws MyException
     */
    void updateLaptopName(String laptopModel, int laptopId) throws MyException;
}