package Service;

import java.util.List;
import Model.Message;
import DAO.AccountDAOImpl;
import DAO.MessageDAOImpl;

// Service class for Message handling

public class MessageService {
    MessageDAOImpl messageDAO = new MessageDAOImpl();
    AccountDAOImpl accountDAO = new AccountDAOImpl();
   
    public Message createNewMessage(Message message) {

        if (message.getMessage_text().equals("")) {
            return null;
        } else if (message.getMessage_text().length() > 255) {
            return null;
        } else if (accountDAO.getAccountById(message.getPosted_by()) == null) {
            return null;
        }
        return messageDAO.insertMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int msgId) {
        return messageDAO.getMessageById(msgId);
    }

    public Message updateMessage(String newString, int msgId) {
        if (messageDAO.getMessageById(msgId) == null) {
            return null;
        } else if (newString.equals("") || newString.length() > 255) {
            return null;
        }
        return messageDAO.updateMessage(newString, msgId);
    }

    public Message deleteMessageById(int msgId) {
        return messageDAO.deleteMessageById(msgId);
    }

}
