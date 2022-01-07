package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.models.BankAccount;
import com.salmon.spicysalmon.models.Transaction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TransactionController {

    private static final LinkedHashMap<String, LinkedHashMap<String, ArrayList<Transaction>>> allTransactions = new LinkedHashMap<>();

    // Creates a pair of transactions and calls the add method
    public void createTransaction(String acc1, String acc2, double amount){
        Transaction transaction1 = new Transaction(acc1, acc2, 0-amount);
        Transaction transaction2 = new Transaction(acc2, acc1, amount);
        addTransactions(acc1, acc2, transaction1, transaction2);
    }

    // Only called from the JSON controller for demonstration purposes.
    // The users of the application will not have the ability to back date transactions.
    public void createTransaction(String acc1, String acc2, double amount, String date){
        Transaction transaction1 = new Transaction(acc1, acc2, 0-amount, date);
        Transaction transaction2 = new Transaction(acc2, acc1, amount, date);
        addTransactions(acc1, acc2, transaction1, transaction2);
    }

    // used by employees to withdraw and deposit;
    public void createEmployeeTransaction(String accNum, double amount){
        if(amount > 0){
            Transaction newTransaction = new Transaction(accNum, "Spicy Deposit", amount);
            String SSN = accNum.substring(0,10);
            String id = accNum.substring(10,12);
            allTransactions.get(SSN).get(id).add(newTransaction);
        } else{
            Transaction newTransaction = new Transaction(accNum, "Spicy Withdrawal", amount);
            String SSN = accNum.substring(0,10);
            String id = accNum.substring(10,12);
            allTransactions.get(SSN).get(id).add(newTransaction);
        }
    }

    //Method to initialize transactions for a customer
    public void initializeCustomer(String SSN){
        LinkedHashMap<String, ArrayList<Transaction>> newMap = new LinkedHashMap<>();
        allTransactions.put(SSN, newMap);
    }
    //Method to initialize bank account
    public void initializeBankAccount(String SSN, String accID){
        ArrayList<Transaction> newList= new ArrayList<>();
        allTransactions.get(SSN).put(accID, newList);
    }

    // Adds two transactions to their respective accounts
    public void addTransactions(String acc1, String acc2, Transaction transaction1, Transaction transaction2){
        // Extracting SSN and accountID from accountNumber
        String SSN1 = acc1.substring(0,10);
        String accID1 = acc1.substring(10,12);
        String SSN2 = acc2.substring(0,10);
        String accID2 = acc2.substring(10,12);
        allTransactions.get(SSN1).get(accID1).add(transaction1);
        allTransactions.get(SSN2).get(accID2).add(transaction2);
    }
    //This method check to see if there are transactions that exists
    public boolean checkIfSSNUnique(String SSN) {
        return allTransactions.get(SSN) == null;
    }

    public String sortTransactionsAscending (String SSN, String accID){
        ArrayList<Transaction> sortedList = sortTransactionsByAmount(SSN, accID);
        try {
            return transactionsStringBuilder(sortedList);
        } catch(Exception e){
            return "There was a problem printing the transactions. Please contact the bank.";
        }
    }

    // Sort transactions by amount
    public ArrayList<Transaction> sortTransactionsByAmount (String SSN, String accID){
        ArrayList<Transaction> sortedList = allTransactions.get(SSN).get(accID);
        Collections.sort(sortedList);
        return sortedList;
    }

    // Sort transactions by descending amount
    public String sortTransactionsDescending (String SSN, String accID) { // arre tis 4 jan 20:54: bug in this method
        ArrayList<Transaction> sortedList = sortTransactionsByAmount(SSN, accID);
        Collections.reverse(sortedList);
        try {
            return transactionsStringBuilder(sortedList);
        } catch(Exception e) {
            return "There was a problem printing the transactions. Please contact the bank.";
        }
    }
    //Returns a string that has the transactions for an account
    public String printTransactionsForAnAccount(String SSN, String accID){
        try {
            //get list from
            ArrayList<Transaction> list = new ArrayList<>(allTransactions.get(SSN).get(accID));
            return transactionsStringBuilderEmployee(list);
        } catch (Exception customerNotFound){
            return customerNotFound.getMessage();
        }
    }

    // Print transactions for all accounts
    public String printTransactionsForAllAccounts(String SSN){
        try {
            ArrayList<Transaction> listOfAccounts = new ArrayList<>();
            for(String accountKey : allTransactions.get(SSN).keySet()){
                listOfAccounts.addAll(allTransactions.get(SSN).get(accountKey));
            }
            return transactionsStringBuilder(listOfAccounts);
        }
        catch (Exception customerNotFound){
            return customerNotFound.getMessage();
        }
    }
    //Method returns all transactions
    public String printAllTransactions(){
        String result = "All Registered Transactions" + Util.EOL;
        if(!allTransactions.isEmpty()){
            result += transactionsStringBuilderEmployee(sortTransactionsDateEarliest());
            }
         else{
            result += "No transactions registered yet." + Util.EOL;
        }
        return result;
    }
    //Returns an arraylist of earliest transactions
    public ArrayList<Transaction> sortTransactionsDateEarliest(String SSN, String accID){
        ArrayList<Transaction> sortedList = new ArrayList<>(allTransactions.get(SSN).get(accID));
        sortedList.sort(Comparator.comparing(Transaction::getDATE));
        return sortedList;
    }
    //Returns an arraylist of earliest transactions "duplicate code"
    public ArrayList<Transaction> sortTransactionsDateEarliest(){
        ArrayList<Transaction> sortedList = new ArrayList<>();
        for(String SSN : allTransactions.keySet()){
            for(String accID: allTransactions.get(SSN).keySet()){
                sortedList.addAll(allTransactions.get(SSN).get(accID));
            }
        }
        sortedList.sort(Comparator.comparing(Transaction::getDATE));
        return sortedList;
    }
    //Methods that retrieves earliest transactions for printing
    public String printTransactionsSortedEarliest(String SSN, String accID){
        ArrayList<Transaction> sortedEarliest = sortTransactionsDateEarliest(SSN, accID);
        try {
            return transactionsStringBuilder(sortedEarliest);
        } catch(Exception e){
            return "There was a problem printing the transactions. Please contact the bank.";
        }
    }
    //Latest transactions sorted by this method
    public String printTransactionsSortedLatest(String SSN, String accID){
        ArrayList<Transaction> sortedList = sortTransactionsDateEarliest(SSN, accID);
        Collections.reverse(sortedList);
        try {
            return transactionsStringBuilder(sortedList);
        } catch(Exception e){
            return "There was a problem printing the transactions. Please contact the bank.";
        }
    }
    //This method returns transactions within a date interval
    public String sortByDateInterval (String SSN, String accID, String startInterval, String endInterval) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        ArrayList<Transaction> limitedTransactionList = new ArrayList<>();
        ArrayList<Transaction> desiredAccount = allTransactions.get(SSN).get(accID);

        try {
            //If NumberFormatException is thrown here below, the entered date does not follow YYYY/MM/DD
            int startYear = Integer.parseInt(startInterval.substring(0,4));
            int startMonth = Integer.parseInt(startInterval.substring(5,7));
            int startDay = Integer.parseInt(startInterval.substring(8,10));
            int endYear = Integer.parseInt(endInterval.substring(0,4));
            int endMonth = Integer.parseInt(endInterval.substring(5,7));
            int endDay = Integer.parseInt(endInterval.substring(8,10));

            Date lowerBoundDate = formatter.parse(startInterval);
            Date upperBoundDate = formatter.parse(endInterval);
            Date currentDay = calendar.getTime();

            //If the customer sets an lowerBound of ex. 2034, the method sets it back to the current date
            if (lowerBoundDate.after(currentDay)) {
                lowerBoundDate = currentDay;
            }

            //filtering Transactions in given bounds happens here
            for (Transaction transaction : desiredAccount) {
                Date toBeCompared = formatter.parse(transaction.getDATE());
                if (toBeCompared.after(lowerBoundDate) && toBeCompared.before(upperBoundDate)) {
                    limitedTransactionList.add(transaction);
                }
            }

            //Prevents users from entering invalid values for dates and month numbers
            if ((startDay > 31 || endDay > 31) || (startMonth > 12 || endMonth > 12) || lowerBoundDate.after(upperBoundDate)){
                return Util.EOL + "Invalid bounds provided for dates. Please try again! " + Util.EOL;
            }
        }

        //Restricts format to YYYY/MM/DD (inclusive of /)
        catch (ParseException | NumberFormatException | StringIndexOutOfBoundsException p){
            return Util.EOL + "Please enter correct date(s) in the form YYYY/MM/DD." + Util.EOL;
        }
        return transactionsStringBuilder(limitedTransactionList);
    }

    //StringBuilder for transactions used by the customer
    public String transactionsStringBuilder(ArrayList<Transaction> transactions) throws Exception{
        CustomerController customerController = new CustomerController();

        StringBuilder sb = new StringBuilder();
        String ID, ACC1, ACC2, DATE;
        double AMOUNT;

        int counter = 0;

        if(transactions.isEmpty()){
            return Util.EOL + "No transactions for this account have been recorded." + Util.EOL;
        }

        for(Transaction transaction: transactions){
            ID = transaction.getID();
            ACC2 = transaction.getACC2();
            DATE = transaction.getDATE().substring(0,11);
            AMOUNT = transaction.getAMOUNT();

            // get sender/reciever name
            String toFrom;
            if(Util.isValidAccountNumberFormat(ACC2)){
                BankAccount theAccount = customerController.findBankAccount(ACC2.substring(0,10), ACC2.substring(10,12));
                // trim to 10, if firstName is longer than 10 letters
                String firstName = theAccount.getCustomerFirstName();
                firstName = firstName.length() > 10 ? firstName.substring(0,11)+"." : firstName;
                // concatenate the first letter of the last Name
                String personName = firstName + " " + theAccount.getCustomerLastName().charAt(0)+".";
                toFrom = personName + " ("+ACC2+")";
            } else{ // if the account is the bank, as bank is not a registered customer
                toFrom = ACC2;
            }

            // append with appropriate number of spaces
            sb.append(ID).append(" ".repeat(8)).append(toFrom).append(" ".repeat(AMOUNT > 0 ? 32-toFrom.length() : 31-toFrom.length()))
                    .append(String.format("%.2f", AMOUNT)).append(" ".repeat(AMOUNT > 0 ? 13-String.format("%.2f", AMOUNT).length() : 14-String.format("%.2f", AMOUNT).length()))
                    .append(DATE).append(" ".repeat(19-DATE.length()));
            counter += 1;
            if(counter != transactions.size()){
                sb.append(Util.EOL);
            }
        }

        return Util.EOL
                + "-----------------------------------------------------------------------------" + Util.EOL
                + "ID                 " + "To/From                        " + "Amount        " + "Date   " + Util.EOL
                + "-----------------------------------------------------------------------------" +  Util.EOL
                + sb + Util.EOL
                + "-----------------------------------------------------------------------------" + Util.EOL;
    }

    //Returns a string used in printing transactions, used by the employee
    public String transactionsStringBuilderEmployee(ArrayList<Transaction> list){
        if(list.isEmpty()){
            return "No transactions for this account have been recorded.";
        }
        int number = 1;
        String amount = "";
        StringBuilder sb = new StringBuilder();
        for(Transaction transaction : list) {
            amount = String.format("%.2f",transaction.AMOUNT);
            if(!amount.startsWith("-")){ amount = " "+amount;}
            sb.append(number).append(" ".repeat(6-String.valueOf(number).length()))
                    .append(transaction.ACC1).append(" ".repeat(5))
                    .append(transaction.ACC2).append(" ".repeat(4))
                    .append(amount).append(" ".repeat(14-amount.length()))
                    .append(transaction.DATE.substring(0,10)).append(" ".repeat(4))
                    .append(transaction.ID);
            number += 1;
            if (number!= list.size() + 1) {
                sb.append(Util.EOL);
            }
        }
        return Util.EOL
                + "------------------------------------------------------------------------------" + Util.EOL
                + "#     From             To               Amount       Date          ID" + Util.EOL
                + "------------------------------------------------------------------------------" +  Util.EOL
                + sb.toString() + Util.EOL
                + "------------------------------------------------------------------------------" + Util.EOL;
    }

}

