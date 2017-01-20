package com.jarvis.services;

import com.jarvis.JarvisModule;

public class ServiceModule extends JarvisModule {

	private ServiceManager serviceManager;

	public ServiceModule() {
		super("ServiceModule");
	}

	public void preInit() {

	}

	public void init() {
		running = true;
		serviceManager = new ServiceManager();
		serviceManager.init();
	}

	public void postInit() {

	}

	public void shutDown() {

	}

	public ServiceManager getServiceManager() {
		return serviceManager;
	}

}
