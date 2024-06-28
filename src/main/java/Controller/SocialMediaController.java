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


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    ObjectMapper mapper = new ObjectMapper();
    AccountService accService = new AccountService();
    MessageService msgService = new MessageService();
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
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

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    // I don't understand why the IDE wanted me to add this throws declaration to the function?
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