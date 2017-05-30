import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.json.JSONArray;

@Path("racebook")
public class SocialHandler {
	@Path("")
	@GET
	@Produces("text/plain")
	public String help() {
		StringBuilder sb = new StringBuilder();
		sb.append("This is the help-page of RACEBOOK\n");
		sb.append("Command list\n\n");
		sb.append("register/<username>/<password>\n");
		sb.append("<username>/post/<message>\n");
		sb.append("home/<username>/<password>\n");
		sb.append("addfriend/<username>/<target>\n");
		sb.append("feed/<username>\n");
		sb.append("users\n");
		sb.append("<username>/messages\n");
		sb.append("<username>/friends\n");
		return sb.toString();
	}
	
	@Path("register/{username}/{password}")
	@GET
	@Produces("text/plain")
	public String register(@PathParam("username") String user, @PathParam("password") String password) {
		boolean ok = DataBase.registerUser(new User(user, password));
		if(ok)
			return "User " + user + " registered successfully with password " + password;
		else
			return "The username is already taken";
	}
	@Path("{username}/post/{message}")
	@GET
	@Produces("text/plain")
	public String post(@PathParam("username") String username, @PathParam("message") String message) {
		DataBase.postMessage(username, message);
		return "Message submitted successfully";
	}
	@Path("home/{username}/{password}")
	@GET
	@Produces("text/plain")
	public String getHome(@PathParam("username") String username, @PathParam("password") String password) {
		User u = DataBase.userFromName(username);
		if(!u.getPassword().equals(password))
			return "Wrong Password!";
		List<Message> allMsgs = new ArrayList<Message>();
		StringBuilder feed = new StringBuilder();
		for(String fs : u.getFriends()) {
			User f = DataBase.userFromName(fs);
			for(Message m : f.getMessages())
				allMsgs.add(m);
		}
		allMsgs.sort(new Comparator<Message>() {

			@Override
			public int compare(Message o1, Message o2) {
				return o2.getTime().compareTo(o1.getTime());
			}
		});
		for(Message m : allMsgs) {
			feed.append(m.getSender());
			feed.append("\n");
			feed.append(m.getText());
			feed.append("\n\n\n");
		}
		return feed.toString();
	}
	@Path("addfriend/{username}/{target}")
	@GET
	@Produces("text/plain")
	public String addFriend(@PathParam("username") String username, @PathParam("target") String target) {
		User u = DataBase.userFromName(username);
		User t = DataBase.userFromName(target);
		if(u.getFriends().contains(t)) {
			return "You are already friends with " + target;
		}
		else if(t != null && u != null) {
			u.getFriends().add(t.getUsername());
			return "User added successfully";
		} else {
			return "The user does not exist";
		}
	}
	@Path("feed/{username}")
	@GET
	@Produces("text/plain")
	public String showFeed(@PathParam("username") String username) {
		StringBuilder sb = new StringBuilder();
		User u = DataBase.userFromName(username);
		sb.append(u.getUsername() + " is friends with:\n");
		for(String f : u.getFriends()) {
			sb.append(f);
			sb.append("\n");
		}
		sb.append("-------------------------\n");
		for(Message m : u.getMessages()) {
			sb.append(m.getTime().toString());
			sb.append("\t-\t");
			sb.append(m.getText());
			sb.append("\n\n\n");
		}
		return sb.toString();
	}
	@Path("users")
	@GET
	@Produces("application/json")
	public String getUsers() {
		return new JSONArray(DataBase.getUsers()).toString();
	}
	@Path("{username}/messages")
	@GET
	@Produces("application/json")
	public String getMessages(@PathParam("username") String username) {
		User u = DataBase.userFromName(username);
		return new JSONArray(u.getMessages()).toString();
	}
	@Path("{username}/friends")
	@GET
	@Produces("application/json")
	public String getFriends(@PathParam("username") String username) {
		User u = DataBase.userFromName(username);
		return new JSONArray(u.getFriends()).toString();
	}
}
