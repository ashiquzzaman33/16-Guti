package com.guti16.network;

import java.awt.Image;
import java.awt.ImageCapabilities;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import javax.swing.ImageIcon;

import com.application.model.GeneralAttribute;

public class Server extends NetWorkAbstract {
	private ServerSocket server;

	public Server(int port) {
		this.port = port;
	}

	public void tryToConnect() throws IOException {
		server = new ServerSocket(port, 1);
		connection = server.accept();
		displayMessage("Server: Connected to "
				+ connection.getInetAddress().getHostName());

	}

	@Override
	public void processConnection() throws IOException {
		sendData("Header: " + GeneralAttribute.getTimeOut() + " 1 "
				+ GeneralAttribute.getPlayerName(1));
		super.processConnection();
	}
}
