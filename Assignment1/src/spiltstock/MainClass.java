package spiltstock;
/* Assignment 1: Stock Spilts
 * Class: cs 330
 * Name: Toan Nguyen
 * ID: W00989643
 */

import java.util.*;
/**
 *
 * @author v90207
 */
class spiltstock {
    public static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args){
        // Calling MainMenu() method
        MainMenu();
    }
    
    public static void encryptData (int number) { // return type can be changed if necessary
        // type your code here
        int newNum = 0;
        int temp = 0;
        
        for(int i = 0; i <= 3; i++)
        {
           newNum = number % (10 ^ (i + 1));
           number -= newNum;
           System.out.println(newNum);
        }
    }
    
 
    public static void listSalaryCalculation() {
 // type your code here
        System.out.print("Enter the pay code: ");
        int codeNum = sc.nextInt();
        // Declaring variables
        double manGrossSalary1 = 1750.00;
        double manIncomeTax1 = 0.045;
        double hourlyWorkerWage2 = 11.45;
        double hourlyWorkerInTa2 = 0.02;
        double overtimeRate2 = 1.5;
        int fullTime2 = 40;
        double comWorkerFixedAmount3 = 250.00;
        double comWorkerWeeklySalePercent3 = 0.057;
        double comWorkerInTa3 = 0.065;
        double pieceWorkerWage4 = 0.56;
        
        // Initializing variables
        double grossSalary = 0;
        double netSalary = 0;
        int hourWorked2 = 0;
        int overtimeHours2 = 0;
        double grossWeeklySales3 = 0;
        int numOfItemsProduced4 = 0;
        
        switch(codeNum)
        {
            case 1: 
                netSalary = manGrossSalary1 - (manGrossSalary1 * manIncomeTax1);
                
                System.out.format("%10s%19s%15s", "Pay code", 
                                  "Type of Employee", "Net salary \n");
                System.out.format("%10s%19s%15s", "--------", 
                                  "----------------", "---------- \n");
                System.out.format("%7s%17s%18s", codeNum, "Manager", "$" + netSalary + "\n");
                System.out.format("  ----------------------------------------\n");
                break;
            case 2:
                System.out.print("Enter the hour worked: ");
                hourWorked2 = sc.nextInt();
                overtimeHours2 = hourWorked2 - fullTime2;
                grossSalary = (hourlyWorkerWage2 * fullTime2) + 
                              (hourlyWorkerWage2 * overtimeRate2 * overtimeHours2);
                if(grossSalary <= 700)
                    netSalary = grossSalary;
                else
                    netSalary = grossSalary - (grossSalary * hourlyWorkerInTa2);
                
                System.out.format("%10s%19s%15s%15s%15s", "Pay code", "Type of Employee", 
                                  "No of hours worked", "overtime hours", "Net salary \n");
                System.out.format("%10s%19s%15s", "--------", 
                                  "----------------", "---------- \n");
                
                System.out.format("%7s%17s%18s%18s%18s", codeNum, "hourly worker",
                                  hourWorked2, overtimeHours2, "$" + netSalary + "\n");
                System.out.format("  ----------------------------------------\n");
                break;
            case 3:
                System.out.print("Gross weekly sales(in $): ");
                grossWeeklySales3 = sc.nextDouble();
                grossSalary = comWorkerFixedAmount3 + 
                              (grossWeeklySales3 * comWorkerWeeklySalePercent3);
                if(grossSalary <= 550)
                {
                    netSalary = grossSalary;
                }
                else
                    netSalary = grossSalary - (grossSalary * comWorkerInTa3);
                
                System.out.format("%10s%19s%15s%15s%15s", "Pay code", "Type of Employee", 
                                  "gross sales (weekly)", "fixed amount", "Net salary \n");
                System.out.format("%10s%19s%15s", "--------", 
                                  "----------------", "---------- \n");
                
                System.out.format("%7s%17s%18s%18s%18s", codeNum, "commission worker",
                                  "$" + grossWeeklySales3, "$" + comWorkerFixedAmount3, 
                                  "$" + netSalary + "\n");
                System.out.format("  ----------------------------------------\n");
            case 4:
                System.out.print("Number of items produced (for a week): ");
                numOfItemsProduced4 = sc.nextInt();
                netSalary = numOfItemsProduced4 * pieceWorkerWage4;
                
                System.out.format("%10s%19s%15s%15s", "Pay code", "Type of Employee", 
                                  "items produced", "Net salary \n");
                System.out.format("%10s%19s%15s", "--------", 
                                  "----------------", "---------- \n");
                
                System.out.format("%7s%17s%18s%18s", codeNum, "piece worker",
                                  numOfItemsProduced4, "$" + netSalary + "\n");
                System.out.format("  ----------------------------------------\n");
                break;
        }   
    }

    public static void MainMenu () {
    // type your code here
        System.out.println("1) Salary calculation");
        System.out.println("2) Encrypt - data");
        System.out.println("3) Exit");
        System.out.print("Select the option: ");
        int num = sc.nextInt();
        switch(num)
        {
            case 1:
                listSalaryCalculation();
                MainMenu();
                break;
            case 2:
                System.out.print("Enter the number to be encrypted: ");
                int encryptedNum = sc.nextInt();
                encryptData(encryptedNum);
                MainMenu();
                break;
            case 3:
                System.exit(o);
                break;
        }
    }
}
