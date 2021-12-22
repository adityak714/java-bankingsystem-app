package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.models.Customer;
import com.salmon.spicysalmon.models.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TransactionController {

    private static final LinkedHashMap<String, LinkedHashMap<String, ArrayList<Transaction>>> allTransactions = new LinkedHashMap<>();

    public void createTransaction(String acc1, String acc2, double amount){
        Transaction transaction1 = new Transaction(acc1, acc2, 0-amount);
        Transaction transaction2 = new Transaction(acc2, acc1, amount);
        String SSN1 = acc1.substring(0,9);
        String accID1 = acc1.substring(10,11);
        String SSN2 = acc2.substring(0,9);
        String accID2 = acc2.substring(10,11);
        allTransactions.get(SSN1).get(accID1).add(transaction1);
        allTransactions.get(SSN2).get(accID2).add(transaction2);
    }

    public boolean checkIfSSNUnique(String SSN) { // Armin: use verb when naming methods
        return allTransactions.get(SSN) == null;
    }

    public String ascendingTransactionsByPriceForAccount (String SSN, String accID){
        try {
            CustomerController customerController = new CustomerController();
            Customer desiredCustomer = customerController.findCustomer(SSN);
            ArrayList<Transaction> sortedList = sortTransactionsByPriceInAcc(SSN, accID);
            String sortedTransactions = "";
            for (Transaction transaction : sortedList) {
                sortedTransactions += transaction;
            }
            return sortedTransactions;
        } catch (Exception customerNotFound) {
            return customerNotFound.getMessage();
        }
    }

    public ArrayList<Transaction> sortTransactionsByPriceInAcc (String SSN, String accID){
        ArrayList<Transaction> sortedList = allTransactions.get(SSN).get(accID);
        Collections.sort(sortedList);
        return sortedList;
    }

    public String descendingTransactionsByPriceForAccount (String SSN, String accID) {
        ArrayList<Transaction> sortedList = sortTransactionsByPriceInAcc(SSN, accID);
        String sortedTransactions = "";
        for(int i = sortedList.size(); i > 0; i--){
            sortedTransactions += sortedList.get(i) + Util.EOL;
        }

        return sortedTransactions;
    }

    public String printTransactionsForAnAccount(String SSN, String accID){
        try {
            CustomerController customerController = new CustomerController();
            String transactionForAnAccount = "";
            Customer customer = customerController.findCustomer(SSN);
            for(Transaction transaction : allTransactions.get(SSN).get(accID)){
                transactionForAnAccount += transaction + Util.EOL;
            }
            return transactionForAnAccount;
        } catch (Exception customerNotFound){
            return customerNotFound.getMessage();
        }
    }

    public String printTransactionsForAllAccounts(String SSN){
        try {
            CustomerController customerController = new CustomerController();
            String transactionsForAllAccounts = "";
            Customer customer = customerController.findCustomer(SSN);
            for(String accountKey : allTransactions.get(SSN).keySet()){
                for(Transaction transaction : allTransactions.get(SSN).get(accountKey)){
                    transactionsForAllAccounts += transaction + Util.EOL;
                }
            }
            return transactionsForAllAccounts;
        }
        catch (Exception customerNotFound){
            return customerNotFound.getMessage();
        }
    }

    public ArrayList<Transaction> sortByDateEarliest(String SSN, String accID){
        ArrayList<Transaction> sortedList = allTransactions.get(SSN).get(accID);
        sortedList.sort(Comparator.comparing(Transaction::getDATE));
        return sortedList;
    }

    public String printTransactionsSortedEarliest(String SSN, String accID){
        String transactionsList = "";
        for(Transaction transaction : sortByDateEarliest(SSN, accID)){
            transactionsList += transaction + Util.EOL;
        }

        return transactionsList;
    }

    public String printTransactionsSortedLatest(String SSN, String accID){
        ArrayList<Transaction> sortedList = sortByDateEarliest(SSN, accID);
        String transactionsList = "";
        for(int i=sortedList.size()-1; i>=0; i--){
            transactionsList += sortedList.get(i) + Util.EOL;
        }
        return transactionsList;
    }

    public String sortByDateInterval (String SSN, String accID, String startInterval, String endInterval){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        String limitedTransactionList = "";
        ArrayList<Transaction> desiredAccount = allTransactions.get(SSN).get(accID);

        try {
            Date lowerBoundDate = formatter.parse(startInterval);
            Date upperBoundDate = formatter.parse(endInterval);
            Date currentDay = calendar.getTime();

            //If the customer sets an upperbound of ex. 2034, the method sets it back to the current date
            if(upperBoundDate.after(currentDay)){
                upperBoundDate = currentDay;
            }

            //filtering Transactions in given bounds happens here
            for (Transaction transaction : desiredAccount) {
                Date toBeCompared = formatter.parse(transaction.getDATE());
                if (toBeCompared.after(lowerBoundDate) && toBeCompared.before(upperBoundDate)) {
                    limitedTransactionList += transaction + System.lineSeparator();
                }
            }
        }

        catch (ParseException p){
            return "Please enter the date in the form YYYY/MM/DD";
        }

        return limitedTransactionList;
    }
}
