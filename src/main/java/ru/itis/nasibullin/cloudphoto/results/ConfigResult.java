package ru.itis.nasibullin.cloudphoto.results;

import ru.itis.nasibullin.cloudphoto.entities.Config;

import java.util.Map;

public class ConfigResult implements Result<Config> {
    private final Config config;

    public ConfigResult(Map<String, String> values) {
        if (values.size() != 5) {
            throw new IllegalArgumentException("Invalid config provided");
        }
        config = Config.builder().bucket(values.get("bucket"))
                .accessKey(values.get("aws_access_key_id"))
                .secretKey(values.get("aws_secret_access_key"))
                .region(values.get("region"))
                .endpoint(values.get("endpoint_url"))
                .build();
    }

    public ConfigResult(Config config) {
        this.config = config;
    }

    @Override
    public Config getResult() {
        return config;
    }
}
