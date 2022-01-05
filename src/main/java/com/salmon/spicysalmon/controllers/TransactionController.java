package com.salmon.spicysalmon.controllers;

import com.salmon.spicysalmon.Util;
import com.salmon.spicysalmon.models.BankAccount;
import com.salmon.spicysalmon.models.Customer;
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

    public void initializeCustomer(String SSN){
        LinkedHashMap<String, ArrayList<Transaction>> newMap = new LinkedHashMap<>();
        allTransactions.put(SSN, newMap);
    }

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

    public boolean checkIfSSNUnique(String SSN) {
        return allTransactions.get(SSN) == null;
    }

    public String sortTransactionsAscending (String SSN, String accID){
        ArrayList<Transaction> sortedList = sortTransactionsByAmount(SSN, accID);
        return transactionsStringBuilder(sortedList);
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
        Collections.reverse(sortedList);1
        return transactionsStringBuilder(sortedList);
    }

    public String printTransactionsForAnAccount(String SSN, String accID){
        try {
            String transactionForAnAccount = "====================================================" + Util.EOL;
            for(Transaction transaction : allTransactions.get(SSN).get(accID)){
                transactionForAnAccount += transaction + Util.EOL +
                        "====================================================" + Util.EOL;
            }
            return transactionForAnAccount;
        } catch (Exception customerNotFound){
            return customerNotFound.getMessage();
        }
    }

    // Print transactions for all accounts
    public String printTransactionsForAllAccounts(String SSN){
        try {
            String transactionsForAllAccounts = "====================================================" + Util.EOL;
            for(String accountKey : allTransactions.get(SSN).keySet()){
                for(Transaction transaction : allTransactions.get(SSN).get(accountKey)){
                    transactionsForAllAccounts += transaction + Util.EOL +
                            "====================================================" + Util.EOL;
                }
            }
            return transactionsForAllAccounts;
        }
        catch (Exception customerNotFound){
            return customerNotFound.getMessage();
        }
    }

    public String printAllTransactions(){
        String result = "All Registered Transactions" + Util.EOL;
        result += "====================================================" + Util.EOL;
        if(!allTransactions.isEmpty()){
            for(Transaction transaction: sortTransactionsDateEarliest()){
                result += transaction + Util.EOL +
                        "====================================================" + Util.EOL;
            }
        } else{
            result += "No transactions registered yet." + Util.EOL;
        }
        return result;
    }

    public ArrayList<Transaction> sortTransactionsDateEarliest(String SSN, String accID){
        ArrayList<Transaction> sortedList = new ArrayList<>(allTransactions.get(SSN).get(accID));
        sortedList.sort(Comparator.comparing(Transaction::getDATE));
        return sortedList;
    }

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

    public String printTransactionsSortedEarliest(String SSN, String accID){
        ArrayList<Transaction> sortedEarliest = sortTransactionsDateEarliest(SSN, accID);
        return transactionsStringBuilder(sortedEarliest);
    }

    public String printTransactionsSortedLatest(String SSN, String accID){
        ArrayList<Transaction> sortedList = sortTransactionsDateEarliest(SSN, accID);
        Collections.reverse(sortedList);
        return transactionsStringBuilder(sortedList);
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

    public String transactionsStringBuilder(ArrayList<Transaction> transactions){
        CustomerController customerController = new CustomerController();

        StringBuilder sb = new StringBuilder();
        String ID, ACC1, ACC2, DATE;
        double AMOUNT;

        int counter = 0;
        for(Transaction transaction: transactions){
            ID = transaction.getID();
            ACC2 = transaction.getACC2();
            DATE = transaction.getDATE().substring(0,11);
            AMOUNT = transaction.getAMOUNT();

            // get sender/reciever name
            BankAccount theAccount = theAccount = customerController.findBankAccount(ACC2.substring(0,10), ACC2.substring(10,12));
            // trim to 10, if firstName is longer than 10 letters
            String firstName = theAccount.getCustomerFirstName();
            firstName = firstName.length() > 10 ? firstName.substring(0,11)+"." : firstName;
            // concatenate the first letter of the last Name
            String personName = firstName + " " + theAccount.getCustomerLastName().charAt(0)+".";
            String toFrom = personName + " ("+ACC2+")";
            // append with appropriate number of spaces
            sb.append(ID).append(" ".repeat(8)).append(toFrom).append(" ".repeat(AMOUNT > 0 ? 32-toFrom.length() : 31-toFrom.length()))
                    .append(String.format("%.2f", AMOUNT)).append(" ".repeat(AMOUNT > 0 ? 13-String.format("%.2f", AMOUNT).length() : 14-String.format("%.2f", AMOUNT).length()))
                    .append(DATE).append(" ".repeat(19-DATE.length()));
            counter += 1;
            if(counter != transactions.size()){
                sb.append(Util.EOL);
            }
        }

        if(transactions.isEmpty()){
            return "No transactions for this account have been recorded.";
        }

        return Util.EOL
                + "-----------------------------------------------------------------------------" + Util.EOL
                + "ID                 " + "To/From                        " + "Amount        " + "Date   " + Util.EOL
                + "-----------------------------------------------------------------------------" +  Util.EOL
                + sb + Util.EOL
                + "-----------------------------------------------------------------------------" + Util.EOL;
    }

}
