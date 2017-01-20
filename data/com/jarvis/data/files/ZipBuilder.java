package com.jarvis.data.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipBuilder {

	private ZipOutputStream	zip;
	private JFile			output;

	private ZipBuilder(JFile f) {
		this.output = f;
		f.create();
		zip = new ZipOutputStream(f.genOutputStream());
	}

	public ZipBuilder addFile(JFile f) {
		try {
			byte[] buffer = new byte[1024];
			FileInputStream input = f.genFileInputStream();
			zip.putNextEntry(new ZipEntry(f.getName()));

			int len;
			while ((len = input.read(buffer)) >= 0) {
				zip.write(buffer, 0, len);
			}
			input.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public ZipBuilder addFolder(JFolder folder) {
		for (JFolder sub : folder.listFolders())
			addFolder(sub);
		for (JFile f : folder.listFiles())
			addFile(f);
		return this;
	}

	public JFile create() {
		try {
			zip.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}

	public static ZipBuilder createZip(JFile output) {
		return new ZipBuilder(output);
	}

	public static void main(String[] args) {
		JFolder folder = new JFolder(new File("C:/Users/Niklas/Desktop/Test"));
		JFile output = new JFile(new File("C:/Users/Niklas/Desktop/output.zip"));
		ZipBuilder.createZip(output).addFolder(folder).create();
	}
}
