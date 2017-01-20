package com.jarvis.net;

import com.jarvis.JarvisModule;

public class NetModule extends JarvisModule {

	// STATISTIC [NetModule] Count send/received bytes, packages
	private ServerHandler serverHandler;

	public NetModule() {
		super("NetModule");
	}

	public void preInit() {

	}

	public void init() {
		running = true;
		serverHandler = new ServerHandler();
	}

	public void postInit() {

	}

	public void shutDown() {
		serverHandler.disconnect();
	}

	public ServerHandler getServerHandler() {
		return serverHandler;
	}

}
