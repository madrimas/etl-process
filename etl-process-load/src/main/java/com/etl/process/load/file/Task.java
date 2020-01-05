package com.etl.process.load.file;

import com.etl.process.load.config.Config;
import com.etl.process.load.entity.Opinion;
import com.etl.process.load.entity.Product;
import com.etl.process.load.rest.OpinionController;
import com.etl.process.load.rest.ProductController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Task {

	@Autowired
	private Config config;

	@Autowired
	private Gson gson;

	@Autowired
	private ProductController productController;

	@Autowired
	private OpinionController opinionController;

	private Stats stats;

//	@Scheduled(fixedDelay = 10000)

	/**
	 *
	 * store records
	 */
	public void store() {
		stats = new Stats();
		List<Product> addedProducts = new ArrayList<>();
		List<Product> modifiedProducts = new ArrayList<>();
		List<Opinion> addedOpinions = new ArrayList<>();
		List<Opinion> modifiedOpinions = new ArrayList<>();

		try (Stream<Path> walk = Files.walk(config.getInputFilesDirectoryPath())) {
			List<Path> result = walk.filter(Files::isRegularFile).collect(Collectors.toList());

			result.forEach(file -> processFile(addedProducts, modifiedProducts, addedOpinions, modifiedOpinions, file));
		} catch (IOException e) {
			e.printStackTrace();
		}

		stats.update(addedProducts.size(), modifiedProducts.size(), addedOpinions.size(), modifiedOpinions.size(), true);
	}

	/**
	 *
	 * @param addedProducts list
	 * @param modifiedProducts list
	 * @param addedOpinions list
	 * @param modifiedOpinions list
	 * @param f file to process
	 */
	private void processFile(List<Product> addedProducts, List<Product> modifiedProducts, List<Opinion> addedOpinions, List<Opinion> modifiedOpinions, Path f) {
		try {
			storeRecord(addedProducts, modifiedProducts, addedOpinions, modifiedOpinions, f);
			stats.update(addedProducts.size(), modifiedProducts.size(), addedOpinions.size(), modifiedOpinions.size(), false);
			moveFile(f, config.getStoredFilesDirectoryPath().resolve(f.getFileName()));
		} catch (IOException ioe) {
			System.out.println("Failed to move file " + f.toString());
			ioe.printStackTrace();
		} catch (Exception e) {
			System.out.println("Failed to store file " + f.toString());
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param addedProducts list
	 * @param modifiedProducts list
	 * @param addedOpinions list
	 * @param modifiedOpinions list
	 * @param f file to process
	 * @throws Exception when input dir doesn't exits
	 */
	private void storeRecord(List<Product> addedProducts, List<Product> modifiedProducts, List<Opinion> addedOpinions, List<Opinion> modifiedOpinions, Path f) throws Exception {
		FileReader fr = new FileReader(f.toString());
		if (f.getFileName().toString().contains("info")) {
			storeProduct(addedProducts, modifiedProducts, fr);
		} else if((f.getFileName().toString().contains("opinions"))) {
			storeOpinion(addedOpinions, modifiedOpinions, fr);
		}
		fr.close();
	}

	/**
	 *
	 * @param addedProducts list
	 * @param modifiedProducts list
	 * @param fr file reader
	 * @throws Exception when product cannot be added/modified
	 */
	private void storeProduct(List<Product> addedProducts, List<Product> modifiedProducts, FileReader fr) throws Exception {
		Product product = gson.fromJson(fr, Product.class);
		if (productController.get(product.getProductId()) == null) {
			addedProducts.add(productController.add(product));
		} else {
			modifiedProducts.add(productController.modify(product));
		}
	}

	/**
	 *
	 * @param addedOpinions list
	 * @param modifiedOpinions list
	 * @param fr file reader
	 * @throws Exception when opinion cannot be added/modified
	 */
	private void storeOpinion(List<Opinion> addedOpinions, List<Opinion> modifiedOpinions, FileReader fr) throws Exception {
		List<Opinion> opinions = gson.fromJson(fr, new TypeToken<List<Opinion>>() {}.getType());
		for (Opinion opinion : opinions) {
			if (opinionController.get(opinion.getOpinionId()) == null) {
				addedOpinions.add(opinionController.add(opinion));
			} else {
				modifiedOpinions.add(opinionController.modify(opinion));
			}
		}
	}

	/**
	 *
	 * @param input dir path
	 * @param output dir path
	 * @throws IOException when input dir doesn't exist
	 */
	private void moveFile(Path input, Path output) throws IOException {
		if (!Files.exists(input.getParent())) {
			throw new IOException("Input directory doesn't exist");
		}

		if (!Files.exists(output.getParent())) {
			Files.createDirectories(output.getParent());
		}

		Files.move(input, output);
	}

	public Stats getStats() {
		return stats;
	}
}
