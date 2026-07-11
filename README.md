# Project Eclipse

A production-grade, data-driven MMORPG engine for Paper 1.21.8 and Java 21.

## Build

```bash
gradle clean build
```

The shaded plugin is written to `plugin/build/libs/`.

## Architecture

The project is a Gradle multi-module build. `core` owns lifecycle, events, and services; `api` contains stable contracts; database and configuration are isolated implementations; later engine and gameplay domains are separate modules with dependency direction enforced by Gradle.

Development follows the 20-phase Project Eclipse specification. Phase branches are merged only after CI, tests, and Paper smoke tests pass.
