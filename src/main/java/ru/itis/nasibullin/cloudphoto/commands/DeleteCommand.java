package ru.itis.nasibullin.cloudphoto.commands;

import ru.itis.nasibullin.cloudphoto.entities.Arguments;
import ru.itis.nasibullin.cloudphoto.entities.Config;
import ru.itis.nasibullin.cloudphoto.repositories.YandexCloudRepo;
import ru.itis.nasibullin.cloudphoto.results.EmptyResult;
import ru.itis.nasibullin.cloudphoto.results.Result;

public class DeleteCommand implements Command<EmptyResult> {
    @Override
    public Result<EmptyResult> execute(Config config, Arguments args) {
        YandexCloudRepo repo = new YandexCloudRepo(config);
        repo.delete(args);
        return null;
    }
}
