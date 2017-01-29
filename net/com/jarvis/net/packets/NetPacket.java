package com.jarvis.net.packets;

public abstract class NetPacket {

	protected enum PacketType {

		Message("/m/"), FILE("/f/");

		private String prefix;

		PacketType(String prefix) {
			this.prefix = prefix;
		}

		public String getPrefix() {
			return prefix;
		}

	}

	protected String	prefix;
	protected byte[]	content;

	public NetPacket(PacketType type) {
		prefix = type.getPrefix();
	}

	public byte[] toStream() {
		byte[] pre = prefix.getBytes();
		byte[] stream = new byte[pre.length + content.length];
		System.arraycopy(pre, 0, stream, 0, pre.length);
		System.arraycopy(content, 0, stream, pre.length, stream.length);
		return stream;
	}

}
