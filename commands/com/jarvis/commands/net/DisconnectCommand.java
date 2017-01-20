package com.jarvis.commands.net;

import com.jarvis.JarvisMain;
import com.jarvis.commands.Command;
import com.jarvis.utils.Logger;
import com.jarvis.utils.Logger.Level;

public class DisconnectCommand extends Command {

	public DisconnectCommand() {
		super("disconnect", "dc");
	}

	public boolean checkArgs() {
		if (args.length != 0) {
			Logger.error("Command " + getName() + " requires no arguments!", Level.LVL1);
			return false;
		}
		return true;
	}

	public Runnable compile() {
		Runnable r = new Runnable() {
			public void run() {
				JarvisMain.getNetModule().getServerHandler().disconnect();
			}
		};
		return r;
	}

}
