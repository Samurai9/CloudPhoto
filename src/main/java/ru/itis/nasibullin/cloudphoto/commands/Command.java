package ru.itis.nasibullin.cloudphoto.commands;

import ru.itis.nasibullin.cloudphoto.entities.Arguments;
import ru.itis.nasibullin.cloudphoto.entities.Config;
import ru.itis.nasibullin.cloudphoto.results.Result;

public interface Command<T> {
    Result<T> execute(Config config, Arguments args);
}
