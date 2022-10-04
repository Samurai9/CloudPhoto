package ru.itis.nasibullin.cloudphoto.utils;

public class MksiteUtils {
    public static String getErrorPage() {
        return "<!doctype html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <meta charset=utf-8>\n" +
                "        <title>Фотоархив</title>\n" +
                "    </head>\n" +
                "<body>\n" +
                "    <h1>Ошибка</h1>\n" +
                "    <p>Ошибка при доступе к фотоархиву. Вернитесь на <a href=\"index.html\">главную страницу</a> фотоархива.</p>\n" +
                "</body>\n" +
                "</html>";
    }

    public static String getAlbumPage(String images) {
        return "<!doctype html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <meta charset=utf-8>\n" +
                "        <link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdnjs.cloudflare.com/ajax/libs/galleria/1.6.1/themes/classic/galleria.classic.min.css\" />\n" +
                "        <style>\n" +
                "            .galleria{ width: 960px; height: 540px; background: #000 }\n" +
                "        </style>\n" +
                "        <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js\"></script>\n" +
                "        <script src=\"https://cdnjs.cloudflare.com/ajax/libs/galleria/1.6.1/galleria.min.js\"></script>\n" +
                "        <script src=\"https://cdnjs.cloudflare.com/ajax/libs/galleria/1.6.1/themes/classic/galleria.classic.min.js\"></script>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <div class=\"galleria\">\n" +
                images +
                "        </div>\n" +
                "        <p>Вернуться на <a href=\"index.html\">главную страницу</a> фотоархива</p>\n" +
                "        <script>\n" +
                "            (function() {\n" +
                "                Galleria.run('.galleria');\n" +
                "            }());\n" +
                "        </script>\n" +
                "    </body>\n" +
                "</html>";
    }

    public static String getIndexPage(String albums) {
        return "<!doctype html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <meta charset=utf-8>\n" +
                "        <title>Фотоархив</title>\n" +
                "    </head>\n" +
                "<body>\n" +
                "    <h1>Фотоархив</h1>\n" +
                "    <ul>\n" +
                albums +
                "    </ul>\n" +
                "</body";
    }
}
