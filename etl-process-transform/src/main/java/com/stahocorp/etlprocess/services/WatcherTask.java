package com.stahocorp.etlprocess.services;

import com.stahocorp.etlprocess.config.EtlProperties;
import com.stahocorp.etlprocess.config.EtlStatistics;
import com.stahocorp.etlprocess.files.FileMover;
import com.stahocorp.etlprocess.transform.FileFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
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

        readAndTransformAsync();
    }

    /**
     * Async execution of read and transform method. Used mostly in gui.
     */
    @Async
    public void readAndTransformAsync() {
        readAndTransform();
    }

    /**
     * This method is scanning input folder, triggers processing, saves results and moves processed files to done folder.
     */
    public void readAndTransform() {
        try (Stream<Path> walk = Files.walk(etlProperties.getInputDirAsPath())) {
            EtlStatistics.resetStats();

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
        } finally {
            EtlStatistics.getFinished().set(true);
        }
    }

}
