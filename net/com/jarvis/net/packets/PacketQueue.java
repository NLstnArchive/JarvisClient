package com.jarvis.net.packets;

import java.util.ArrayList;
import java.util.List;

public class PacketQueue {

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

	private Queue			queue;
	private List<Message>	messages;

	public PacketQueue(Queue queue) {
		this.queue = queue;
		messages = new ArrayList<Message>();
	}

	public Queue getQueue() {
		return queue;
	}

	public List<Message> getMessages() {
		return messages;
	}

}
