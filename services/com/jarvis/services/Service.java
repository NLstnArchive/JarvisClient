package com.jarvis.services;

import com.jarvis.utils.Logger;
import com.jarvis.utils.Logger.Level;

public abstract class Service {

	private String name;
	protected Object result;
	protected Runnable serviceRunnable;

	protected boolean needsUpdate;
	protected boolean needsRebuild;

	//FIX [Service] Services at their current state are pretty useless, as they should be used to provide data over a long periode of time, not just for on log. Refactor Service class to be able to contain a set of data(HashMap<Name, Object>?) and make Services available to other modules.
	public Service(String name, boolean needsUpdate) {
		this.name = name;
		this.needsUpdate = needsUpdate;
	}

	public void init() {
		buildProgram();
	}

	public abstract void buildProgram();

	public Object getResult() {
		if (needsUpdate) {
			if (serviceRunnable != null)
				serviceRunnable.run();
			else {
				Logger.error("No precompiled Runnable found in service " + getName() + "!", Level.LVL1);
				return null;
			}
		}
		if (result == null)
			buildProgram();
		return result;
	}

	public String getName() {
		return name;
	}

}
