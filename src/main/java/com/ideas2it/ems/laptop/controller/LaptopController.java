package com.ideas2it.ems.laptop.controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.ideas2it.ems.exception.MyException;
import com.ideas2it.ems.model.Employee;
import com.ideas2it.ems.model.Laptop;
import com.ideas2it.ems.laptop.service.LaptopService;
import com.ideas2it.ems.laptop.service.LaptopServiceImpl;


/**
 * This class handle the all operation related to laptop based on user request
 *
 * @author Gokul
 */
public class LaptopController {
    Scanner scanner = new Scanner(System.in);
    LaptopService laptopService = new LaptopServiceImpl();
    private static Logger logger = LogManager.getLogger(LaptopController.class);

    /**
     * Create laptop based on user request
     *
     * @throws MyException
     */
    public void createLaptop() throws MyException {
        System.out.print("Enter laptop model id : ");
        String model = scanner.nextLine();
        Laptop laptop = laptopService.createLaptop(model);
        logger.info("Laptop Added");
        System.out.println("--------------------------------");
        System.out.printf(" %-5s | %-10s %n", laptop.getId(), laptop.getModel());
    }

    /**
     * Soft delete laptop by its id
     *
     * @throws MyException
     */
    public void deleteLaptop() throws MyException {
        System.out.print("Enter laptop ID : ");
        int LaptopId = 0;
        try {
            LaptopId = scanner.nextInt();
        } catch (InputMismatchException e) {
            throw new MyException("Invalid Input : ", e);
        }
        if (laptopService.removeLaptop(LaptopId)) {
            logger.info("laptop Removed");
        } else {
            logger.info("laptop Not Found");
        }
    }

    /**
     * Get the List of all Laptops details and Employee Details
     *
     * @throws MyException
     */
    public void viewLaptop() throws MyException {
        System.out.println(" (1) View All laptop" + '\n'
                + " (2) view Employee by laptop ID" + '\n'
                + " Enter Option : ");
        int option = 0;
        try {
            option = scanner.nextInt();
        } catch (Exception e) {
            throw new MyException("Invalid Input : ", e);
        }
        if (option == 1) {
            List<Laptop> laptops = laptopService.retrieveLaptops();
            if (laptops != null) {
                for (Laptop laptop : laptops) {
                    System.out.println("--------------------------------");
                    System.out.printf(" %-5s | %-10s %n", laptop.getId(), laptop.getModel());
                }
            } else {
                logger.info("No laptop Found");
            }
        } else if (option == 2) {
            System.out.print("Enter laptop Id : ");
            int LaptopId = scanner.nextInt();
            if (laptopService.isValidLaptopId(LaptopId)) {
                Employee employee = laptopService.retrieveLaptop(LaptopId);
                if (employee == null) {
                    logger.info("employee not found");
                } else {
                    if (!employee.getIsDeleted()) {
                        System.out.println("-----------------------------------------");
                        System.out.printf(" %-5s | %-10s | %-10s%n", employee.getId(), employee.getName(), employee.getDepartment().getDepartmentName());
                    }
                }
            } else {
                logger.info("laptop not found");
            }
        } else {
            logger.info("Choose 1 or 2");
        }
    }

    /**
     * Update the laptop name and add the employees to laptop
     *
     * @throws MyException
     */
    public void updateLaptop() throws MyException {
        System.out.println(" (1) Change laptop Name" + '\n'
                + " Enter Option : ");
        int option = 0;
        try {
            option = scanner.nextInt();
        } catch (Exception e) {
            throw new MyException("Invalid Input : ", e);
        }
        if (option == 1) {
            System.out.print("Enter LaptopId : ");
            int LaptopId = 0;
            try {
                LaptopId = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                throw new MyException("Invalid Input : ", e);
            }
            boolean isValidId = laptopService.isValidLaptopId(LaptopId);
            if (isValidId) {
                System.out.print("Enter new laptop Name : ");
                String LaptopName = scanner.nextLine();
                laptopService.updateLaptopName(LaptopName, LaptopId);
                logger.info("laptop Name Changed");
            } else {
                logger.info("laptop Id not found");
            }
        } else {
            logger.info("Choose 1 only");
        }

    }

    /**
     * Show Menu for laptop CRUD Operation.
     */
    public void laptopManagementMenu() {
        LaptopController LaptopController = new LaptopController();
        boolean flag = false;
        while (!flag) {
            System.out.println(" (1) Add laptop " + '\n'
                    + " (2) Remove laptop " + '\n'
                    + " (3) View laptop " + '\n'
                    + " (4) Edit laptop " + '\n'
                    + " (5) Back " + '\n'
                    + "************************************");
            System.out.print("Enter the Number : ");
            int option = 0;
            try {
                option = scanner.nextInt();
                if (option == 1) {
                    LaptopController.createLaptop();
                    flag = false;
                } else if (option == 2) {
                    LaptopController.deleteLaptop();
                    flag = false;
                } else if (option == 3) {
                    LaptopController.viewLaptop();
                    flag = false;
                } else if (option == 4) {
                    LaptopController.updateLaptop();
                    flag = false;
                } else if (option == 5) {
                    flag = true;
                } else {
                    System.out.println(" Choose 1 - 5 Only ");
                    flag = false;
                }
            } catch (InputMismatchException e) {
                logger.warn("Invalid Input Choose Number");
                LaptopController.laptopManagementMenu();
            } catch (MyException e) {
                logger.error(e.getMessage());
            }
        }
    }
}