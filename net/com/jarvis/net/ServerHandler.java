package com.jarvis.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.jarvis.net.files.FileHandler;
import com.jarvis.net.files.FileSendRequest;
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

	private MessageSocketHandler	messageSocket;
	private FileSocketHandler		fileSocket;

	private FileHandler				fileHandler;

	public ServerHandler() {
		try {
			serverAddress = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			Logger.error("Couldn't evaluate ServerAddress! " + e.getMessage(), Level.LVL1);
		}
	}

	public boolean connect() {
		messageSocket = new MessageSocketHandler();
		connected = true;
		String connectionMessage = new String(messageSocket.receiveMessage());
		if (!connectionMessage.startsWith(PREFIX_CONNECT)) {
			Logger.error("Received invalid connection message! " + connectionMessage, Level.LVL1);
			connected = false;
			return false;
		}
		int id = 0;
		try {
			id = Integer.parseInt(connectionMessage.split("/")[2]);
		} catch (Exception e) {
			Logger.error("Failed to read UUID from packet: " + connectionMessage, Level.LVL1);
			return false;
		}
		messageSocket.sendMessage(PREFIX_CONNECT);
		fileSocket = new FileSocketHandler();
		if (!fileSocket.connect(id)) {
			Logger.error("Failed to connect fileSocket!", Level.LVL1);
			return false;
		}
		Logger.info("Successfully connected to server!", Level.LVL1);
		fileHandler = new FileHandler(messageSocket, fileSocket);
		return true;
	}

	public void send(FileSendRequest request) {
		fileHandler.submitFileSendRequest(request);
	}

	public void send(String message) {
		messageSocket.sendMessage(message);
	}

	public void disconnect() {
		Logger.info("Disconnecting from Server...", Level.LVL1);
		messageSocket.sendMessage(PREFIX_DISCONNECT);
		String answer = messageSocket.receiveMessage();
		connected = false;
		if (!answer.equals("/dc/")) {
			Logger.error("Invalid disconnect answer from server! " + answer, Level.LVL1);
			messageSocket.close();
			fileSocket.close();
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
