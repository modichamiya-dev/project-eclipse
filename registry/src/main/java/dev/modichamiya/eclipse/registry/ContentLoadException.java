package dev.modichamiya.eclipse.registry;

import java.util.List;

public final class ContentLoadException extends Exception {
    private final List<String> errors;
    public ContentLoadException(List<String> errors) { super(String.join(System.lineSeparator(), errors)); this.errors = List.copyOf(errors); }
    public List<String> errors() { return errors; }
}
