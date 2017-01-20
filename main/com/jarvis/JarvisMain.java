package com.jarvis;

import com.jarvis.commands.CommandModule;
import com.jarvis.concurrency.SuperThreadPool;
import com.jarvis.data.DataModule;
import com.jarvis.data.files.FileSystem;
import com.jarvis.input.InputModule;
import com.jarvis.net.NetModule;
import com.jarvis.services.ServiceModule;
import com.jarvis.utils.Logger;
import com.jarvis.utils.Logger.Level;

public class JarvisMain {

	public static final String version = "pre-alpha";

	private static SuperThreadPool commonThreadPool;

	private static InputModule inputModule;
	private static CommandModule commandModule;
	private static DataModule dataModule;
	private static ServiceModule serviceModule;
	private static NetModule netModule;

	private JarvisMain() {

	}

	// Initialize things that are needed for
	private static void preInit() {
		FileSystem.init();
		Logger.init();
		inputModule = new InputModule();
		inputModule.preInit();
		commandModule = new CommandModule();
		commandModule.preInit();
		serviceModule = new ServiceModule();
		serviceModule.preInit();
		dataModule = new DataModule();
		dataModule.preInit();
		netModule = new NetModule();
		netModule.preInit();
	}

	// initialize all modules
	private static void init() {
		Logger.info("Setting up CommonThreadPool", Level.LVL2);
		commonThreadPool = new SuperThreadPool();
		Logger.info("Finished setting up CommonThreadPool", Level.LVL3);
		Logger.info("Setting up InputModule", Level.LVL2);
		inputModule.init();
		Logger.info("Finished setting up InputModule", Level.LVL3);
		Logger.info("Setting up CommandModule", Level.LVL2);
		commandModule.init();
		Logger.info("Finished setting up CommandModule", Level.LVL3);
		Logger.info("Setting up ServiceModule", Level.LVL2);
		serviceModule.init();
		Logger.info("Finished setting up ServiceModule", Level.LVL3);
		Logger.info("Setting up DataModule", Level.LVL2);
		dataModule.init();
		Logger.info("Finished setting up DataModule", Level.LVL3);
		netModule.init();
	}

	private static void postInit() {
		inputModule.postInit();
		commandModule.postInit();
		serviceModule.postInit();
		dataModule.postInit();
		netModule.postInit();
	}

	public static InputModule getInputModule() {
		return inputModule;
	}

	public static SuperThreadPool getCommonThreadPool() {
		return commonThreadPool;
	}

	public static CommandModule getCommandModule() {
		return commandModule;
	}

	public static ServiceModule getServiceModule() {
		return serviceModule;
	}

	public static DataModule getDataModule() {
		return dataModule;
	}

	public static NetModule getNetModule() {
		return netModule;
	}

	public static void shutdown() {
		Logger.info("Shutting down Jarvis", Level.LVL1);
		inputModule.shutDown();
		commandModule.shutDown();
		serviceModule.shutDown();
		dataModule.shutDown();
		netModule.shutDown();
		commonThreadPool.joinAll();
		Logger.info("Finished shutting down Jarvis. Goodbye!", Level.LVL1);
		Logger.close();
		System.exit(0);
	}

	public static void main(String[] args) {
		JarvisMain.preInit();
		Logger.info("Starting Jarvis v" + version, Level.LVL1);
		JarvisMain.init();
		JarvisMain.postInit();
		Logger.info("Finished starting Jarvis.", Level.LVL1);
	}
}