package com.jarvis.commands.cmdCommands;

import java.io.IOException;

import com.jarvis.commands.Command;
import com.jarvis.utils.Logger;
import com.jarvis.utils.Logger.Level;
import com.jarvis.utils.StreamGobbler;

public class CmdCommand extends Command {

	public CmdCommand(String name) {
		super(name);
	}

	public CmdCommand() {
		super("cmd");
	}

	public Runnable compile() {
		Runnable runnable = null;
		try {
			runnable = new Runnable() {
				public void run() {
					Runtime rt = Runtime.getRuntime();
					Process p = null;
					try {
						p = rt.exec((String[]) args);
					}
					catch (IOException e) {
						Logger.error("Failed to create Process.", Level.LVL1);
						e.printStackTrace();
						return;
					}
					StreamGobbler infoGobbler = new StreamGobbler(p.getInputStream());
					StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream());

					infoGobbler.start();
					errorGobbler.start();

					Logger.info("Successfully issued command!", Level.LVL1);
				}
			};
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return runnable;
	}

	public boolean checkArgs() {
		if (args.length == 0) {
			Logger.error("The command " + getName() + " requires at least 1 argument!", Level.LVL1);
			return false;
		}
		if (isStringArray(args)) {
			return true;
		}
		else {
			Logger.error("Command " + getName() + " requires a String array as argument", Level.LVL1);
			return false;
		}
	}
}
