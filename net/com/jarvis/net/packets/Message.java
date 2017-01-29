package com.jarvis.net.packets;

import com.jarvis.net.packets.NetPacket.PacketType;

public class Message extends NetPacket {

	public enum MessageType {
		CONNECTION("c/"), DISCONNECTION("d/"), FILE_INFO("fi/");

		private String prefix;

		MessageType(String prefix) {
			this.prefix = prefix;
		}

		public String getPrefix() {
			return prefix;
		}

		public static MessageType fromStream(byte[] data) {
			String messageType = new String(data).substring(3, 5);
			for (MessageType type : MessageType.values()) {
				if (type.getPrefix().equals(messageType))
					return type;
			}
			return null;
		}
	}

	private MessageType type;

	public Message(MessageType type, String content) {
		this();
		this.type = type;
		this.content = content.getBytes();
		prefix = prefix + type.getPrefix();
	}

	private Message() {
		super(PacketType.Message);
	}

	public String getTypePrefix() {
		return type.getPrefix();
	}

	public String getContent() {
		return new String(content);
	}

	public int getSize() {
		return prefix.getBytes().length + content.length;
	}

	public byte[] getBytes() {
		byte[] data = new byte[getSize()];
		System.arraycopy(prefix, 0, data, 0, prefix.length());
		System.arraycopy(content, 0, data, prefix.length(), content.length);
		return data;
	}

	public static Message fromStream(byte[] data) {
		Message result = new Message();
		result.prefix = result.prefix + MessageType.fromStream(data).getPrefix();
		result.content = new byte[data.length - 5];
		System.arraycopy(data, 5, result.content, 0, result.content.length);
		return result;
	}
}
