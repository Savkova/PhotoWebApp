package com.savkova.app;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Util {

    public static final String PHOTOS_DIRECTORY = "./photoapp/photos/";

    static {
        try {
            if (!Files.exists(Paths.get(Util.PHOTOS_DIRECTORY)))
                Files.createDirectory(Paths.get(PHOTOS_DIRECTORY));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File generateZipFile(long[] itemsId, Map<Long, String> photos) {
        String archiveName = "download.zip";
        File archive = new File(PHOTOS_DIRECTORY + archiveName);
        try {
            FileOutputStream fos = new FileOutputStream(archive);
            ZipOutputStream zout = new ZipOutputStream(fos);
            for (long id : itemsId) {
                File file = FileUtils.getFile(PHOTOS_DIRECTORY, photos.get(id));
                FileInputStream fis = new FileInputStream(file);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zout.putNextEntry(zipEntry);

                byte[] data = FileUtils.readFileToByteArray(file);
                zout.write(data);

                fis.close();
            }
            zout.close();
            fos.close();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return archive;
    }

    public static void writeToFile(String fileName, byte[] data) {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(PHOTOS_DIRECTORY + fileName))) {
            out.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] readFromFile(String fileName) {
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(PHOTOS_DIRECTORY + fileName))) {
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String deleteFile(long id, Map<Long, String> photos){
        File file = new File(Util.PHOTOS_DIRECTORY + photos.get(id));

        String result = null;
        if (file.delete())
            result = photos.remove(id);

        return result;
    }

}