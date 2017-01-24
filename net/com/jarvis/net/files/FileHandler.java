package com.jarvis.net.files;

import com.jarvis.net.sockets.FileSocketHandler;
import com.jarvis.net.sockets.MessageSocketHandler;

public class FileHandler {

	private MessageSocketHandler	messageSocket;
	private FileSocketHandler		fileSocket;

	public FileHandler(MessageSocketHandler messageSocket, FileSocketHandler fileSocket) {
		this.messageSocket = messageSocket;
		this.fileSocket = fileSocket;
	}

	public void submitFileSendRequest(FileSendRequest request) {
		messageSocket.sendMessage(request.getRequest());
	}

}
