/*
 * Copyright 2021 Digital Tree Media Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cz.majksa.commons.logging;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <p><b>Interface {@link Logger}</b></p>
 *
 * @author Majksa
 * @version 1.0.0
 * @since 1.0.0
 */
public class Logger {

    /**
     * The Log4J {@link org.apache.logging.log4j.Logger}
     */
    protected final org.apache.logging.log4j.Logger log4jLogger;

    /**
     * The {@link cz.majksa.commons.logging.ErrorWriter}
     */
    protected final ErrorWriter errorWriter;

    /**
     * Is debug enabled?
     */
    @Getter
    @Setter
    protected boolean debug;

    /**
     * {@link Logger} constructor
     *
     * @param logger      {@link #log4jLogger}
     * @param errorWriter {@link #errorWriter}
     * @param debug       {@link #debug}
     */
    public Logger(org.apache.logging.log4j.Logger logger, ErrorWriter errorWriter, boolean debug) {
        this.log4jLogger = logger;
        this.errorWriter = errorWriter;
    }

    /**
     * Logs a message in debug mode
     *
     * @param message the message to be logged
     */
    public void debug(@NotNull Object message) {
        if (debug) {
            log4jLogger.debug(message);
        }
    }

    /**
     * Logs a message in debug mode
     *
     * @param message the message to be logged
     * @param throwable the throwable to be logged
     */
    public void debug(@NotNull Object message, @NotNull Throwable throwable) {
        if (debug) {
            log4jLogger.debug(message, throwable);
        }
    }

    /**
     * Logs a message in info mode
     *
     * @param message the message to be logged
     */
    public void info(@NotNull Object message) {
        log4jLogger.info(message);
    }

    /**
     * Logs a message in info mode
     *
     * @param message the message to be logged
     * @param throwable the throwable to be logged
     */
    public void info(@NotNull Object message, @NotNull Throwable throwable) {
        log4jLogger.info(message, throwable);
    }

    /**
     * Logs a message in warn mode
     *
     * @param message the message to be logged
     */
    public void warn(@NotNull Object message) {
        log4jLogger.warn(message);
    }

    /**
     * Logs a message in warn mode
     *
     * @param message the message to be logged
     * @param throwable the throwable to be logged
     */
    public void warn(@NotNull Object message, @NotNull Throwable throwable) {
        log4jLogger.warn(message, throwable);
    }

    /**
     * Logs a message in error mode
     *
     * @param throwable the throwable to be logged
     */
    @Nullable
    public String error(@NotNull Throwable throwable) {
        log4jLogger.error(throwable);
        if (debug) {
            return null;
        }
        return errorWriter.write(throwable);
    }

    /**
     * Logs a message in error mode
     *
     * @param message the message to be logged
     */
    @Nullable
    public String error(@NotNull Object message) {
        log4jLogger.error(message);
        if (debug) {
            return null;
        }
        return errorWriter.write(new RuntimeException(String.valueOf(message)));
    }

    /**
     * Logs a message in error mode
     *
     * @param message the message to be logged
     * @param throwable the throwable to be logged
     */
    @Nullable
    public String error(@NotNull Object message, @NotNull Throwable throwable) {
        log4jLogger.error(message, throwable);
        if (debug) {
            return null;
        }
        return errorWriter.write(throwable);
    }

}
