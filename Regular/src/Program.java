
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Program {

	public static void main(String[] args) throws Exception {
		
		Message m = new Message("2011-01-01 21:21:21 !!!!! ParisServerResponse 'From Paris with Love!)))'");
		
		System.out.println("Date sent:" + m.getDate());
		System.out.println("Message Importance: " + m.getCharacters());
		System.out.println("From: " + m.getSource());
		System.out.println("Message contains: " + m.getMessage());
	}

}
