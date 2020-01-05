package data.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main runner class
 */
@SpringBootApplication
public class EtlProcessExtractApplication {

    /**
     * Runner class to start application
     * @param args command line arguments for application, not required
     */
    public static void main(String[] args) {
        SpringApplication.run(EtlProcessExtractApplication.class, args);
    }

}
