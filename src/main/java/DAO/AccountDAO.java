package DAO;

import java.util.List;

import Model.Account;
import Model.Message;

// DAO interface for implementation object

public interface AccountDAO {
    public Account insertAccount(Account newAccount);
    public Account getAccountByUsername(String username);
    public Account getAccountById(int id);
    public List<Message> getAccountMessages(int id);
}