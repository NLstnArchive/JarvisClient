package com.jarvis.net.sockets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.jarvis.JarvisMain;
import com.jarvis.concurrency.JTask;
import com.jarvis.concurrency.JTaskBuilder;
import com.jarvis.utils.Logger;
import com.jarvis.utils.Logger.Level;

public abstract class AbstractSocketHandler {

	private Socket			socket;
	private OutputStream	out;
	private InputStream		in;

	private List<byte[]>	receivedMessages	= new ArrayList<byte[]>();

	public AbstractSocketHandler(int port) {
		try {
			socket = new Socket(JarvisMain.getNetModule().getServerHandler().getServerAddress(), port);
			in = socket.getInputStream();
			out = socket.getOutputStream();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JTask task = JTaskBuilder.newTask().executing(new Runnable() {
			public void run() {
				while (JarvisMain.getNetModule().isRunning() && JarvisMain.getNetModule().getServerHandler().isConnected()) {
					receivedMessages.add(receiveInternal());
				}
			}
		});
		JarvisMain.getNetModule().getThreadPool().submit(task);
	}

	protected void send(byte[] data) {
		if (!JarvisMain.getNetModule().getServerHandler().isConnected()) {
			Logger.error("You have to connect to the server before trying to send data!", Level.LVL1);
			return;
		}
		try {
			out.write(data);
			out.flush();
		}
		catch (IOException e) {
			Logger.error("Failed to send data to server! " + e.getMessage(), Level.LVL1);
		}
	}

	protected byte[] receive() {
		while (receivedMessages.size() == 0) {
			try {
				Thread.sleep(250);
			}
			catch (InterruptedException e) {
				Logger.error("Failed to interrupt Thread", Level.LVL2);
			}
		}
		return receivedMessages.remove(0);
	}

	private byte[] receiveInternal() {
		if (!JarvisMain.getNetModule().getServerHandler().isConnected()) {
			Logger.error("You have to connect to the server before trying to receive data!", Level.LVL1);
		}
		byte[] data = new byte[1024];
		int size = 0;
		while (size == 0) {
			try {
				size = in.read(data);
			}
			catch (IOException e) {
				Logger.error("Failed to read data from server! " + e.getMessage(), Level.LVL1);
				Logger.error("Disconnecting from server!", Level.LVL1);
				JarvisMain.getNetModule().getServerHandler().setConnected(false);
				return null;
			}
		}
		if (size == -1)
			return new byte[0];
		byte[] returnData = new byte[size];
		System.arraycopy(data, 0, returnData, 0, size);
		return returnData;
	}

	public void close() {
		try {
			socket.close();
		}
		catch (IOException e) {
			Logger.error("Failed to close socket!", Level.LVL3);
		}
	}
}
