package com.jarvis.services;

import java.util.ArrayList;
import java.util.List;

import com.jarvis.services.hardware.HeapSpaceInfoService;
import com.jarvis.utils.Logger;
import com.jarvis.utils.Logger.Level;

public class ServiceManager {

	private List<Service> services = new ArrayList<Service>();

	public void init() {
		registerService(new HeapSpaceInfoService());
	}

	public void registerService(Service service) {
		services.add(service);
		service.init();
	}

	public Object getResultOfService(String serviceName) {
		for (Service service : services) {
			if (service.getName().equals(serviceName))
				return service.getResult();
		}
		Logger.error("Couldn't find service " + serviceName, Level.LVL3);
		return null;
	}
}