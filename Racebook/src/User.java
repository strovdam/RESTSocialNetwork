import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class User implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4940917422868833647L;
	private String username;
	private String password;
	
	private List<String> friends;
	private List<Message> messages;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		
		friends = new ArrayList<String>();
		messages = new ArrayList<Message>();
	}

	public List<String> getFriends() {
		return friends;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Message> getMessages() {
		return messages;
	}
	
	
	
	
}
