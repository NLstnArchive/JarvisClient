package com.jarvis.commands.data;

import com.jarvis.commands.Command;
import com.jarvis.data.fileFormat.Mp3AudioFile;
import com.jarvis.data.files.FileSystem;
import com.jarvis.data.files.JFile;
import com.jarvis.utils.Logger;
import com.jarvis.utils.Logger.Level;

public class LoadMp3Command extends Command {

	public LoadMp3Command() {
		super("loadMp3");
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
		JFile f = FileSystem.getFile((String) args[0]);
		if (!f.exists()) {
			Logger.error("Command " + getName() + " requires an existing file as argument!", Level.LVL1);
			return false;
		}
		if (!f.getFileFormat().equals(".mp3")) {
			Logger.error("Command " + getName() + " requires an mp3 file as argument!", Level.LVL1);
			return false;
		}
		return true;
	}

	public Runnable compile() {
		Runnable runnable = null;
		try {
			runnable = new Runnable() {
				public void run() {
					new Mp3AudioFile((String) args[0]);
				}
			};
		}
		catch (Exception e) {
			Logger.error("Failed to compile Runnable!", Level.LVL1);
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
