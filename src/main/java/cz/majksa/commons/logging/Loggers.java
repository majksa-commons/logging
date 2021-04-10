/*
 *  Custom loggers with serializing thrown exceptions and an option to run code safely.
 *  Copyright (C) 2021  Majksa
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cz.majksa.commons.logging;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Properties;
import java.util.function.Supplier;

/**
 * <p><b>Class {@link Loggers}</b></p>
 *
 * @author Majksa
 * @version 1.0.0
 * @since 1.0.0
 */
public class Loggers {

    /**
     * The name of configuration file.<br>
     * Usually located at <code>{module}/src/main/resources/logger.properties</code>
     *
     * @see java.util.Properties
     */
    private static final String CONFIGURATION = "logger.properties";

    /**
     * The repository that contains all the loggers.
     *
     * @see cz.majksa.commons.logging.LoggerRepository
     */
    private static final LoggerRepository LOGGER_REPOSITORY;

    static {
        String errors = "errors";
        boolean debug = true;
        try {
            final Properties properties = new Properties();
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIGURATION));
            if (properties.containsKey("errors")) {
                errors = properties.getProperty("errors");
            }
            if (properties.containsKey("debug")) {
                debug = Boolean.parseBoolean(properties.getProperty("debug"));
            }
        } catch (IOException ignored) {
        }
        LOGGER_REPOSITORY = new LoggerRepository(errors, debug);
    }

    /**
     * Gets a logger for the class provided.<br>
     * Creates a new one if a logger for this class does not exist yet.
     *
     * @param clazz the {@link Class} provided
     * @return {@link cz.majksa.commons.logging.Logger}
     */
    public static Logger getLogger(Class<?> clazz) {
        return LOGGER_REPOSITORY.get(clazz.getName());
    }

    /**
     * Gets a logger for the name provided.<br>
     * Creates a new one if a logger for this name does not exist yet.
     *
     * @param name the provided name
     * @return {@link cz.majksa.commons.logging.Logger}
     */
    public static Logger getLogger(String name) {
        return LOGGER_REPOSITORY.get(name);
    }

    /**
     * Gets the root logger that can change the debug mode of all registered Loggers.
     *
     * @return {@link cz.majksa.commons.logging.Logger}
     */
    public static RootLogger getRootLogger() {
        return LOGGER_REPOSITORY.getRootLogger();
    }

    /**
     * Gets the {@link cz.majksa.commons.logging.ErrorWriter} containing all errors.
     *
     * @return {@link cz.majksa.commons.logging.ErrorWriter}
     */
    public static ErrorWriter getErrors() {
        return LOGGER_REPOSITORY.getErrorsWriter();
    }

    /**
     * Runs a supplier and returns the value.<br>
     * If a throwable appears, <code>null</code> is returned.
     *
     * @param supplier the supplier we want to prevent from throwing exceptions
     * @param <T>      the type that supplier provides
     * @return the value that provider provides or <code>null</code>
     */
    public static <T> @Nullable T run(@NonNull Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Throwable e) {
            getRootLogger().error(e);
            return null;
        }
    }

}
