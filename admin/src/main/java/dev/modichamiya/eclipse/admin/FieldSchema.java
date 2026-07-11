package dev.modichamiya.eclipse.admin;

import java.util.*;
import java.util.function.Predicate;

public record FieldSchema(String name,Class<?> type,boolean required,Object defaultValue,Predicate<Object> validator,String message){public FieldSchema{Objects.requireNonNull(name);Objects.requireNonNull(type);validator=validator==null?v->true:validator;message=message==null?"invalid value":message;}public Optional<String>validate(Object value){if(value==null)return required?Optional.of("required"):Optional.empty();if(!type.isInstance(value))return Optional.of("expected "+type.getSimpleName());return validator.test(value)?Optional.empty():Optional.of(message);}}
