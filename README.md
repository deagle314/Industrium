# Industrium - Industrial Framework Mod

A large-scale industrial framework mod for Minecraft Java Edition Forge 1.20.1.

## Build

```bash
./gradlew build
```

## Run Client

```bash
./gradlew runClient
```

## Available Blocks

| Block | Description | Recipe |
|-------|-------------|--------|
| Coal Generator | Burns fuel to generate LV power | Iron + Redstone + Furnace |
| Battery Box | Stores LV power | Iron + Redstone + Gold |
| Power Cable | Transmits power | Redstone |
| Electric Furnace | Powered smelter | Iron + Furnace |

## Progression

```
Vanilla Materials
    ↓ (craft)
Machine Casing (9 iron)
    ↓
Coal Generator + Battery + Power Cable + Electric Furnace
    ↓ (assemble)
Automated ore processing
```

## Architecture

- `com.industrium.core.api.power` - Power API (VoltageTier, IEnergyGrid)
- `com.industrium.core.common.power` - Power implementation
- `com.industrium.core.common.machine` - Machine blocks

## License

All Rights Reserved
