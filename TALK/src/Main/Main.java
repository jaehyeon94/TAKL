package Main;

import java.io.IOException;

import GUI.Login.login;
import Socket.SocketClient;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		SocketClient client = new SocketClient();
		
		login login = new login(client);
		login.setVisible(true);
		

	}

}

