package com.jarvis.net.files;

import java.util.List;

import com.jarvis.JarvisMain;
import com.jarvis.concurrency.JTaskBuilder;
import com.jarvis.net.packets.Message;
import com.jarvis.net.packets.PacketQueue.Queue;
import com.jarvis.net.sockets.FileSocketHandler;
import com.jarvis.net.sockets.MessageSocketHandler;

public class FileHandler {

	private MessageSocketHandler	messageSocket;
	private FileSocketHandler		fileSocket;

	private List<Message>			fileInfoMessages;
	private List<FileSendRequest>	pendingRequests;

	public FileHandler(MessageSocketHandler messageSocket, FileSocketHandler fileSocket) {
		this.messageSocket = messageSocket;
		this.fileSocket = fileSocket;
		fileInfoMessages = messageSocket.linkQueue(Queue.FILE_INFO);
		JarvisMain.getNetModule().getThreadPool().submit(JTaskBuilder.newTask().executing(this::processSendRequests));
	}

	public void submitFileSendRequest(FileSendRequest request) {
		pendingRequests.add(request);
	}

	private void processSendRequests() {
		while (JarvisMain.getNetModule().isRunning() && JarvisMain.getNetModule().getServerHandler().isConnected()) {
			while (pendingRequests.isEmpty())
				;
			FileSendRequest currentRequest = pendingRequests.remove(0);
			messageSocket.sendMessage(currentRequest.getRequest());
			while (fileInfoMessages.isEmpty()) // TODO timeout
				;
			if (fileInfoMessages.get(0).equals(currentRequest.getRequest())) {
				fileInfoMessages.remove(0);
			}

		}
	}

}
