#!/usr/bin/env python3
"""Generate industrial texture pack for Industrium mod."""
import zlib
import struct
import os

def make_png(w, h, pixels):
    def png_chunk(ct, d):
        c = ct + d
        return struct.pack('>I', len(d)) + c + struct.pack('>I', zlib.crc32(c) & 0xffffffff)
    
    raw = b''
    for y in range(h):
        raw += b'\x00'
        for x in range(w):
            r = int(pixels[y][x][0])
            g = int(pixels[y][x][1])
            b_val = int(pixels[y][x][2])
            raw += bytes([r, g, b_val, 255])
    
    ihdr = struct.pack('>IIBBBBB', w, h, 8, 6, 0, 0, 0)
    return b'\x89PNG\r\n\x1a\n' + png_chunk(b'IHDR', ihdr) + png_chunk(b'IDAT', zlib.compress(raw)) + png_chunk(b'IEND', b'')

def riveted_plate(r, g, b):
    p = [[[r, g, b, 255] for _ in range(32)] for _ in range(32)]
    for y in range(32):
        for x in range(32):
            if x < 2 or x > 29 or y < 2 or y > 29:
                p[y][x] = [r//2, g//2, b//2, 255]
            elif (x-6) % 8 == 0 and (y-6) % 8 == 0:
                p[y][x] = [160, 165, 170, 255]
    return p

def gauge_plate(r, g, b):
    p = riveted_plate(r, g, b)
    for y in range(12, 20):
        for x in range(12, 20):
            if (x-16)**2 + (y-16)**2 < 25:
                p[y][x] = [40, 45, 50, 255]
    for y in range(14, 16):
        p[y][16] = [200, 40, 40, 255]
    return p

def tank_plate(r, g, b):
    p = riveted_plate(r, g, b)
    for y in [8, 16, 24]:
        for x in range(4, 28):
            p[y][x] = [50, 55, 60, 255]
    return p

BASE = '/home/engine/project/src/main/resources/assets/industrium/textures'
os.makedirs(f'{BASE}/block', exist_ok=True)
os.makedirs(f'{BASE}/item', exist_ok=True)
os.makedirs(f'{BASE}/gui', exist_ok=True)

# Block textures
blocks = [
    ('coal_generator', 65, 60, 55, 'riveted'),
    ('battery_box', 170, 150, 45, 'gauge'),
    ('power_cable', 165, 105, 55, 'riveted'),
    ('electric_furnace', 95, 105, 120, 'riveted'),
    ('basic_battery', 175, 155, 50, 'gauge'),
    ('pipe_basic', 140, 90, 60, 'riveted'),
    ('cable_lv', 170, 110, 60, 'riveted'),
    ('conveyor_basic', 100, 105, 110, 'riveted'),
    ('tank_basic', 110, 110, 115, 'tank'),
    ('steam_boiler', 105, 110, 115, 'riveted'),
    ('boiler', 105, 110, 115, 'tank'),
    ('steam_engine', 110, 115, 120, 'riveted'),
    ('assembler', 110, 115, 120, 'riveted'),
    ('ore_washer', 105, 110, 115, 'riveted'),
    ('mixer', 110, 105, 100, 'riveted'),
    ('compressor', 120, 125, 130, 'riveted'),
    ('blast_furnace', 85, 90, 95, 'riveted'),
    ('diesel_generator', 100, 105, 110, 'riveted'),
    ('pipe_reinforced', 120, 85, 65, 'riveted'),
    ('cable_mv', 150, 100, 55, 'riveted'),
    ('lathe', 130, 135, 140, 'riveted'),
    ('press_machine', 110, 115, 120, 'riveted'),
    ('refinery', 95, 100, 105, 'riveted'),
    ('chemical_reactor', 110, 105, 100, 'riveted'),
    ('centrifuge', 115, 110, 105, 'riveted'),
    ('automation_controller', 70, 90, 110, 'gauge'),
    ('logistics_hub', 90, 95, 100, 'riveted'),
    ('industrial_battery', 185, 165, 55, 'gauge'),
    ('pipe_pressurized', 100, 75, 65, 'riveted'),
    ('cable_hv', 130, 90, 50, 'riveted'),
    ('turbine_generator', 100, 105, 110, 'riveted'),
    ('reactor_casing', 85, 90, 95, 'riveted'),
    ('heat_exchanger', 105, 110, 115, 'riveted'),
    ('data_core', 50, 70, 90, 'gauge'),
    ('fusion_coil', 95, 100, 105, 'riveted'),
    ('smart_factory_node', 80, 85, 90, 'riveted'),
    ('steel_scaffold', 110, 115, 120, 'riveted'),
    ('factory_floor', 100, 105, 110, 'riveted'),
    ('hazard_plate', 230, 210, 50, 'riveted'),
    ('maintenance_hatch', 95, 100, 105, 'riveted'),
    ('control_panel', 75, 80, 85, 'gauge'),
    ('industrial_lamp', 220, 210, 120, 'riveted'),
    ('gearbox', 110, 115, 120, 'riveted'),
    ('crusher', 105, 110, 115, 'riveted'),
    ('pulverizer', 110, 115, 120, 'riveted'),
    ('hand_press', 120, 125, 130, 'riveted'),
    ('rotational_motor', 110, 115, 120, 'riveted'),
    ('shaft', 105, 110, 115, 'riveted'),
    ('axle', 110, 115, 120, 'riveted'),
]

for name, r, g, b, st in blocks:
    if st == 'gauge' or 'gauge' in name:
        p = gauge_plate(r, g, b)
    elif 'tank' in name:
        p = tank_plate(r, g, b)
    else:
        p = riveted_plate(r, g, b)
    with open(f'{BASE}/block/{name}.png', 'wb') as f:
        f.write(make_png(32, 32, p))

# Item textures
items = [
    ('gear_iron', 145, 150, 155),
    ('gear_steel', 115, 120, 125),
    ('circuit_basic', 50, 90, 70),
    ('circuit_advanced', 50, 70, 90),
    ('motor', 105, 110, 115),
    ('rotor', 120, 125, 130),
    ('bearing', 110, 115, 120),
    ('plate_steel', 145, 150, 155),
    ('wire_copper', 170, 110, 60),
    ('battery_cell', 175, 155, 50),
]

for name, r, g, b in items:
    p = riveted_plate(r, g, b)
    with open(f'{BASE}/item/{name}.png', 'wb') as f:
        f.write(make_png(32, 32, p))

# GUI textures
guis = [
    ('machine_gui', 232, 202, 147),
    ('power_gui', 232, 180, 95),
    ('tank_gui', 160, 190, 210),
    ('control_gui', 190, 180, 140),
]

for name, r, g, b in guis:
    p = riveted_plate(r, g, b)
    with open(f'{BASE}/gui/{name}.png', 'wb') as f:
        f.write(make_png(32, 32, p))

print(f"Created {len(blocks)} block textures")
print(f"Created {len(items)} item textures")  
print(f"Created {len(guis)} GUI textures")