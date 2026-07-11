package dev.modichamiya.eclipse.registry;

import java.util.Objects;
import java.util.regex.Pattern;

public record ContentKey(String namespace, String value) implements Comparable<ContentKey> {
    private static final Pattern PART = Pattern.compile("[a-z0-9._-]+");
    private static final Pattern VALUE = Pattern.compile("[a-z0-9._/-]+");
    public ContentKey {
        Objects.requireNonNull(namespace); Objects.requireNonNull(value);
        if (!PART.matcher(namespace).matches() || !VALUE.matcher(value).matches()) throw new IllegalArgumentException("Invalid content key: " + namespace + ":" + value);
    }
    public static ContentKey parse(String text) {
        Objects.requireNonNull(text); int separator = text.indexOf(':');
        if (separator < 1 || separator == text.length() - 1 || text.indexOf(':', separator + 1) >= 0) throw new IllegalArgumentException("Expected namespace:value, got " + text);
        return new ContentKey(text.substring(0, separator), text.substring(separator + 1));
    }
    @Override public String toString() { return namespace + ":" + value; }
    @Override public int compareTo(ContentKey other) { return toString().compareTo(other.toString()); }
}
