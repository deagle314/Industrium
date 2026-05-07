# Industrium - Industrial Foundation Mod

An industrial foundation mod for Minecraft Java Edition Forge 1.20.1. Provides infrastructure systems for factory automation.

## Build Instructions

### Prerequisites
- Java 17+ (JDK 21 recommended)
- Gradle 8.4+

### Setup Commands

```bash
# Linux/macOS
./gradlew setupDecompWorkspace
./gradlew genEclipseRuns

# Windows
gradlew.bat setupDecompWorkspace
gradlew.bat genEclipseRuns
```

### Build Commands

```bash
# Build the mod JAR
./gradlew build

# Run client (for testing)
./gradlew runClient

# Run dedicated server
./gradlew runServer
```

### IDE Setup

**IntelliJ IDEA:**
```bash
./gradlew idea
```

**Eclipse:**
```bash
./gradlew genEclipseRuns
```

## Project Structure

```
src/main/java/com/industrium/core/
├── api/                    # Public API interfaces
│   ├── power/             # Energy system APIs
│   ├── heat/              # Heat system APIs
│   ├── rotation/          # Rotational power APIs
│   ├── fluid/             # Fluid piping APIs
│   └── logistics/         # Item logistics APIs
├── common/
│   ├── block/            # Block implementations
│   ├── blockentity/        # Block entity classes
│   └── registry/          # Registration helpers
└── Industrium.java       # Main mod class
```

## Implemented Systems

1. **Power System** - Energy tiers (LV/MV/HV/EHV), cables, storage, generators
2. **Heat System** - Temperature, heat transfer, heaters, boilers
3. **Rotational Power** - Shafts, gearboxes, axles, motors
4. **Fluid System** - Pipes, tanks, pumps, pressure handling
5. **Logistics** - Storage crates, conveyors, warehouses

## Version Information

- Mod Version: 1.0.0
- Minecraft: 1.20.1
- Forge MDK: 47.4.0
- Java: 17+

## License

All Rights Reserved