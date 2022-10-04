package ru.itis.nasibullin.cloudphoto.commands;

import lombok.SneakyThrows;
import ru.itis.nasibullin.cloudphoto.entities.Arguments;
import ru.itis.nasibullin.cloudphoto.entities.Config;
import ru.itis.nasibullin.cloudphoto.repositories.YandexCloudRepo;
import ru.itis.nasibullin.cloudphoto.results.Result;
import ru.itis.nasibullin.cloudphoto.results.StringResult;
import ru.itis.nasibullin.cloudphoto.utils.MksiteUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class MksiteCommand implements Command<String> {
    @SneakyThrows
    @Override
    public Result<String> execute(Config config, Arguments args) {
        YandexCloudRepo repo = new YandexCloudRepo(config);
        if (!Files.isDirectory(Paths.get(args.getPath()))) {
            String[] pathArray = args.getPath().split(File.separator);
            pathArray = Arrays.copyOf(pathArray, pathArray.length-1);
            args.setPath(String.join("/", pathArray));
        }
        repo.setBucketPublic();
        List<String> albums = repo.getAllAlbums();
        StringBuilder albumBuilder = new StringBuilder();
        int i = 1;
        for (String album: albums) {
            args.setAlbum(album);
            List<String> urls = repo.getDownloadImagesURL(args);
            StringBuilder builder = new StringBuilder();
            for (String url: urls) {
                builder.append("<img src=\"").append(url).append(" \"data-title=\"").append(url.split("/")[4].split("\\?")[0]).append("\">\n");
            }
            String filePath = args.getPath() + File.separator + "album" + i + ".html";
            try {
                Files.createFile(Paths.get(filePath));
            } catch (FileAlreadyExistsException e) {
                //ignore
            }
            File albumFile = new File(filePath);
            try (Writer writer = new FileWriter(albumFile)) {
                writer.write(MksiteUtils.getAlbumPage(builder.toString()));
                writer.flush();
            }
            repo.create("album" + i + ".html", albumFile);
            Files.delete(Paths.get(filePath));
            albumBuilder.append("<li><a href=\"album").append(i).append(".html\"> ").append(album).append("</a></li>\n");
            i++;
        }
        String filePath = args.getPath() + File.separator + "index.html";
        try {
            Files.createFile(Paths.get(filePath));
        } catch (FileAlreadyExistsException e) {
            //ignore
        }
        File albumFile = new File(filePath);
        try (Writer writer = new FileWriter(albumFile, StandardCharsets.UTF_8)) {
            writer.write(MksiteUtils.getIndexPage(albumBuilder.toString()));
            writer.flush();
        }
        repo.create("index.html", albumFile);
        Files.delete(Paths.get(filePath));
        return new StringResult(repo.getDownloadURL("index.html"));
    }
}
