package ru.itis.nasibullin.cloudphoto.commands;

import lombok.SneakyThrows;
import ru.itis.nasibullin.cloudphoto.entities.Arguments;
import ru.itis.nasibullin.cloudphoto.entities.Config;
import ru.itis.nasibullin.cloudphoto.exceptions.NoImagesToUploadException;
import ru.itis.nasibullin.cloudphoto.repositories.YandexCloudRepo;
import ru.itis.nasibullin.cloudphoto.results.Result;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UploadCommand implements Command<Void> {
    @Override
    public Result<Void> execute(Config config, Arguments arguments) {
        YandexCloudRepo repo = new YandexCloudRepo(config);
        List<File> uploadFiles = getFiles(arguments.getPath());
        if (uploadFiles.size() == 0) {
            throw new NoImagesToUploadException("No files to upload in folder " + arguments.getPath());
        }
        for (File file : uploadFiles) {
            try {
                repo.create(arguments.getAlbum() + "/" + file.getName(), file, false);
            } catch (Exception e) {
                System.err.println("Exception while uploading photo: " + e.getMessage());
            }

        }
        return null;
    }

    @SneakyThrows
    private List<File> getFiles(String path) {
        if (!Files.exists(Paths.get(path))) {
            throw new FileNotFoundException("Directory is not exists: " + path);
        }
        if (!Files.isDirectory(Paths.get(path))) {
            throw new IllegalArgumentException("This is not directory: " + path);
        }
        List<File> files = new ArrayList<>();
        File directory = new File(path);
        for (File file : directory.listFiles()) {
            if (isImageExtension(file)) {
                files.add(file);
            }
        }
        return files;
    }

    private boolean isImageExtension(File file) {
        String[] extensions = new String[]{"jpg", "jpeg"};
        String[] nameParts = file.getName().split("\\.");
        return nameParts.length > 1 && Arrays.asList(extensions).contains(nameParts[nameParts.length - 1]);
    }

}
