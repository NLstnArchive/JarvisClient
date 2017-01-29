package com.jarvis.net.sockets;

import java.util.ArrayList;
import java.util.List;

import com.jarvis.JarvisMain;
import com.jarvis.concurrency.JTaskBuilder;
import com.jarvis.net.packets.Message;
import com.jarvis.net.packets.PacketQueue;
import com.jarvis.net.packets.PacketQueue.Queue;

public class MessageSocketHandler extends AbstractSocketHandler {

	private static final int	port	= 61243;

	private List<PacketQueue>	queues	= new ArrayList<PacketQueue>();

	public MessageSocketHandler() {
		super(port);
		queues.add(new PacketQueue(Queue.DEFAULT));
		queues.add(new PacketQueue(Queue.FILE_INFO));
		JTaskBuilder.newTask().executing(this::updateQueues);
	}

	private void updateQueues() { // TODO sort in messages from bytestream to queues directly
		while (JarvisMain.getNetModule().isRunning() && JarvisMain.getNetModule().getServerHandler().isConnected()) {
			Message received = Message.fromStream(receive());
			boolean matched = false;
			for (PacketQueue pQueue : queues) {
				if (received.getTypePrefix().startsWith(pQueue.getQueue().getPrefix())) {
					pQueue.getMessages().add(received);
					matched = true;
					break;
				}
			}
			if (!matched)
				queues.get(0).getMessages().add(received);
		}
	}

	public List<Message> linkQueue(Queue queue) { // TODO block queue if it is linked also in receiveMessage(Queue)
		if (queue == Queue.DEFAULT)
			return null;
		return getQueue(queue).getMessages();
	}

	public void sendMessage(Message message) {
		send(message.getBytes());
	}

	public Message receiveMessage(Queue queue) {
		while (getQueue(queue).getMessages().isEmpty())
			;
		return getQueue(queue).getMessages().remove(0);
	}

	public Message receiveMessage() {
		return receiveMessage(Queue.DEFAULT);
	}

	private PacketQueue getQueue(Queue queue) {
		for (PacketQueue pQueue : queues) {
			return pQueue;
		}
		return queues.get(0);
	}

}
