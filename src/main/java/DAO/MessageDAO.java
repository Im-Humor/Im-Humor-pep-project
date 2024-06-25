package DAO;

import java.util.List;
import Model.Message;

public interface MessageDAO {
    public boolean insertMessage(Message message);
    public Message getMessageById(int id);
    public boolean updateMessage(Message message);
    public List<Message> getAllMessages();
}
