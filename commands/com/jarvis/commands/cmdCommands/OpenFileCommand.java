package com.jarvis.commands.cmdCommands;

import com.jarvis.commands.Command;
import com.jarvis.data.files.FileSystem;
import com.jarvis.data.files.JFile;
import com.jarvis.utils.Logger;
import com.jarvis.utils.Logger.Level;

public class OpenFileCommand extends Command {

	public OpenFileCommand() {
		super("openFile");
	}

	public Runnable compile() {
		Runnable runnable = null;

		try {
			runnable = new Runnable() {
				public void run() {
					JFile file = FileSystem.getFile((String) args[0]);
					try {
						new ProcessBuilder().command("cmd.exe", "/c", file.getPath()).start();
					}
					catch (Exception e) {
						Logger.error("Failed to create Process for Command " + getName(), Level.LVL1);
						e.printStackTrace();
						return;
					}
					Logger.info("Successfully opened file " + file, Level.LVL2);
				}
			};
		}
		catch (Exception e) {
			Logger.error("Failed to create Command " + getName(), Level.LVL1);
		}

		return runnable;
	}

	public boolean checkArgs() {
		if (args.length == 0) {
			Logger.error("Command " + getName() + " requires at least 1 argument!", Level.LVL1);
			return false;
		}
		try {
			String file = (String) args[0];
			if (FileSystem.getFile(file).exists())
				return true;
			else {
				Logger.error("Command " + getName() + " requires an existing file as argument!", Level.LVL1);
				return false;
			}
		}
		catch (Exception e) {
			Logger.error("Command " + getName() + " requires a String as argument!", Level.LVL1);
			return false;
		}
	}

}
