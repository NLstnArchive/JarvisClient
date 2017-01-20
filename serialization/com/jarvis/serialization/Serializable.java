package com.jarvis.serialization;

public interface Serializable {

	public byte[] serialize();

	public void createFromBytes(byte[] data, int offset);

}
