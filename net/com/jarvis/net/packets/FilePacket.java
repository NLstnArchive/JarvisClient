package com.jarvis.net.packets;

import com.jarvis.data.files.JFile;

public class FilePacket extends NetPacket {

	public FilePacket(JFile f, String destination) {
		super(PacketType.FILE);
		prefix += destination.replace("/", ";");

	}

}
