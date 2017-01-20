package com.jarvis.data;

import java.util.ArrayList;
import java.util.List;

import com.jarvis.Cleanable;
import com.jarvis.JarvisModule;
import com.jarvis.data.files.Organizer;

public class DataModule extends JarvisModule {

	private DataPool dataPool;
	private DropboxHandler dropbox;
	private Organizer organizer;

	private static List<Cleanable> cleanables = new ArrayList<Cleanable>();

	public DataModule() {
		super("DataModule");
	}

	public void preInit() {

	}

	public void init() {
		running = true;
		DataPool.init();
		dropbox = new DropboxHandler();
		organizer = new Organizer();
	}

	public void postInit() {
	}

	public void registerCleanable(Cleanable cleanable) {
		cleanables.add(cleanable);
	}

	public void cleanUp() {
		for (Cleanable cleanable : cleanables) {
			cleanable.cleanUp();
		}
	}

	public void shutDown() {
		DataPool.save();
	}

	public DataPool getDataPool() {
		return dataPool;
	}

	public Organizer getOrganizer() {
		return organizer;
	}

	public DropboxHandler getDropboxHandler() {
		return dropbox;
	}

}
