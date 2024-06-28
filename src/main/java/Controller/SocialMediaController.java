package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import java.util.List;


public class SocialMediaController {
    ObjectMapper mapper = new ObjectMapper();
    AccountService accService = new AccountService();
    MessageService msgService = new MessageService();

    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // Endpoint handling functions
        app.post("register", (context) -> this.registerUser(context));
        app.post("login", this::loginUser);
        app.post("messages", this::postMessage);
        app.get("messages", this::getAllMessages);
        app.get("messages/{message_id}", this::getMessageById);
        app.delete("messages/{message_id}", this::deleteMessageById);
        app.patch("messages/{message_id}", this::updateMessage);
        app.get("accounts/{account_id}/messages", this::getAccountMessages);

        return app;
    }

    private void registerUser(Context context) throws JsonMappingException, JsonProcessingException {
        Account userAccount = mapper.readValue(context.body(), Account.class);
        Account resultAcc = accService.addNewAccount(userAccount);
        if (resultAcc == null) {
            context.status(400);
        } else {
            context.status(200);
            context.json(resultAcc);
        }
    }

    private void loginUser(Context context) throws JsonMappingException, JsonProcessingException {
        Account userAccount = mapper.readValue(context.body(), Account.class);
        Account verifiedUser = accService.authenticateAccount(userAccount);
        if (verifiedUser == null) {
            context.status(401);
        } else {
            context.status(200);
            context.json(verifiedUser);
        }
    }

    private void postMessage(Context context) throws JsonMappingException, JsonProcessingException {
        Message message = mapper.readValue(context.body(), Message.class);
        Message newMessage = msgService.createNewMessage(message);

        if (newMessage == null) {
            context.status(400);
        } else {
            context.status(200);
            context.json(newMessage);
        }
    }

    private void getAllMessages(Context context) {
        List<Message> msgList = msgService.getAllMessages();
        context.json(msgList);
    }

    private void getMessageById(Context context) {
        int msgId = Integer.parseInt(context.pathParam("message_id"));
        Message returnMsg = msgService.getMessageById(msgId);

        if (returnMsg == null) {
            context.json("");
        } else {
            context.json(returnMsg);
        }

    }

    private void deleteMessageById(Context context) {
        int msgId = Integer.parseInt(context.pathParam("message_id"));
        Message returnMsg = msgService.deleteMessageById(msgId);

        if (returnMsg == null) {
            context.status(200);
        } else {
            context.status(200);
            context.json(returnMsg);    
        }

    }

    private void updateMessage(Context context) throws JsonMappingException, JsonProcessingException {
        Message newMsg = mapper.readValue(context.body(), Message.class);
        String newString = newMsg.getMessage_text();
        int msgId = Integer.parseInt(context.pathParam("message_id"));
        
        Message returnMsg = msgService.updateMessage(newString, msgId);
        if (returnMsg == null) {
            context.status(400);
        } else {
            context.status(200);
            context.json(returnMsg);
        }
    }

    private void getAccountMessages(Context context) {
        int accId = Integer.parseInt(context.pathParam("account_id"));
        List<Message> msgList = accService.getAccountMessages(accId);

        context.status(200);
        context.json(msgList);
    }

}