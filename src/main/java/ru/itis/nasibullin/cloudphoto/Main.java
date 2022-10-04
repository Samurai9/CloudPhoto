package ru.itis.nasibullin.cloudphoto;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import ru.itis.nasibullin.cloudphoto.commands.*;
import ru.itis.nasibullin.cloudphoto.entities.Arguments;
import ru.itis.nasibullin.cloudphoto.entities.Config;
import ru.itis.nasibullin.cloudphoto.exceptions.NoImagesException;
import ru.itis.nasibullin.cloudphoto.results.EmptyResult;
import ru.itis.nasibullin.cloudphoto.utils.ArgsParser;
import ru.itis.nasibullin.cloudphoto.utils.ConfigUtils;

import java.nio.file.NoSuchFileException;
import java.util.*;

public class Main {
    private Config config;

    public static void main(String[] args) {
        Main main = new Main();
        try {
            main.run(args);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void run(String[] args) {
        String command = args[0];
        Arguments arguments = ArgsParser.parse(Arrays.copyOfRange(args, 1, args.length));
        initConfig();

        switch (command) {
            case "upload":
                if (arguments.getAlbum() == null) {
                    throw new IllegalArgumentException("You need to specify album first");
                }
                if (arguments.getPath() == null) {
                    arguments.setPath(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
                }
                Command<Void> uploadCommand = new UploadCommand();
                uploadCommand.execute(config, arguments);
                break;
            case "download":
                if (arguments.getAlbum() == null) {
                    throw new IllegalArgumentException("You need to specify album first");
                }
                if (arguments.getPath() == null) {
                    arguments.setPath(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
                }
                Command<EmptyResult> downloadCommand = new DownloadCommand();
                downloadCommand.execute(config, arguments);
                break;
            case "list":
                Command<List<S3ObjectSummary>> listCommand = new ListCommand();
                List<S3ObjectSummary> images = listCommand.execute(config, arguments).getResult();
                if (images.size() == 0) {
                    throw new NoImagesException("No images or albums found");
                }
                if (arguments.getAlbum() == null || arguments.getAlbum().equals("")) {
                    Set<String> albums = new HashSet<>();
                    for (S3ObjectSummary image : images) {
                        if (image.getKey().split("/").length > 1) {
                            albums.add(image.getKey().split("/")[0]);
                        }
                    }
                    for (String album : albums) {
                        System.out.println(album);
                    }
                } else {
                    for (S3ObjectSummary image : images) {
                        System.out.println(image.getKey().split("/")[1]);
                    }
                }
                break;
            case "delete":
                if (arguments.getAlbum() == null) {
                    throw new IllegalArgumentException("You need to specify album first");
                }
                Command<EmptyResult> deleteCommand = new DeleteCommand();
                deleteCommand.execute(config, arguments);
                break;
            case "mksite":
                Command<String> mksite = new MksiteCommand();
                arguments.setPath(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
                System.out.println(mksite.execute(config, arguments).getResult());
                break;
            case "init":
                Scanner scanner = new Scanner(System.in);
                Config initConfig = new Config();

                System.out.println("Enter access key:");
                initConfig.setAccessKey(scanner.nextLine());
                System.out.println("Enter secret key:");
                initConfig.setSecretKey(scanner.nextLine());
                System.out.println("Enter bucket:");
                initConfig.setBucket(scanner.nextLine());

                initConfig.setRegion("ru-central1");
                initConfig.setEndpoint("https://storage.yandexcloud.net");

                Command<Void> init = new InitCommand();
                init.execute(initConfig, null);
                this.config = initConfig;
                break;
            default:
                throw new IllegalArgumentException("Unknown command: " + command);
        }
    }

    private void initConfig() {
        try {
            this.config = ConfigUtils.load();
        } catch (NoSuchFileException e) {
            this.config = null;
        }
    }
}
