package com.guti16.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.application.model.GeneralAttribute;

public class Client extends NetWorkAbstract {
	private InetAddress serverAddress;

	public Client(String host, int port) {
		try {
			this.serverAddress = InetAddress.getByName(host);
			this.port = port;

		} catch (UnknownHostException e) {
			System.err.println("Server not found!!!");
		} catch (NullPointerException e) {
			System.err.println("Server " + serverAddress.getHostAddress() + ":"
					+ port + " is not Active");
		}

	}

	public void tryToConnect() throws IOException{
			connection = new Socket(serverAddress, port);
			displayMessage("Client:  Connected to "
					+ connection.getInetAddress().getHostName());

	}
	@Override
	public void processConnection() throws IOException {
		sendData("Header: " + " -1 2 "
				+ GeneralAttribute.getPlayerName(2));
		super.processConnection();
	}

}
