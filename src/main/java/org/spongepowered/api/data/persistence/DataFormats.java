/*
 * This file is part of SpongeAPI, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.api.data.persistence;

import org.spongepowered.api.data.DataContainer;

import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * A pseudo-enum of supported {@link DataFormat}s.
 */
public final class DataFormats {

    /**
     * Allows reading and writing {@link DataContainer}s using the
     * Human-Optimized Config Object Notation (HOCON) configuration format.
     */
    public static final DataFormat HOCON = null;
    /**
     * Allows reading and writing using the Named Binary Tag format used for the
     * majority of minecraft's data files.
     * 
     * </p>It is <strong>highly</strong> recommended to wrap your input/output
     * stream in a {@link GZIPInputStream} or {@link GZIPOutputStream}
     * respectively as this is the standard.
     */
    public static final DataFormat NBT = null;

    private DataFormats() {
    }

}
