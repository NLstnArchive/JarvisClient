package com.jarvis.commands.info;

import com.jarvis.JarvisMain;
import com.jarvis.commands.Command;
import com.jarvis.utils.Logger;
import com.jarvis.utils.Logger.Level;

public class InfoCommand extends Command {

	public InfoCommand() {
		super("info");
	}

	// FUTURE [InfoCommand] implement more info commands. rename this one to HeapSpaceInfo.
	public Runnable compile() {
		Runnable runnable = null;
		try {
			runnable = new Runnable() {
				public void run() {
					Logger.info((String) JarvisMain.getServiceModule().getServiceManager().getResultOfService("HeapSpaceInfo"), Level.LVL1);
				}
			};
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return runnable;
	}

	public boolean checkArgs() {
		if (args.length != 0) {
			Logger.error("Command " + getName() + " requires no arguments!", Level.LVL1);
			return false;
		}
		return true;
	}
}
