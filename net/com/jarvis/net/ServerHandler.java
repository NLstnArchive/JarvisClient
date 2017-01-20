package com.jarvis.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.jarvis.net.sockets.FileSocketHandler;
import com.jarvis.net.sockets.MessageSocketHandler;
import com.jarvis.utils.Logger;
import com.jarvis.utils.Logger.Level;

public class ServerHandler {

	// TODO need a way to filter all received messages for the right one!

	static final String				PREFIX_CONNECT		= "/c/";
	static final String				PREFIX_DISCONNECT	= "/dc/";
	static final String				PREFIX_FILE			= "/f/";
	static final String				PREFIX_FILE_INFO	= "/fi/";

	private volatile boolean		connected			= false;

	private InetAddress				serverAddress;

	private MessageSocketHandler	messageHandler;
	private FileSocketHandler		fileHandler;

	public ServerHandler() {
		try {
			serverAddress = InetAddress.getByName("localhost");
		}
		catch (UnknownHostException e) {
			Logger.error("Couldn't evaluate ServerAddress! " + e.getMessage(), Level.LVL1);
		}
	}

	public boolean connect() {
		messageHandler = new MessageSocketHandler();
		connected = true;
		String connectionMessage = new String(messageHandler.receiveMessage());
		if (!connectionMessage.startsWith(PREFIX_CONNECT)) {
			Logger.error("Received invalid connection message! " + connectionMessage, Level.LVL1);
			connected = false;
			return false;
		}
		int id = 0;
		try {
			id = Integer.parseInt(connectionMessage.split("/")[2]);
		}
		catch (Exception e) {
			Logger.error("Failed to read UUID from packet: " + connectionMessage, Level.LVL1);
			return false;
		}
		messageHandler.sendMessage(PREFIX_CONNECT);
		fileHandler = new FileSocketHandler();
		if (!fileHandler.connect(id)) {
			Logger.error("Failed to connect fileSocket!", Level.LVL1);
			return false;
		}
		Logger.info("Successfully connected to server!", Level.LVL1);
		return true;
	}

	public void send(String message) {
		messageHandler.sendMessage(message);
	}

	public void disconnect() {
		Logger.info("Disconnecting from Server...", Level.LVL1);
		messageHandler.sendMessage(PREFIX_DISCONNECT);
		String answer = messageHandler.receiveMessage();
		connected = false;
		if (!answer.equals("/dc/")) {
			Logger.error("Invalid disconnect answer from server! " + answer, Level.LVL1);
			messageHandler.close();
			fileHandler.close();
		}
		Logger.info("Disconnected from server!", Level.LVL1);
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public boolean isConnected() {
		return connected;
	}

	public InetAddress getServerAddress() {
		return serverAddress;
	}
}
