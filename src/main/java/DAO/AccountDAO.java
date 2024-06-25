package DAO;

import java.util.List;

import Model.Account;
import Model.Message;

public interface AccountDAO {
    public boolean insertAccount(Account newAccount);
    public Account getAccountByUsername(String username);
    public List<Message> getAccountMessages(Account account);
}
