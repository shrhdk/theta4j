/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j;

import java.io.Closeable;
import java.io.IOException;
import java.util.Stack;

public final class Closer implements Closeable {
    private boolean isClosed;
    private final Stack<Closeable> closeables = new Stack<>();

    public synchronized <T extends Closeable> T push(T closeable) {
        if (isClosed) {
            throw new IllegalStateException(String.format("%s is already closed.", this));
        }

        if (closeable == null) {
            return null;
        }

        closeables.push(closeable);

        return closeable;
    }

    @Override
    public synchronized void close() {
        isClosed = true;

        while (!closeables.empty()) {
            try {
                closeables.pop().close();
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        if (!isClosed) {
            close();
        }

        super.finalize();
    }
}