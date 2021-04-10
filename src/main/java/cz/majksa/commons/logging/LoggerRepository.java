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

import cz.majksa.commons.file.Folder;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * <p><b>Class {@link LoggerRepository}</b></p>
 *
 * @author Majksa
 * @version 1.0.0
 * @see cz.majksa.commons.logging.Logger
 * @since 1.0.0
 */
public class LoggerRepository {

    /**
     * The registered loggers
     *
     * @see cz.majksa.commons.logging.Logger
     */
    private final Map<String, Logger> loggers = new HashMap<>();

    /**
     * The writer that is called when an error occurs
     *
     * @see cz.majksa.commons.logging.ErrorWriter
     */
    @Getter
    private final ErrorWriter errorsWriter;

    /**
     * Is debug mode enabled?
     * <ul>
     *      <li><b>Yes: </b>{@link cz.majksa.commons.logging.Logger#debug(Object)} does something</li>
     *      <li><b>No: </b>{@link cz.majksa.commons.logging.Logger#debug(Object)} does not do anything</li>
     * </ul>
     *
     * @see cz.majksa.commons.logging.Logger#debug(Object)
     * @see cz.majksa.commons.logging.Logger#debug(Object, Throwable)
     */
    private final boolean debug;

    /**
     * The root logger that can change the debug mode of all loggers.
     */
    @Getter
    private final RootLogger rootLogger;

    /**
     * {@link LoggerRepository} constructor
     *
     * @param errors {@link #errorsWriter}
     * @param debug  {@link #debug}
     */
    LoggerRepository(@NotNull String errors, boolean debug) {
        errorsWriter = new ErrorWriter(new Folder(errors));
        this.debug = debug;
        rootLogger = new RootLogger(errorsWriter, debug, loggers);
    }

    /**
     * Gets the logger for the provided name.<br>
     * Creates a new on if does not exist yet.
     *
     * @param name the name for the logger
     * @return {@link cz.majksa.commons.logging.Logger}
     */
    @NotNull
    public Logger get(@NotNull String name) {
        if (!loggers.containsKey(name)) {
            loggers.put(name, new Logger(LogManager.getLogger(name), errorsWriter, debug));
        }
        return loggers.get(name);
    }

}
