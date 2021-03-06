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
package org.spongepowered.api.text.serializer;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.gson.GsonConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.text.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Optional;

/**
 * Represents a {@link TypeSerializer} for {@link Text} objects. Serialization
 * is handled by serializing the text to String with the
 * {@link TextSerializers#JSON} serializer, loading the String into a
 * {@link GsonConfigurationLoader}, and setting the value of the
 * {@link ConfigurationNode} to the root node of the GsonConfigurationLoader.
 * Although JSON is used for serialization internally, this has no effect on
 * the actual configuration format the developer chooses to use.
 */
public class TextConfigSerializer extends AbstractDataBuilder<Text> implements TypeSerializer<Text> {

    public TextConfigSerializer() {
        super(Text.class, 1);
    }

    @Override
    public Text deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
        StringWriter writer = new StringWriter();
        GsonConfigurationLoader gsonLoader = GsonConfigurationLoader.builder().setSink(() -> new BufferedWriter(writer)).build();
        try {
            gsonLoader.save(value);
        } catch (IOException e) {
            throw new ObjectMappingException(e);
        }
        return Sponge.getDataManager().deserialize(Text.class, new MemoryDataContainer().set(Queries.JSON, writer.getBuffer().toString())).get();
    }

    @Override
    public void serialize(TypeToken<?> type, Text obj, ConfigurationNode value) throws ObjectMappingException {
        String json = (String) obj.toContainer().get(Queries.JSON).get();
        GsonConfigurationLoader gsonLoader = GsonConfigurationLoader.builder().setSource(() -> new BufferedReader(new StringReader(json))).build();
        try {
            value.setValue(gsonLoader.load());
        } catch (IOException e) {
            throw new ObjectMappingException(e);
        }
    }

    @Override
    protected Optional<Text> buildContent(DataView container) throws InvalidDataException {
        Optional<Object> json = container.get(Queries.JSON);
        if (json.isPresent()) {
            try {
                //noinspection ConstantConditions
                return Optional.of(TextSerializers.JSON.deserialize(json.get().toString()));
            } catch (TextParseException e) {
                throw new InvalidDataException(e);
            }
        }
        return Optional.empty();
    }
}
