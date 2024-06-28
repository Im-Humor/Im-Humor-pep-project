package DAO;

import Model.Message;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import Util.ConnectionUtil;

// Message DAO Implementation class with methods

public class MessageDAOImpl {

    public Message insertMessage(Message message) {
        Connection conn = ConnectionUtil.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("insert into message (posted_by, message_text, time_posted_epoch) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
    
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            int returnVal = ps.executeUpdate();
            if (returnVal == 0) {
                return null;
            }

            ResultSet rs = ps.getGeneratedKeys();

            int msgId = 0;
            if (rs.next()) {
                msgId = rs.getInt(1);
            }

            return getMessageById(msgId);
    
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Message getMessageById(int id) {
        Connection conn = ConnectionUtil.getConnection();
        Message returnMsg = null;
    
        try {
            PreparedStatement ps = conn.prepareStatement("select * from message where message_id = ?");
    
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                returnMsg = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            }

            return returnMsg;
    
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Message updateMessage(String newString, int msgId) {
        Connection conn = ConnectionUtil.getConnection();

        if (getMessageById(msgId) == null) {
            return null;
        }
    
        try {
            PreparedStatement ps = conn.prepareStatement("update message set message_text = ? where message_id = ?");
    
            ps.setString(1, newString);
            ps.setInt(2, msgId);


            int returnVal = ps.executeUpdate();
            if (returnVal == 0) {
                return null;
            }

            return getMessageById(msgId);
    
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Message> getAllMessages() {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> msgList = new ArrayList<Message>();
    
        try {
            PreparedStatement ps = conn.prepareStatement("select * from message");

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

    public Message deleteMessageById(int id) {
        Connection conn = ConnectionUtil.getConnection();
        Message returnMsg = getMessageById(id);

        if (returnMsg == null) {
            return null;
        }

        try {
            PreparedStatement ps = conn.prepareStatement("delete from message where message_id = ?");

            ps.setInt(1, id);
            ps.executeUpdate();
            return returnMsg;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
