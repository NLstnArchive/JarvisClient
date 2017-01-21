package com.jarvis.commands.std;

import com.jarvis.JarvisMain;
import com.jarvis.commands.Command;
import com.jarvis.utils.Logger;
import com.jarvis.utils.Logger.Level;

public class ShutdownCommand extends Command {

	public ShutdownCommand() {
		super("shutDown", "shutdown", "close");
	}

	public boolean checkArgs() {
		if (args.length != 0) {
			Logger.error("Command " + getName() + " requires exactly 0 arguments!", Level.LVL1);
			return false;
		}
		return true;
	}

	public Runnable compile() {
		Runnable runnable = null;
		try {
			runnable = new Runnable() {
				public void run() {
					// FUTURE [ShutdownCommand] add "r u sure" question
					JarvisMain.shutdown();
				}
			};
		}
		catch (Exception e) {
			Logger.error("Failed to compile Runnable for Command " + getName() + ": " + e.getMessage(), Level.LVL1);
			return null;
		}
		return runnable;
	}

	@Override
	public String[] getHelp() {
		// TODO Auto-generated method stub
		return null;
	}

}
