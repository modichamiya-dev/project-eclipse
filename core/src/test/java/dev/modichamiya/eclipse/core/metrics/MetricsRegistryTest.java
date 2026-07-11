package dev.modichamiya.eclipse.core.metrics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MetricsRegistryTest {@Test void recordsCounters(){MetricsRegistry metrics=new MetricsRegistry();metrics.increment("joins");metrics.add("joins",2);assertEquals(3,metrics.count("joins"));assertEquals(3,metrics.snapshot().get("joins"));}}
