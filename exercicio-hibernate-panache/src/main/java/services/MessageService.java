package services;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Message;
import model.User;

@Path("/message")
@Transactional
public class MessageService {
    
    @GET
    @Path("/add/{name}/{text}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Message addMessage(@PathParam("name") String name, @PathParam("text") String text){
        Message message = new Message();
        message.setMessage(text);
        message.persist();

        User user = User.find("name", name).firstResult();
        user.addMessage(message);
        user.persist();

        return message;
    }

}
