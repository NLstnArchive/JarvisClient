package com.jarvis.net.sockets;

public class MessageSocketHandler extends AbstractSocketHandler {

	private static final int port = 61243;

	public MessageSocketHandler() {
		super(port);
	}

	public void sendMessage(String message) {
		send(message.getBytes());
	}

	public String receiveMessage() {
		return new String(receive());
	}

}
