import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DataBase {
	private static List<User> users = new ArrayList<User>();

	public static boolean registerUser(User user) {
		for(User u : users)
			if(u.getUsername().equals(user.getUsername()))
				return false;
		users.add(user);
		save("data");
		return true;
	}
	
	public static List<User> getUsers() {
		return users;
	}
	
	public static void postMessage(String username, String m) {
		for(User u : users) {
			if(u.getUsername().equals(username)) {
				Message msg = new Message(m, new Date(), u.getUsername());
				u.getMessages().add(msg);
				save("data");
				return;
			}
		}
	}
	
	public static User userFromName(String username) {
		for(User u : users) {
			if(u.getUsername().equals(username)) {
				save("data");
				return u;
			}
		}
		return null;
	}
	
	public static void save(String path) {
		try {
			File f = new File(path);
			if(f.exists()) {
				f.delete();
			}
			f.createNewFile();
			ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(path));
			for(User u : users) {
				o.writeObject(u);
			}
			o.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void load(String path) {
		try {
			File f = new File(path);
			if(!f.exists()) {
				return;
			}
			ObjectInputStream o = new ObjectInputStream(new FileInputStream(path));
			Object u;
			try {
				while((u = o.readObject()) != null) {
					users.add((User)u);
				}
			} catch (EOFException e) { }
			o.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
