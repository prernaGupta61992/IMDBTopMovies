package com.imdbmovies.adaptor;

import java.io.IOException;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class IntTypeAdapter extends TypeAdapter<Number> {
    @Override
    public Number read(final JsonReader in) throws IOException
    {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        try {
            final JsonToken jsonToken = in.peek();
            switch (jsonToken) {
                case NUMBER:
                case STRING:
                    final String s = in.nextString();
                    try {
                        return Integer.parseInt(s);
                    } catch (final NumberFormatException ignored) {
                    }
                    return null;
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    throw new JsonSyntaxException("Expecting number, got: " + jsonToken);
            }
        } catch (final NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    @Override
    public void write(final JsonWriter out, final Number value) throws IOException {
        out.value(value);

    }

}
