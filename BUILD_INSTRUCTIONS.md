# Industrium Build Instructions

## Prerequisites

- Java 17 (JDK 17)
- Gradle 8.4+

## Build Commands

```bash
# Build the mod
./gradlew build

# Run development client
./gradlew runClient

# Run development server
./gradlew runServer
```

## Troubleshooting

### Java not found
Make sure JAVA_HOME is set to JDK 17:
```bash
export JAVA_HOME=/path/to/jdk-17
```

### Gradle issues
Clear gradle cache if needed:
```bash
./gradlew clean
rm -rf ~/.gradle/caches/modules-2/files-2.1/net.minecraftforge/
```

## Output

After successful build, the mod JAR will be at:
`build/libs/industrium-1.0.0.jar`