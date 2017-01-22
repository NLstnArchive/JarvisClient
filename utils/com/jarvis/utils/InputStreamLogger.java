package com.jarvis.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jarvis.JarvisMain;
import com.jarvis.concurrency.JTaskBuilder;
import com.jarvis.utils.Logger.Level;

public class InputStreamLogger {

	private BufferedReader reader;

	public InputStreamLogger(InputStream stream) {
		reader = new BufferedReader(new InputStreamReader(stream));
	}

	public void start() { // TODO maybe we want to log a stream over a long time?
		JarvisMain.getUtilsModule().getThreadPool().submit(JTaskBuilder.newTask().executing(() -> {
			try {
				String line = "";
				while ((line = reader.readLine()) != null) {
					Logger.info(line, Level.LVL1);
				}
			}
			catch (IOException e) {
				Logger.error("Failed to log stream! " + e.getMessage(), Level.LVL1);
			}
		}));
	}

}
