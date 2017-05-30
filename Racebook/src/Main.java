import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.json.JSONArray;


public class Main {

	private final static int port= 80;
	private final static String host="http://localhost/";
	public static void main(String[] args) {
		DataBase.load("data");
		URI baseUri= UriBuilder.fromUri(host).port(port).build();
		ResourceConfig config = new ResourceConfig(SocialHandler.class);
		JdkHttpServerFactory.createHttpServer(baseUri, config);
	}
}
