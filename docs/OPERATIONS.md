# Project Eclipse operations

## Build and deploy

Build with Java 21 using `gradle clean build`. Deploy the shaded JAR from `plugin/build/libs/` to a Paper 1.21.8 staging server first. Back up the plugin data directory and database before every upgrade.

## Rollback

Stop Paper cleanly, restore the previous plugin JAR and matching database backup, then restart. Never replace the SQLite file while Paper is running.

## Health checks

Confirm all modules enable, migrations complete, profiles load asynchronously, content validation passes, and no database work appears on the server thread. Review CI artifacts and logs before promotion.

## Capacity

Load-test combat, AI, timelines, GUIs, dimensions, and economy against the target 150 to 300 concurrent-player profile before public launch. CI proves compilation and unit behavior, not production capacity.
