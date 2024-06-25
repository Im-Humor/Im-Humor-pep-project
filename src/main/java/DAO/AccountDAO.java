package DAO;

import java.util.List;

import Model.Account;
import Model.Message;

public interface AccountDAO {
    public Account insertAccount(Account newAccount);
    public List<Message> getAccountMessages(Account account);
}
