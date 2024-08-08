package com.ideas2it.ems.laptop.service;

import java.util.List;

import com.ideas2it.ems.exception.MyException;
import com.ideas2it.ems.model.Employee;
import com.ideas2it.ems.model.Laptop;

/**
 * Interface that has the following methods
 */
public interface LaptopService {

    /**
     * Create the Laptop
     *
     * @param model : laptop model
     * @return Laptop : Laptop details
     * @throws MyException
     */
    Laptop createLaptop(String model) throws MyException;

    /**
     * Delete Laptop by its id
     *
     * @param LaptopId : id of the Laptop
     * @return boolean Laptop is soft deleted true or false
     * @throws MyException
     */
    boolean removeLaptop(int LaptopId) throws MyException;

    /**
     * Get the all Laptops
     *
     * @return List<Laptop> : list of the all Laptop details
     * @throws MyException
     */
    List<Laptop> retrieveLaptops() throws MyException;

    /**
     * Get the Laptop details by its I'd be valid or not
     *
     * @param LaptopId : Laptop Id
     * @return boolean : Laptop id is contained true or false
     * @throws MyException
     */
    boolean isValidLaptopId(int LaptopId) throws MyException;

    /**
     * Get the employee details by its Laptop id
     *
     * @param LaptopId : Laptop id
     * @return Employee: list of all employees details in Set
     * @throws MyException
     */
    Employee retrieveLaptop(int LaptopId) throws MyException;

    /**
     * Update Laptop name by its id
     *
     * @param LaptopName: name of the Laptop
     * @param LaptopId    of the Laptop
     * @throws MyException
     */
    void updateLaptopName(String LaptopName, int LaptopId) throws MyException;
}