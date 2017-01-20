package com.jarvis.serialization;

import java.io.File;

public class Database {

	private String name;

	private int size;

	public Database(String name) {
		setName(name);
	}

	private void setName(String name) {
		if (this.name != null)
			size -= this.name.getBytes().length;
		this.name = name;
		size += name.getBytes().length;
	}
	
	public int getSize() {
		return size;
	}

	public static Database readFromFile(File f) {
		return null;
	}

}
