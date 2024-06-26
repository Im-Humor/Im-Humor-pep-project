package Service;

import Model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import DAO.AccountDAOImpl;

public class AccountService {
    AccountDAOImpl accountDAO = new AccountDAOImpl();

    public Account addNewAccount(Account account) {
        Account returnAcc = null;

        if (accountDAO.getAccountByUsername(account.getUsername()) != null) {
            return returnAcc;
        }
        if (account.getUsername().length() == 0) {
            return returnAcc;
        }
        else if (account.getPassword().length() < 4) {
            return returnAcc;
        }
        return accountDAO.insertAccount(account);
    }
    
    public boolean verifyUserPassword(Account account) {
        return false;
    };

    public Account authenticateAccount(Account account) {
        Account userAccount = accountDAO.getAccountByUsername(account.getUsername());
        if (userAccount == null) {
            return userAccount;
        }
        if (userAccount.getUsername().equals(account.getUsername()) && userAccount.getPassword().equals(account.getPassword())) {
            return userAccount;
        }
        else {
            return userAccount = null;
        }
    }
}
