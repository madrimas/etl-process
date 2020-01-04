package com.stahocorp.etlprocess.transform;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stahocorp.etlprocess.files.FilenameParser;
import com.stahocorp.etlprocess.items.FileItem;
import com.stahocorp.etlprocess.items.InfoItem;
import com.stahocorp.etlprocess.items.OpinionItem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1>Defines flow of a file through parsers</h1>
 * This class takes filePath of file which should be parsed into json object.
 * Several things happen, such as parsing file name and processing given html input.
 */
public class FileFlow {

    private final static String FILE_EXTENSION = ".json";

    /**
     *
     * @param filePath path to processed file
     * @return file as json object
     * @throws IOException
     */
    public static String processFile(Path filePath) throws IOException {
        String data = new String(Files.readAllBytes(filePath));
        FileItem fileItem = FilenameParser.parseFilename(filePath.getFileName().toString());

        String jsonToSave;
        switch (fileItem.getFileType()) {
            case INFO:
                InfoFileParser ifp = new InfoFileParser(data);
                InfoItem ii = ifp.parseHtmlToItem();
                ii.setFilename(fileItem.getFileName());
                ii.setFetchDate(fileItem.getDateFetched());
                jsonToSave = prepareInfoJson(ii);
                break;
            case OPINIONS:
                OpinionFileParser ofp = new OpinionFileParser(data);
                List<OpinionItem> opinions = ofp.parseHtmlToItems();
                opinions = opinions
                        .stream()
                        .map(o -> {
                            OpinionItem temp = new OpinionItem(o);
                            temp.setItemInfoId(fileItem.getItemId());
                            temp.setTimeStamp(fileItem.getDateFetched());
                            return temp;
                        })
                        .collect(Collectors.toList());
                jsonToSave = prepareOpinionsJson(opinions);
                break;
            default:
                jsonToSave = "";
        }
        return jsonToSave;
    }

    /**
     * This method is used to serialize list of opinions items to json.
     * @param opinions opinion about product
     * @return opinions as json object String
     */
    private static String prepareOpinionsJson(List<OpinionItem> opinions) {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        Gson gson = builder.create();

        return gson.toJson(opinions);
    }

    /**
     * This method is used to serialize product info items to json object.
     * @param infoItem product information from reseller site
     * @return product info as a Json object string
     */
    private static String prepareInfoJson(InfoItem infoItem) {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        Gson gson = builder.create();

        return gson.toJson(infoItem);
    }

    /**
     * This method is used to save output json object string into a new file. It changes output file name due to replication in case of reprocessing.
     * @param content json object(s) to save
     * @param outputDirPath path of output folder (defined in application.properties)
     * @param filename name of original file
     * @param timeStamp combination of hour, minutes and seconds of processing.
     * @throws IOException
     */
    public static void createFileAndSaveString(String content, Path outputDirPath, String filename, String timeStamp) throws IOException {
        String newFilename = filename + "_out";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputDirPath.toString() + "/" + newFilename + "_" + timeStamp + FILE_EXTENSION))) {
            writer.write(content);
        }

    }
}
