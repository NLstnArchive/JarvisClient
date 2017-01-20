package com.jarvis.commands.net;

import com.jarvis.JarvisMain;
import com.jarvis.commands.Command;
import com.jarvis.utils.Logger;
import com.jarvis.utils.Logger.Level;

public class ConnectCommand extends Command {

	public ConnectCommand() {
		super("connect");
	}

	public boolean checkArgs() {
		if (args.length != 0) {
			Logger.error("Command " + getName() + " doesn't support arguments yet!", Level.LVL1); // FUTURE accept ip of server as argument
			return false;
		}
		return true;
	}

	public Runnable compile() {
		Runnable r = new Runnable() {
			public void run() {
				JarvisMain.getNetModule().getServerHandler().connect();
			}
		};
		return r;
	}

}
