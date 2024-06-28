package Service;

import Model.Account;
import Model.Message;
import DAO.AccountDAOImpl;
import java.util.List;

// Service class for Account handling

public class AccountService {
    AccountDAOImpl accountDAO = new AccountDAOImpl();

    public Account addNewAccount(Account account) {

        if (accountDAO.getAccountByUsername(account.getUsername()) != null) {
            return null;
        }
        if (account.getUsername().length() == 0) {
            return null;
        }
        else if (account.getPassword().length() < 4) {
            return null;
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
            return null;
        }
    }

    public List<Message> getAccountMessages(int accId) {
        return accountDAO.getAccountMessages(accId);
    }

}
