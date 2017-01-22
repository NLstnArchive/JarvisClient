package com.jarvis.commands;

import java.util.ArrayList;
import java.util.List;

import com.jarvis.commands.cmdCommands.CmdCommand;
import com.jarvis.commands.cmdCommands.OpenFileCommand;
import com.jarvis.commands.cmdCommands.RunExeCommand;
import com.jarvis.commands.data.DropboxUploadCommand;
import com.jarvis.commands.data.LoadMp3Command;
import com.jarvis.commands.data.MountFolderShortcutCommand;
import com.jarvis.commands.data.UnmountFolderShortcutCommand;
import com.jarvis.commands.net.ConnectCommand;
import com.jarvis.commands.net.DisconnectCommand;
import com.jarvis.commands.organizer.OrganizeCommand;
import com.jarvis.commands.organizer.formatMappings.AddFormatMappingCommand;
import com.jarvis.commands.organizer.formatMappings.ClearFormatMappings;
import com.jarvis.commands.organizer.formatMappings.ListFormatMappingsCommand;
import com.jarvis.commands.organizer.formatMappings.RemoveFormatMappingCommand;
import com.jarvis.commands.std.SetLoggerLevelCommand;
import com.jarvis.commands.std.ShutdownCommand;
import com.jarvis.utils.Logger;
import com.jarvis.utils.Logger.Level;

public class CommandBuilder {

	private List<Command>	commands	= new ArrayList<Command>();

	private List<String>	subNames	= new ArrayList<String>();

	public void init() {
		registerCommand(new RunExeCommand());
		registerCommand(new CmdCommand());
		registerCommand(new OpenFileCommand());
		registerCommand(new DropboxUploadCommand());
		registerCommand(new ShutdownCommand());
		registerCommand(new AddFormatMappingCommand());
		registerCommand(new ListFormatMappingsCommand());
		registerCommand(new RemoveFormatMappingCommand());
		registerCommand(new ClearFormatMappings());
		registerCommand(new LoadMp3Command());
		registerCommand(new OrganizeCommand());
		registerCommand(new MountFolderShortcutCommand());
		registerCommand(new UnmountFolderShortcutCommand());
		registerCommand(new SetLoggerLevelCommand());
		registerCommand(new ConnectCommand());
		registerCommand(new DisconnectCommand());
	}

	public void registerCommand(Command command) {
		boolean invalid = false;
		for (String subName : subNames) {
			if (command.containsName(subName))
				invalid = true;
		}
		if (invalid) {
			Logger.error("Failed to register Command " + command.getName() + ", NameConflictError!", Level.LVL1);
			return;
		}
		else {
			commands.add(command);
		}
	}

	public Runnable buildCommand(String name, Object... args) {
		Logger.info("Trying to build command...", Level.LVL2);
		Command cmd = null;
		for (Command command : commands) {
			if (command.containsName(name))
				cmd = command;
		}
		if (cmd != null) {
			cmd.setArgs(args);
			if (cmd.checkArgs())
				return cmd.compile();
			else {
				for (String message : cmd.getHelp())
					Logger.info(message, Level.LVL1);
				return null;
			}

		}
		else
			return null;
	}
}