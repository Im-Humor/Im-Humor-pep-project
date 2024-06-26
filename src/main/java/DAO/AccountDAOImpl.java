package DAO;

import java.util.List;
import java.util.ArrayList;
import Model.Account;
import Model.Message;
import DAO.AccountDAO;
import Util.ConnectionUtil;
import java.sql.*;

public class AccountDAOImpl implements AccountDAO {

    public Account insertAccount(Account newAccount) {
        Connection conn = ConnectionUtil.getConnection();

        Account returnAcc = null;

        try {

            PreparedStatement ps = conn.prepareStatement(
                "insert into account (username, password) values (?, ?)"
            );

            ps.setString(1, newAccount.getUsername());
            ps.setString(2, newAccount.getPassword());

            int result = ps.executeUpdate();
            if (result != 1) {
                return returnAcc;
            }
            return getAccountByUsername(newAccount.getUsername());

        } catch (SQLException e) {
            e.printStackTrace();
            return returnAcc;
        }
    }


    public Account getAccountByUsername(String username) {
        Connection conn = ConnectionUtil.getConnection();
        Account returnAcc = null;

        try {

            PreparedStatement ps = conn.prepareStatement(
                "select * from account where username = ?"
            );

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                returnAcc = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                    );
                    return returnAcc;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnAcc;
    }

    public List<Message> getAccountMessages(Account account) {
        List<Message> arr = new ArrayList<Message>();
        return arr;
    }

}
