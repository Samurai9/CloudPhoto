package ru.itis.nasibullin.cloudphoto.commands;

import ru.itis.nasibullin.cloudphoto.entities.Arguments;
import ru.itis.nasibullin.cloudphoto.entities.Config;
import ru.itis.nasibullin.cloudphoto.results.Result;
import ru.itis.nasibullin.cloudphoto.utils.ConfigUtils;

public class InitCommand implements Command<Void> {

    @Override
    public Result<Void> execute(Config config, Arguments args) {
        save(config);
        return null;
    }

    private void save(Config config) {
        ConfigUtils.save(config);
    }
}
