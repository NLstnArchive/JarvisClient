package com.jarvis.services.hardware;

import java.lang.management.ManagementFactory;

import com.jarvis.services.Service;
import com.jarvis.utils.Logger;
import com.jarvis.utils.Logger.Level;

public class HeapSpaceInfoService extends Service {

	public HeapSpaceInfoService() {
		super("HeapSpaceInfo", true);
	}

	public void buildProgram() {
		try {
			serviceRunnable = new Runnable() {
				public void run() {
					int mb = 1024 * 1024;
					Runtime runtime = Runtime.getRuntime();
					StringBuilder builder = new StringBuilder();
					builder.append("\t").append("Heap info: ").append("\n");
					// IMPROVEMENT [HeapSpaceInfoService][Difficult] make logger configure tabs by himself
					builder.append("\t\t\t\t\t\t\t").append("Total System memory: ").append("\t").append(((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize() / mb).append("\n");
					builder.append("\t\t\t\t\t\t\t").append("Maximum Heap space: ").append("\t").append(runtime.maxMemory() / mb).append("\n");
					builder.append("\t\t\t\t\t\t\t").append("Allocated Heap space: ").append("\t").append(runtime.totalMemory() / mb).append("\n");
					builder.append("\t\t\t\t\t\t\t").append("Free Heap space: ").append("\t\t").append(runtime.freeMemory() / mb).append("\n");
					builder.append("\t\t\t\t\t\t\t").append("Used Heap space: ").append("\t\t").append((runtime.totalMemory() - runtime.freeMemory()) / mb).append("\n");
					builder.append("\t\t\t\t\t\t").append("Available Processors: ").append("\t").append(runtime.availableProcessors());
					result = builder.toString();
				}
			};
			Logger.info("Succesfully compiled Runnable of service " + getName(), Level.LVL3);
		}
		catch (Exception e) {
			Logger.error("Failed to build Runnable for service " + getName(), Level.LVL1);
			e.printStackTrace();
		}
	}

}
