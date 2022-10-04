package ru.itis.nasibullin.cloudphoto.utils;

import ru.itis.nasibullin.cloudphoto.entities.Arguments;

public class ArgsParser {

    public static Arguments parse(String[] args) {
        Arguments arguments = new Arguments();
        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "--path":
                    if (args[i + 1].endsWith("/")) {
                        arguments.setPath(args[i + 1].substring(0, args[i+1].length()-1));
                    } else {
                        arguments.setPath(args[i + 1]);
                    }
                    break;
                case "--album":
                    arguments.setAlbum(args[i + 1]);
                    break;
                case "--photo":
                    arguments.setPhoto(args[i + 1]);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown flag " + args[i]);
            }
        }
        return arguments;
    }
}
