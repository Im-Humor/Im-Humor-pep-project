package DAO;

import java.util.List;
import Model.Message;

// DAO interface for implementation object

public interface MessageDAO {
    public Message insertMessage(Message message);
    public Message getMessageById(int id);
    public Message updateMessage(String newString, int id);
    public Message deleteMessageById(int id);
    public List<Message> getAllMessages();
}
