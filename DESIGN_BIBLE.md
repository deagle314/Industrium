# INDUSTRIUM DESIGN BIBLE
## Visual Identity, UX & Sound Specification Document
### Version 1.0 | For Internal Production Reference

---

# SECTION 1: CORE CREATIVE DIRECTION

## 1.1 Emotional Philosophy

Industrium exists at the intersection of **pragmatic engineering** and **romantic monumentality**. Every asset the player encounters should communicate:

- **The weight of labor** - Machines look like they were built to be used, not displayed
- **The arc of progress** - Progression is visible, literal, and rewardingly physical
- **The hum of civilization** - The world feels alive with potential energy

### Player Emotional Journey

| Game Phase | Emotional Target | Design Focus |
|----------|---------------|------------|
| Early (T1-T2) | Curiosity + Craftsmanship | Hand-cranked, belt-driven, visible mechanism |
| Mid (T3-T4) | Productivity + Mastery | Electric motors, conveyors, growing complexity |
| Late (T5+) | Dominion + Grandeur | Massive multiblocks, control rooms, grid networks |

The player should never feel like they're "playing inventory management." They should feel like **they're building an empire.**

---

# SECTION 2: VISUAL IDENTITY

## 2.1 Material Library

All textures and models must utilize these material categories consistently:

### Primary Metals
| Material | Hex Reference | Use Case |
|----------|--------------|----------|
| Cast Iron | #3D3D3D | Base machine casings, heavy frames |
| Wrought Iron | #5A5A5A | Pipes, fittings, decorative straps |
| Steel | #6B7B8C | Modern casings, reinforced sections |
| Stainless Steel | #C0C0C0 | Food-safe, visible piping |
| Copper | #B87333 | Electrical, heat exchange, weathered green patina |
| Brass | #C9A227 | Gauges, decorative trim, T1-T2 machinery |
| Bronze | #8B6914 | Historic machinery, aged components |
| Aluminum | #D1D1D1 | Lightweight casings, heat sinks |
| Weathered/Rust | #6E4E3A | Industrial wear, outdoor exposure |

### Structural Materials
| Material | Hex Reference | Use Case |
|----------|--------------|----------|
| Riveted Plate | #4A4A4A + rivets | Heavy machinery casings |
| Welded Seam | #555555 + weld lines | Modern steel construction |
| I-Beam | #5A5A5A | Structural supports |
| Grate | #3A3A3A | Flooring, ventilation |
| Reinforced Concrete | #9E9E9E | Foundations, walls |
| Firebrick | #8B4040 | Furnace interiors |
| Ceramic Insulation | #D4C4A8 | Heater exteriors, pipe wrap |
| Wood Crate | #8B6914 | Early storage, packaging |

### Utility Elements
| Element | Appearance | Use Case |
|---------|----------|----------|
| Rubber Hose | #2A2A2A | Fluid transfer, flexible connection |
| Glass Gauge | #88CCFF + transparency | Level indicators, windows |
| Valve Wheel | Brass + spokes | Flow control |
| Warning Stripe | #FFD700 / #1A1A1A | Hazard marking |
| Label Plate | #F0F0F0 + text | Machine identification |
| Bolt Head | #4A4A4A + hex | Fastener detail |
| Vent Grille | #3A3A3A + slots | Heat dissipation |

## 2.2 Color Palette

### Core Palette (Dominant)
```
IRON_GRAY       = #3D3D3D
COAL_BLACK      = #1A1A1A
WARM_STEEL_BLUE = #4A5A6A
COPPER_ORANGE   = #B87333
BRASS_GOLD     = #C9A227
CONCRETE_BEIGE  = #9E9E9E
AGED_BROWN     = #5A4A3A
DARK_RED       = #6E2A2A
```

### Functional Accents
```
CAUTION_YELLOW  = #FFD700
DANGER_RED    = #CC2222
COLD_BLUE     = #3388DD
ACTIVE_GREEN  = #22CC44
CONTROL_WHITE = #F0F0F0
POWER_ORANGE  = #FF8800
VOLTAGE_BLUE  = #4488FF
STEAM_WHITE   = #EEEEEE
```

## 2.3 Era/Tier Visual Progression

Each tier should look visibly different while maintaining aesthetic consistency:

### Tier 0: Primitive
- Rough-cut wood frames
- Stone furnace cores
- Iron band straps
- Soot-blackened surfaces
- Hand-cranked mechanisms

### Tier 1: Steam Power
- Brass gauge faces
- Cylindrical boilers
- Exposed piping
- Brick furnace bases
- Flywheel momentum visuals

### Tier 2: Electrification  
- Copper wire bundles
- Ceramic insulators
- Electric motors
- Switchbox panels
- Junction boxes

### Tier 3: Heavy Industry
- Steel frame construction
- Conveyor belt systems
- Large storage tanks
- Substation transformers
- Rail logistics

### Tier 4: Automation
- Control panel interfaces
- Clean modular casings
- LED indicator arrays
- Modular assembly lines

### Tier 5: Megaproject
- Massive turbine housings
- Tower structures
- Reactor vessels
- Grid hub substations

---

# SECTION 3: BLOCK MODELING RULES

## 3.1 Fundamental Principles

**CRITICAL**: No "magic cubes with icons." Every block must visually communicate its function through:

1. **Silhouette storytelling** - Outline suggests purpose
2. **Surface detail** - Ports, vents, gauges tell the story
3. **Side-specific design** - Each face serves a purpose
4. **Material honesty** - Shows what it's made of

## 3.2 Block Category Templates

### GENERATOR BLOCKS
```
Visual Elements:
├── Rotating rotor (animated)
├── Exhaust port with particle effects
├── Ventilation grilles
├── Maintenance access hatch
├── Power output cable port
└── Status indicator lamp
Materials: Cast iron frame, brass trim, copper windings
```

### PROCESSOR BLOCKS (Crushers, Mills, etc.)
```
Visual Elements:
├── Active mechanism (rollers/jaw/blades animated)
├── Input hopper visible
├── Output chute
├── Guard panels (hazard aesthetic)
├── Vibration isolation mounts
├── Control interface
└── Collection container
Materials: Steel frame, heavy-duty guards, rubber seals
```

### TANK BLOCKS
```
Visual Elements:
├── Curved shell panels
├── Ladder rungs (full block)
├── Level gauge (glass or bar)
├── Pressure gauge
├── Inlet/outlet ports
├── Reinforcement ribs
└── Ground mounting feet
Materials: Welded steel, riveted sections, glass gauge
```

### ENERGY STORAGE BLOCKS
```
Visual Elements:
├── Cell array (visible batteries/capacitors)
├── Cable connection ports
├── Charge level indicator
├── Warning labels
├── Thermal management vents
└── Maintenance panel
Materials: Steel casing, copper bus bars, indicator lamps
```

### HEATER/BOILER BLOCKS
```
Visual Elements:
├── Glowing internal chamber (emissive)
├── Coil or heating element
├── Insulation jacket visible
├── Temperature gauge
├── Steam output port
├── Water inlet
├── Safety valve
└── Soot/ash collection
Materials: Firebrick interior, steel shell, ceramic insulation
```

### PUMP/MOTOR BLOCKS
```
Visual Elements:
├── Motor housing
├── Impeller/blade suggestion
├── Pipe port connections
├── Drive shaft extension
├── Mounting base
└── Control interface
Materials: Cast housing, brass fittings, rubber mount
```

---

# SECTION 4: TEXTURE SPECIFICATION

## 4.1 PBR-Adjacent Style

Textures must work without shaders but imply depth:

### Required Texture Detail Layers

1. **Base color** - Flat material color
2. **Grime/wear** - Oil stains, soot deposits (subtle)
3. **Surface scratches** - Usage evidence
4. **Fastener detail** - Bolts, rivets, seams
5. **Paint condition** - Fade, peel, chip marks
6. **Heat discoloration** - Near hot components
7. **Stamped markings** - Brand/lot numbers

### Resolution Standards
| Asset Type | Resolution | Notes |
|-----------|------------|-------|
| Standard blocks | 16x16 | Faithful vanilla feel |
| Preferred standard | 32x32 | Balance of detail/performance |
| Hero assets | 64x64 | Key machines, GUIs |
| UI elements | Custom | Based on screen space |

### Readability Test
ALL textures must pass the "thumbnail test":
- Readable at 16x16 after scaling
- Distinguishable from similar blocks
- Clear silhouette at distance

---

# SECTION 5: GUI / UX SPECIFICATION

## 5.1 Control Room Aesthetic

GUIs should feel like operating industrial control equipment:

### Aesthetic Anchors
- Analog panel inspiration
- Instrument cluster feel
- Military/industrial HMI design
- Maintenance console readability

### GUI Color System
```
PANEL_BACKGROUND = #1A1A1A (dark steel)
PANEL_BORDER   = #4A4A4A (worn metal)
TEXT_PRIMARY  = #F0E6D2 (off-white)
TEXT_AMBER    = #FFB000 (indicator glow)
WARNING_RED  = #FF3333
OPERATIONAL  = #44FF44
INACTIVE    = #555555
```

### Information Hierarchy
Every machine GUI must display, in priority order:
1. **Machine state** (running/offline/error)
2. **Input inventory/energy**
3. **Output inventory/energy**
4. **Efficiency metric**
5. **Warning indicators**
6. **Advanced tab** (for detailed stats)

## 5.2 UI Element Library

### Standard Controls
- [ ] On/Off toggle switch
- [ ] Speed RPM dial
- [ ] Temperature bar
- [ ] Pressure gauge
- [ ] Capacity meter
- [ ] Efficiency percentage
- [ ] Process progress bar
- [ ] Energy tier indicator
- [ ] Flow direction arrow
- [ ] Warning indicator lamp

---

# SECTION 6: ANIMATION RULES

## 6.1 Motion Language

All animated elements must feel:

| Element | Animation | Timing |
|---------|----------|--------|
| Shafts | Rotate 360° | 20-60 RPM depending on machine |
| Belts | Scroll texture | Continuous flow |
| Pistons | Extend/Retract | 1-3 second cycle |
| Gauges | Needle movement | Responsive to values |
| Exhaust | Particle pulse | 2-4 second cycle |
| Fans | Spin | Continuous, speed-variable |
| Warning lights | Blink | 1 second on/off |
| Steam | Particle burst | On output cycle |

## 6.2 Idle Animation

ALL machines should have subtle idle animation:
- Indicator lamp "breathing"
- Gauges with micro-movement
- Subtle particle drift

---

# SECTION 7: PARTICLE SPECIFICATION

## 7.1 Functional Particles

| Effect | Visual | Trigger |
|--------|--------|--------|
| Steam | Dense white burst | Boiler output, steam vent |
| Heat shimmer | Transparent wave | Near heater outputs |
| Electric arc | Blue/yellow sparks | High-voltage connections |
| Dust cloud | Brown/gray | Ore processing |
| Oil droplet | Dark sphere | Fluid transfer, gears |
| Pressure vent | White/gray violent jet | Safety valve, overpressure |

---

# SECTION 8: SOUND IDENTITY

## 8.1 Core Sound Palette

### Mechanical Sounds
| Sound | Description | Use Case |
|-------|------------|---------|
| Metal clank | Impact + ring | Machine collision, footsteps |
| Ratchet click | Mechanical detent | Crank turning, stepped output |
| Bearing whirr | Rotating smooth hum | Motor running |
| Gear grind | Meshing teeth | Crusher, mixer startup |

### Steam Sounds
| Sound | Description | Use Case |
|-------|------------|---------|
| Steam hiss | Release pressure | Valve opening, boiler |
| Boiler rumble | Low-frequency growl | Large boiler running |
| Pressure release | Violent vent | Safety valve, overpressure |

### Electrical Sounds
| Sound | Description | Use Case |
|-------|------------|---------|
| Transformer hum | 60Hz deep buzz | Generators, substations |
| Relay click | Sharp snap | Switch engagement |
| Arc crackle | Electric snap | High-voltage connection |

### Heavy Industry
| Sound | Description | Use Case |
|-------|------------|---------|
| Deep motor | Startup groan | Large machines |
| Compressor | Pulsing pump | Pump running |
| Conveyor roll | Continuous rumble | Belt systems |

### Environmental
| Sound | Description | Use Case |
|-------|------------|---------|
| Factory ambience | Layered room tone | Within machine halls |
| Substation buzz | Electrical hum | Near power infrastructure |

## 8.2 Sound Rules

1. **Loop pleasantness**: Loops must not cause listener fatigue
2. **Distance attenuation**: Larger machines audible from farther
3. **State distinctiveness**: Startup/shutdown/overload must have unique sounds

---

# SECTION 9: BRANDING GUIDELINES

## 9.1 Logo Direction

Industrium branding should evoke:
- Forged steel feeling
- Bold serif or industrial sans letterforms
- Gear or infrastructure motifs
- Corporation meets state infrastructure

---

# SECTION 10: ANTI-PATTERNS

## 10.1 Prohibited Aesthetics

Do NOT produce assets that feel:
- ✗ Magical/glowing for no reason
- ✗ Cartoon/toy-like  
- ✗ Random sci-fi neon
- ✗ Generic fantasy (floating swords)
- ✗ Apple-style smooth tech
- ✗ Textureless cubes
- ✗ Cluttered unreadable noise
- ✗ Copy-paste machine spam
- ✗ Rainbow color coding

---

# APPENDIX: ASSETS REQUIRING GENERATION

| Priority | Asset Category | Examples |
|----------|---------------|----------|
| HIGH | Core machines | Generator, Crusher, Smelter, Boiler |
| HIGH | Power blocks | Battery, PowerCable, Transformer |
| HIGH | Storage | Tank, Crate, Warehouse |
| HIGH | Logistics | ConveyorBelt, Pipe, Pump |
| MEDIUM | Heating | Heater, HeatPipe, Boiler |
| MEDIUM | Rotation | Shaft, Gearbox, Motor |
| LOW | Decorative | Fences, ladders, rails |

---

*Document Version: 1.0*
*For Industrium Internal Production Use Only*