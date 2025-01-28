package org.example.factories;


import org.example.utils.ConfigFileReader;

public class FileReaderFactory {
	private static FileReaderFactory fileReaderManager = new FileReaderFactory();
	private static ConfigFileReader configFileReader;
	
	private FileReaderFactory() {
	}
 
	 public static FileReaderFactory getInstance( ) {
	      return fileReaderManager;
	 }
 
	 public ConfigFileReader getConfigReader(String propertyFilePath) {
		 return (configFileReader == null) ? new ConfigFileReader(propertyFilePath) : configFileReader;
	 }
}
