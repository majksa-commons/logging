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

import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * <p><b>Class {@link cz.majksa.commons.logging.RootLogger}</b></p>
 * The logger that can change debug mode of all registered loggers.
 *
 * @author Majksa
 * @version 1.0.0
 * @see cz.majksa.commons.logging.Logger
 * @since 1.0.0
 */
public class RootLogger extends Logger {

    /**
     * Map of registered loggers to change their debug mode when {@link #setDebug(boolean)} is called.
     */
    private final Map<String, Logger> loggers;

    /**
     * {@link cz.majksa.commons.logging.RootLogger} constructor
     *
     * @param errorWriter {@link #errorWriter}
     * @param debug       {@link #debug}
     * @param loggers     {@link #loggers}
     */
    public RootLogger(@NotNull ErrorWriter errorWriter, boolean debug, @NotNull Map<String, Logger> loggers) {
        super(LogManager.getRootLogger(), errorWriter, debug);
        this.loggers = loggers;
        setDebug(debug);
    }

    /**
     * {@link #debug} setter
     *
     * @param debug the new debug mode
     */
    @Override
    public void setDebug(boolean debug) {
        super.setDebug(debug);
        loggers.values().forEach(logger -> logger.setDebug(debug));
    }

}
