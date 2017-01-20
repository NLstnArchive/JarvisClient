package com.jarvis.net.sockets;

import com.jarvis.data.files.JFile;
import com.jarvis.utils.Logger;
import com.jarvis.utils.Logger.Level;

public class FileSocketHandler extends AbstractSocketHandler {

	private static final int port = 49506;

	public FileSocketHandler() {
		super(port);
	}

	public boolean connect(int id) {
		send(("/c/" + id).getBytes());
		String connectionMessage = new String(receive());
		if (!connectionMessage.equals("/c/")) {
			Logger.error("Invalid connection message from server! " + connectionMessage, Level.LVL1);
			return false;
		}
		Logger.info("Successfully connected fileSocket to server!", Level.LVL1);
		return true;
	}

	public void sendFile(JFile f) {
		Logger.info("Sending file " + f.getPath() + " to server!", Level.LVL1);
		send(f.toStream());
	}

	public JFile receiveFile() {
		return JFile.fromStream(receive());
	}

}
