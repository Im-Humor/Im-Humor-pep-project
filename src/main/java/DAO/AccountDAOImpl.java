package DAO;

import java.util.List;
import java.util.ArrayList;
import Model.Account;
import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;

// Account DAO Implementation class with methods

public class AccountDAOImpl implements AccountDAO {

    public Account insertAccount(Account newAccount) {
        Connection conn = ConnectionUtil.getConnection();

        try {

            PreparedStatement ps = conn.prepareStatement(
                "insert into account (username, password) values (?, ?)"
            );

            ps.setString(1, newAccount.getUsername());
            ps.setString(2, newAccount.getPassword());

            int result = ps.executeUpdate();
            if (result == 0) {
                return null;
            }
            return getAccountByUsername(newAccount.getUsername());

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Account getAccountByUsername(String username) {
        Connection conn = ConnectionUtil.getConnection();

        try {

            PreparedStatement ps = conn.prepareStatement(
                "select * from account where username = ?"
            );

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account returnAcc = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                    );
                    return returnAcc;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account getAccountById(int id) {
        Connection conn = ConnectionUtil.getConnection();

        try {

            PreparedStatement ps = conn.prepareStatement(
                "select * from account where account_id = ?"
            );

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account returnAcc = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                    );
                    return returnAcc;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Message> getAccountMessages(int accId) {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> msgList = new ArrayList<Message>();

        if (getAccountById(accId) == null) {
            return msgList;
        }
    
        try {
            PreparedStatement ps = conn.prepareStatement("select * from message where posted_by = ?");

            ps.setInt(1, accId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message returnMsg = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                msgList.add(returnMsg);
            }

            return msgList;
    
        } catch (SQLException e) {
            e.printStackTrace();
            return msgList;
        }
    }

}
