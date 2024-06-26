package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Service.AccountService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    ObjectMapper mapper = new ObjectMapper();
    AccountService accService = new AccountService();
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
        System.out.println(resultAcc);
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


}