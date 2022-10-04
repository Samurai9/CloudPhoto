package ru.itis.nasibullin.cloudphoto.commands;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import ru.itis.nasibullin.cloudphoto.entities.Arguments;
import ru.itis.nasibullin.cloudphoto.entities.Config;
import ru.itis.nasibullin.cloudphoto.repositories.YandexCloudRepo;
import ru.itis.nasibullin.cloudphoto.results.ListImagesResult;
import ru.itis.nasibullin.cloudphoto.results.Result;

import java.util.List;

public class ListCommand implements Command<List<S3ObjectSummary>> {
    @Override
    public Result<List<S3ObjectSummary>> execute(Config config, Arguments args) {
        YandexCloudRepo repo = new YandexCloudRepo(config);
        return new ListImagesResult(repo.getAllImages(args));
    }
}
