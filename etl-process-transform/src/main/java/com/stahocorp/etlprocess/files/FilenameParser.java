package com.stahocorp.etlprocess.files;

import com.stahocorp.etlprocess.enums.FileType;
import com.stahocorp.etlprocess.items.FileItem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class FilenameParser {


    public static FileItem parseFilename(String fileName) {
        final String minusCharacter = "-";
        List<String> fileNameFragments = Arrays.asList(fileName.split(minusCharacter));
        if (fileNameFragments.size() != 4) {
            throw new RuntimeException("FileName is wrong.\nShould be page-itemId-sourceDate-fileType.html");
        }

        FileItem item = new FileItem();
        item.setFileName(fileName);

        item.setSupplierName(fileNameFragments.get(FileNameIndexes.SUPPLIER_NAME.getValue()));
        item.setItemId(Long.parseLong(fileNameFragments.get(FileNameIndexes.ITEM_ID.getValue())));
        item.setDateFetched(getDateFromString(fileNameFragments.get(FileNameIndexes.DATE_FETCHED.getValue())));
        item.setFileType(getFileTypeFromFragment(fileNameFragments));

        return item;
    }

    private static FileType getFileTypeFromFragment(List<String> fileNameFragments) {
        String fileTypeTmp = fileNameFragments
                .get(FileNameIndexes.FILE_TYPE.getValue())
                .split("\\.")[0];

        return FileType.getTypeForString(fileTypeTmp);
    }

    private static LocalDateTime getDateFromString(String dateAsString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(dateAsString, formatter).atStartOfDay();
    }


    private enum FileNameIndexes {
        SUPPLIER_NAME(0),
        ITEM_ID(1),
        DATE_FETCHED(2),
        FILE_TYPE(3);

        private final int value;

        FileNameIndexes(int x) {
            value = x;
        }

        public int getValue() {
            return value;
        }
    }

}
