package com.etl.process.load.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Load process configuration class
 */
@Configuration
@PropertySource("classpath:application.properties")
public class Config {

	@Value("${files.input}")
	private String inputFilesDirectory;

	@Value("${files.done}")
	private String storedFilesDirectory;

	/**
	 * method to get input directory for load process
	 * @return input files directory path
	 */
	public Path getInputFilesDirectoryPath(){
		return Paths.get(inputFilesDirectory);
	}

	/**
	 * method to get output directory for load process
	 * @return stored files directory path
	 */
	public Path getStoredFilesDirectoryPath(){
		return Paths.get(storedFilesDirectory);
	}
}
