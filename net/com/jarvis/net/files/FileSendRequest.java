package com.jarvis.net.files;

import com.jarvis.data.files.JFile;
import com.jarvis.net.packets.FilePacket;
import com.jarvis.net.packets.Message;
import com.jarvis.net.packets.Message.MessageType;

public class FileSendRequest {

	private FilePacket	filePacket;
	private Message		requestPacket;

	public FileSendRequest(JFile file, String dest) {
		requestPacket = new Message(MessageType.FILE_INFO, "size/" + dest.replaceAll("/", ";"));
		filePacket = new FilePacket(file, dest);
	}

	public Message getRequest() {
		return requestPacket;
	}

	public FilePacket getFilePacket() {
		return filePacket;
	}

}
