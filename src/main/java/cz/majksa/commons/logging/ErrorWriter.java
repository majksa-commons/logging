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
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p><b>Class {@link ErrorWriter}</b></p>
 *
 * @author Majksa
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class ErrorWriter {

    /**
     * The suffix of the file.
     */
    private static final String SUFFIX = ".ser";

    /**
     * The folder where new files are created.
     */
    private final Folder folder;

    /**
     * Saves the throwable into a file with a random id.
     *
     * @param throwable the throwable to be saved
     * @return the id of the saved file
     */
    public @NotNull String write(@NotNull Throwable throwable) {
        final String filename = UUID.randomUUID().toString();
        try {
            final FileOutputStream fileOut = new FileOutputStream(folder.getFile(filename + SUFFIX, true));
            final ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(throwable);
            out.close();
            fileOut.close();
            return filename;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the throwable from a file found by the id.
     *
     * @param identifier the id to be searched
     * @return the created throwable
     * @throws NullPointerException when the throwable by given id was not found
     */
    public @NotNull Throwable read(@NotNull String identifier) throws NullPointerException {
        try {
            final File file = folder.getFile(identifier + SUFFIX, false);
            if (!file.exists()) {
                throw new NullPointerException(String.format("Throwable saved with id %s was not found.", identifier));
            }
            final FileInputStream fileIn = new FileInputStream(file);
            final ObjectInputStream in = new ObjectInputStream(fileIn);
            final Throwable throwable = (Throwable) in.readObject();
            in.close();
            fileIn.close();
            return throwable;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes the file by given id.
     *
     * @param identifier the identifier of file to be deleted
     * @return if file was deleted
     * @throws NullPointerException when the throwable by given id was not found
     */
    public boolean delete(@NotNull String identifier) throws NullPointerException {
        final File file = folder.getFile(identifier + SUFFIX, false);
        if (!file.exists()) {
            throw new NullPointerException(String.format("Throwable saved with id %s was not found.", identifier));
        }
        return file.delete();
    }

    /**
     * Lists all saved throwables' ids
     *
     * @return the {@link List} of ids
     */
    public @NotNull List<String> list() {
        List<String> identifiers = new ArrayList<>();
        for (File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                identifiers.add(file.getName().subSequence(0, file.getName().length() - SUFFIX.length()).toString());
            }
        }
        return identifiers;
    }

    /**
     * Deletes all files in the error directory.
     */
    public void clear() {
        for (File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                if (!file.delete()) {
                    throw new RuntimeException(String.format("File `%s` could not have been deleted.", file.getName()));
                }
            }
        }
    }

}
