package org.example;
import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    private static Dotenv dotenv;

    private Config() {}

    // Lazy-load dotenv only once
    private static Dotenv getDotenv() {
        if (dotenv == null) {
            dotenv = Dotenv.load();
        }
        return dotenv;
    }

    public static String getEnv(String key) {
        return getDotenv().get(key);
    }
}
