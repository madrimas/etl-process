package com.stahocorp.etlprocess.services;

import com.stahocorp.etlprocess.config.EtlProperties;
import com.stahocorp.etlprocess.config.EtlStatistics;
import com.stahocorp.etlprocess.files.FileMover;
import com.stahocorp.etlprocess.transform.FileFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service which is responsible for watching input directory and running entire processing flow.
 */
@Service
public class WatcherTask {

    @Autowired
    private EtlProperties etlProperties;

    /**
     * This method is running periodically and triggers processing flow if it is enabled.
     */
    @Scheduled(fixedRate = 5000)
    public void readAndTransformFile() {
        if(!etlProperties.isTransformationEnabled()) return;

        readAndTransformOnce();
    }

    /**
     * This method is scanning input folder, triggers processing, saves results and moves processed files to done folder.
     */
    public void readAndTransformOnce() {
        try (Stream<Path> walk = Files.walk(etlProperties.getInputDirAsPath())) {

            List<Path> result = walk.filter(Files::isRegularFile).collect(Collectors.toList());

            result.forEach(f -> {
                try {
                    String contentAsJson = FileFlow.processFile(f);
                    EtlStatistics.filesProcessed.getAndIncrement();
                    String hourAndMinuteOfProcessing = String.valueOf(LocalDateTime.now().getHour()) + String.valueOf(LocalDateTime.now().getMinute()) + String.valueOf(LocalDateTime.now().getSecond());
                    FileFlow.createFileAndSaveString(contentAsJson, etlProperties.getOutputDirAsPath(), f.getFileName().toString(), hourAndMinuteOfProcessing);
                    FileMover.move(f, etlProperties.getDoneDirAsPath().resolve(f.getFileName() + "_" + hourAndMinuteOfProcessing + ".html"));
                } catch (IOException e) {
                    System.out.println("Failed to move file " + f.toString());
                    e.printStackTrace();
                }
            });

            result.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
