package com.jarvis.commands;

import com.jarvis.JarvisModule;

public class CommandModule extends JarvisModule {

    private CommandBuilder commandBuilder;

	public CommandModule() {
		super("CommandModule");
	}

	public void preInit() {

	}

	public void init() {
		running = true;
		commandBuilder = new CommandBuilder();
		commandBuilder.init();
	}

	public void postInit() {

	}

	public void shutDown() {
		running = false;
	}

	public CommandBuilder getCommandBuilder() {
		return commandBuilder;
	}
}
