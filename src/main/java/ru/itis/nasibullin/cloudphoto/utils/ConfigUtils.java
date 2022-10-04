package ru.itis.nasibullin.cloudphoto.utils;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile;
import org.ini4j.Wini;
import ru.itis.nasibullin.cloudphoto.entities.Config;
import ru.itis.nasibullin.cloudphoto.exceptions.InvalidConfigException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Map;

public class ConfigUtils {
    public static final String DEFAULT_PATH = System.getProperty("user.home") +
            File.separator + ".config" +
            File.separator + "cloudphoto" +
            File.separator + "cloudphotorc";

    public static Config buildConfig(Map<String, String> values) {
        return Config.builder()
                .bucket(values.get("bucket"))
                .accessKey(values.get("aws_access_key_id"))
                .secretKey(values.get("aws_secret_access_key"))
                .region(values.get("region"))
                .endpoint(values.get("endpoint_url"))
                .build();
    }

    public static void validateConfig(Config config) {
        if ((config.getBucket() == null || config.getBucket().equals("")) ||
                (config.getRegion() == null || config.getRegion().equals("")) ||
                (config.getEndpoint() == null || config.getEndpoint().equals("")) ||
                (config.getAccessKey() == null || config.getAccessKey().equals("")) ||
                (config.getSecretKey() == null || config.getSecretKey().equals(""))
        ) {
            throw new InvalidConfigException("Config is invalid");
        }
    }

    public static void save(Config config) {
        save(DEFAULT_PATH, config);
    }

    public static void save(String path, Config config) {
        try {
            File configFile = new File(path);
            if (!Files.exists(Paths.get(path))) {
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
            }

            Wini wini = new Wini(Files.newInputStream(configFile.toPath()));
            wini.put("DEFAULT", "bucket", config.getBucket());
            wini.put("DEFAULT", "aws_access_key_id", config.getAccessKey());
            wini.put("DEFAULT", "aws_secret_access_key", config.getSecretKey());
            wini.put("DEFAULT", "region", config.getRegion());
            wini.put("DEFAULT", "endpoint_url", config.getEndpoint());
            wini.store(configFile);
            //TODO
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static Config load() throws NoSuchFileException {
        return load(DEFAULT_PATH);
    }

    public static Config load(String path) throws NoSuchFileException {
        try {
            Wini wini = new Wini(Files.newInputStream(Paths.get(path)));
            Profile.Section section = wini.get("DEFAULT");
            return Config.builder()
                    .bucket(section.get("bucket"))
                    .accessKey(section.get("aws_access_key_id"))
                    .secretKey(section.get("aws_secret_access_key"))
                    .region(section.get("region"))
                    .endpoint(section.get("endpoint_url"))
                    .build();
            //TODO
        } catch (InvalidFileFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new NoSuchFileException("");
        }
    }
}
