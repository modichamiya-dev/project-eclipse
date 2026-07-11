package dev.modichamiya.eclipse.api;

import java.util.Optional;

public interface EclipseApi {String apiVersion();<T>Optional<T>service(Class<T>type);}
