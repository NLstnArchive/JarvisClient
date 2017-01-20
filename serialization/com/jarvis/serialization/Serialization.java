package com.jarvis.serialization;

class Serialization {

	private Serialization() {

	}

	public static void writeBytes(byte[] data, Pointer pointer, byte value) {
		data[pointer.getAndIncrement()] = value;
	}

}
