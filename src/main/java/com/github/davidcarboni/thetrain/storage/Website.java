package com.github.davidcarboni.thetrain.storage;

import com.github.davidcarboni.thetrain.helpers.Configuration;
import com.github.davidcarboni.thetrain.logging.Log;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Works out the directory that contains web content so that files can be published on transaction commit.
 */
public class Website {

    static Path path;

    /**
     * Determines the {@link Path} to the website content.
     * For development purposes, if no {@value Configuration#WEBSITE} configuration value is set
     * then a temponary folder is created.
     *
     * @return A path to the website root or, if the determined path does not point to a directory, null.
     * @throws IOException
     */
    public static Path path() throws IOException {
        Path result = null;

        // Get the Path to the website folder we're going to publish to
        if (path == null) {
            String websitePath = Configuration.Website();
            if (StringUtils.isNotBlank(websitePath)) {
                path = Paths.get(websitePath);
                Log.info("WEBSITE configured as: " + path);
            } else {
                path = Files.createTempDirectory("website");
                Log.info("Simulating website for development using a temp folder at: " + path);
                Log.info("Please configure a WEBSITE variable to configure this directory in production.");
            }
        }

        if (Files.isDirectory(path)) {
            result = path;
        } else {
            Log.info("The configured website path is not a directory: " + path);
        }

        return result;
    }

}
