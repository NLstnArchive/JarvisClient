package com.jarvis.commands.organizer;

import com.jarvis.JarvisMain;
import com.jarvis.commands.Command;
import com.jarvis.data.files.FileSystem;
import com.jarvis.data.files.JFolder;
import com.jarvis.utils.Logger;
import com.jarvis.utils.Logger.Level;

public class OrganizeCommand extends Command {

	// CHECK [OrganizeCommand] check for bugs
	// TODO create FormatMappingSets for different Folders to choose
	public OrganizeCommand() {
		super("organize");
	}

	public Runnable compile() {
		Runnable runnable = null;
		try {
			runnable = new Runnable() {
				public void run() {
					JFolder folder = FileSystem.getFolder((String) args[0]);
					JarvisMain.getDataModule().getOrganizer().organizeFolder(folder);
				}
			};
		}
		catch (Exception e) {
			Logger.error("Failed to compile Runnable for Command " + getName(), Level.LVL1);
		}
		return runnable;
	}

	public boolean checkArgs() {
		if (args.length != 1) {
			Logger.error("Command " + getName() + " requires exactly 1 argument!", Level.LVL1);
			return false;
		}
		if (!isString(args[0])) {
			Logger.error("Command " + getName() + " requires a String as argument!", Level.LVL1);
			return false;
		}
		if (FileSystem.getFolder((String) args[0]) == null) {
			Logger.error("Command " + getName() + " requires a folder as argument!", Level.LVL1);
			return false;
		}
		return true;
	}

	@Override
	public String[] getHelp() {
		// TODO Auto-generated method stub
		return null;
	}

}
