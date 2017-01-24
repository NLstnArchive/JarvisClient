package com.jarvis.net.sockets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jarvis.JarvisMain;
import com.jarvis.concurrency.JTaskBuilder;

public class MessageSocketHandler extends AbstractSocketHandler {

	private static final int port = 61243;

	public enum Queue {

		FILE_INFO("/fi/"), DEFAULT("*");

		private String prefix;

		Queue(String prefix) {
			this.prefix = prefix;
		}

		public String getPrefix() {
			return prefix;
		}

	}

	private Map<Queue, List<String>> queues = new HashMap<Queue, List<String>>();

	public MessageSocketHandler() {
		super(port);
		JTaskBuilder.newTask().executing(this::updateQueues);
	}

	private void updateQueues() {
		while (JarvisMain.getNetModule().isRunning()) {
			String received = new String(receive());
			boolean matched = false;
			for (Queue queue : Queue.values()) {

				if (received.startsWith(queue.getPrefix())) {
					List<String> messages = queues.get(queue);
					if (messages == null) {
						messages = new ArrayList<String>();
						queues.put(queue, messages);
					}
					messages.add(received);
					matched = true;
					break;
				}
			}
			if (!matched) {
				List<String> messages = queues.get(Queue.DEFAULT);
				if (messages == null) {
					messages = new ArrayList<String>();
					queues.put(Queue.DEFAULT, messages);
				}
				messages.add(received);
			}
		}
	}

	public void sendMessage(String message) {
		send(message.getBytes());
	}

	public String receiveMessage() {
		return new String(receive());
	}

}
