package com.etl.process.load.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@PropertySource("classpath:application.properties")
public class Config {

	@Value("${files.input}")
	private String inputFilesDirectory;

	@Value("${files.done}")
	private String storedFilesDirectory;

	/**
	 *
	 * @return input files directory path
	 */
	public Path getInputFilesDirectoryPath(){
		return Paths.get(".", inputFilesDirectory);
	}

	/**
	 *
	 * @return stored files directory path
	 */
	public Path getStoredFilesDirectoryPath(){
		return Paths.get(".", storedFilesDirectory);
	}
}
