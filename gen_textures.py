#!/usr/bin/env python3
"""Generate high-resolution industrial texture pack for Industrium mod."""
import zlib
import struct
import os
import random

WIDTH = 512
HEIGHT = 512

def make_png(w, h, data):
    """Data should be a bytearray of RGBA values."""
    def png_chunk(ct, d):
        return struct.pack('>I', len(d)) + ct + d + struct.pack('>I', zlib.crc32(ct + d) & 0xffffffff)
    
    ihdr = struct.pack('>IIBBBBB', w, h, 8, 6, 0, 0, 0)
    
    # Add filter byte 0 to each scanline
    scanlines = bytearray()
    for y in range(h):
        scanlines.append(0)
        scanlines.extend(data[y * w * 4 : (y + 1) * w * 4])
        
    return b'\x89PNG\r\n\x1a\n' + \
           png_chunk(b'IHDR', ihdr) + \
           png_chunk(b'IDAT', zlib.compress(scanlines, level=6)) + \
           png_chunk(b'IEND', b'')

class TextureGen:
    def __init__(self):
        self.noise = self._generate_noise()
        
    def _generate_noise(self):
        # Pre-generate a noise buffer to speed things up
        buf = bytearray(WIDTH * HEIGHT)
        for i in range(len(buf)):
            buf[i] = random.randint(0, 30)
        return buf

    def _create_base(self, r, g, b):
        data = bytearray(WIDTH * HEIGHT * 4)
        for y in range(HEIGHT):
            for x in range(WIDTH):
                idx = (y * WIDTH + x) * 4
                n = self.noise[y * WIDTH + x]
                data[idx] = max(0, min(255, r + n - 15))
                data[idx+1] = max(0, min(255, g + n - 15))
                data[idx+2] = max(0, min(255, b + n - 15))
                data[idx+3] = 255
        return data

    def _add_bevel(self, data, size=16):
        for y in range(HEIGHT):
            for x in range(WIDTH):
                idx = (y * WIDTH + x) * 4
                factor = 1.0
                if x < size or y < size:
                    factor = 1.2
                elif x > WIDTH - size or y > HEIGHT - size:
                    factor = 0.8
                
                if factor != 1.0:
                    data[idx] = min(255, int(data[idx] * factor))
                    data[idx+1] = min(255, int(data[idx+1] * factor))
                    data[idx+2] = min(255, int(data[idx+2] * factor))

    def _add_rivets(self, data, color=(160, 165, 170)):
        rivet_size = 12
        spacing = 64
        margin = 32
        
        rivet_locs = []
        # Borders
        for x in range(margin, WIDTH, spacing):
            rivet_locs.append((x, margin))
            rivet_locs.append((x, HEIGHT - margin))
        for y in range(margin + spacing, HEIGHT - margin, spacing):
            rivet_locs.append((margin, y))
            rivet_locs.append((WIDTH - margin, y))
            
        for rx, ry in rivet_locs:
            for dy in range(-rivet_size, rivet_size):
                for dx in range(-rivet_size, rivet_size):
                    if dx*dx + dy*dy < rivet_size*rivet_size:
                        tx, ty = rx + dx, ry + dy
                        if 0 <= tx < WIDTH and 0 <= ty < HEIGHT:
                            idx = (ty * WIDTH + tx) * 4
                            # Simple shading
                            shade = 1.2 if dx < 0 or dy < 0 else 0.8
                            data[idx] = min(255, int(color[0] * shade))
                            data[idx+1] = min(255, int(color[1] * shade))
                            data[idx+2] = min(255, int(color[2] * shade))

    def _add_gauge(self, data, color=(201, 162, 39)):
        cx, cy = WIDTH // 2, HEIGHT // 2
        radius = 128
        # Brass rim
        for dy in range(-radius - 10, radius + 11):
            for dx in range(-radius - 10, radius + 11):
                d2 = dx*dx + dy*dy
                if radius**2 < d2 < (radius + 10)**2:
                    tx, ty = cx + dx, cy + dy
                    if 0 <= tx < WIDTH and 0 <= ty < HEIGHT:
                        idx = (ty * WIDTH + tx) * 4
                        data[idx], data[idx+1], data[idx+2] = color
                elif d2 <= radius**2:
                    tx, ty = cx + dx, cy + dy
                    if 0 <= tx < WIDTH and 0 <= ty < HEIGHT:
                        idx = (ty * WIDTH + tx) * 4
                        # White face
                        data[idx], data[idx+1], data[idx+2] = (240, 230, 210)
        
        # Needle (red)
        for i in range(radius - 20):
            tx, ty = cx + i, cy - i
            if 0 <= tx < WIDTH and 0 <= ty < HEIGHT:
                for dy in range(-2, 3):
                    for dx in range(-2, 3):
                        idx = ((ty+dy) * WIDTH + (tx+dx)) * 4
                        data[idx], data[idx+1], data[idx+2] = (200, 40, 40)

    def _add_tank_lines(self, data):
        for y in [HEIGHT // 4, HEIGHT // 2, 3 * HEIGHT // 4]:
            for x in range(64, WIDTH - 64):
                for dy in range(-4, 5):
                    idx = ((y + dy) * WIDTH + x) * 4
                    data[idx] = max(0, data[idx] - 40)
                    data[idx+1] = max(0, data[idx+1] - 40)
                    data[idx+2] = max(0, data[idx+2] - 40)

    def gen_riveted(self, r, g, b):
        data = self._create_base(r, g, b)
        self._add_bevel(data)
        self._add_rivets(data)
        return data

    def gen_gauge(self, r, g, b):
        data = self._create_base(r, g, b)
        self._add_bevel(data)
        self._add_rivets(data)
        self._add_gauge(data)
        return data

    def gen_tank(self, r, g, b):
        data = self._create_base(r, g, b)
        self._add_bevel(data)
        self._add_rivets(data)
        self._add_tank_lines(data)
        return data

    def gen_item(self, r, g, b, shape='gear'):
        # Items are 512x512 too but usually have transparency
        data = bytearray(WIDTH * HEIGHT * 4)
        cx, cy = WIDTH // 2, HEIGHT // 2
        
        if shape == 'gear':
            radius = 180
            for y in range(HEIGHT):
                for x in range(WIDTH):
                    dx, dy = x - cx, y - cy
                    d2 = dx*dx + dy*dy
                    # Simple gear shape logic
                    import math
                    angle = math.atan2(dy, dx)
                    dist = math.sqrt(d2)
                    # 8 teeth
                    teeth = 20 * math.sin(angle * 8)
                    if dist < radius + teeth and dist > 40:
                        idx = (y * WIDTH + x) * 4
                        n = self.noise[y * WIDTH + x]
                        data[idx] = max(0, min(255, r + n - 15))
                        data[idx+1] = max(0, min(255, g + n - 15))
                        data[idx+2] = max(0, min(255, b + n - 15))
                        data[idx+3] = 255
        elif shape == 'plate':
            for y in range(128, 384):
                for x in range(64, 448):
                    idx = (y * WIDTH + x) * 4
                    n = self.noise[y * WIDTH + x]
                    data[idx] = max(0, min(255, r + n - 15))
                    data[idx+1] = max(0, min(255, g + n - 15))
                    data[idx+2] = max(0, min(255, b + n - 15))
                    data[idx+3] = 255
        else: # Generic blob
            radius = 150
            for y in range(HEIGHT):
                for x in range(WIDTH):
                    dx, dy = x - cx, y - cy
                    if dx*dx + dy*dy < radius*radius:
                        idx = (y * WIDTH + x) * 4
                        n = self.noise[y * WIDTH + x]
                        data[idx] = max(0, min(255, r + n - 15))
                        data[idx+1] = max(0, min(255, g + n - 15))
                        data[idx+2] = max(0, min(255, b + n - 15))
                        data[idx+3] = 255
        return data

def main():
    random.seed(42)
    generator = TextureGen()
    BASE = '/home/engine/project/src/main/resources/assets/industrium/textures'
    os.makedirs(f'{BASE}/block', exist_ok=True)
    os.makedirs(f'{BASE}/item', exist_ok=True)
    
    IRON_GRAY = (61, 61, 61)
    COPPER_ORANGE = (184, 115, 51)
    BRASS_GOLD = (201, 162, 39)
    WARM_STEEL_BLUE = (74, 90, 106)
    AGED_BROWN = (90, 74, 58)
    
    blocks = [
        ('coal_generator', IRON_GRAY, 'riveted'),
        ('battery_box', BRASS_GOLD, 'gauge'),
        ('power_cable', COPPER_ORANGE, 'riveted'),
        ('electric_furnace', WARM_STEEL_BLUE, 'riveted'),
        ('basic_battery', BRASS_GOLD, 'gauge'),
        ('pipe_basic', IRON_GRAY, 'riveted'),
        ('cable_lv', COPPER_ORANGE, 'riveted'),
        ('conveyor_basic', IRON_GRAY, 'riveted'),
        ('tank_basic', WARM_STEEL_BLUE, 'tank'),
        ('steam_boiler', IRON_GRAY, 'tank'),
        ('boiler', IRON_GRAY, 'tank'),
        ('steam_engine', BRASS_GOLD, 'riveted'),
        ('assembler', WARM_STEEL_BLUE, 'riveted'),
        ('ore_washer', IRON_GRAY, 'riveted'),
        ('mixer', IRON_GRAY, 'riveted'),
        ('compressor', WARM_STEEL_BLUE, 'riveted'),
        ('blast_furnace', AGED_BROWN, 'riveted'),
        ('diesel_generator', IRON_GRAY, 'riveted'),
        ('pipe_reinforced', IRON_GRAY, 'riveted'),
        ('cable_mv', COPPER_ORANGE, 'riveted'),
        ('lathe', IRON_GRAY, 'riveted'),
        ('press_machine', IRON_GRAY, 'riveted'),
        ('refinery', WARM_STEEL_BLUE, 'riveted'),
        ('chemical_reactor', IRON_GRAY, 'riveted'),
        ('centrifuge', WARM_STEEL_BLUE, 'riveted'),
        ('automation_controller', BRASS_GOLD, 'gauge'),
        ('logistics_hub', IRON_GRAY, 'riveted'),
        ('industrial_battery', BRASS_GOLD, 'gauge'),
        ('pipe_pressurized', IRON_GRAY, 'riveted'),
        ('cable_hv', COPPER_ORANGE, 'riveted'),
        ('turbine_generator', WARM_STEEL_BLUE, 'riveted'),
        ('reactor_casing', IRON_GRAY, 'riveted'),
        ('heat_exchanger', COPPER_ORANGE, 'riveted'),
        ('data_core', BRASS_GOLD, 'gauge'),
        ('fusion_coil', COPPER_ORANGE, 'riveted'),
        ('smart_factory_node', WARM_STEEL_BLUE, 'riveted'),
        ('steel_scaffold', IRON_GRAY, 'riveted'),
        ('factory_floor', IRON_GRAY, 'riveted'),
        ('hazard_plate', (230, 210, 50), 'riveted'),
        ('maintenance_hatch', IRON_GRAY, 'riveted'),
        ('control_panel', BRASS_GOLD, 'gauge'),
        ('industrial_lamp', (220, 210, 120), 'riveted'),
        ('gearbox', IRON_GRAY, 'riveted'),
        ('crusher', IRON_GRAY, 'riveted'),
        ('pulverizer', IRON_GRAY, 'riveted'),
        ('hand_press', IRON_GRAY, 'riveted'),
        ('rotational_motor', IRON_GRAY, 'riveted'),
        ('shaft', IRON_GRAY, 'riveted'),
        ('axle', IRON_GRAY, 'riveted'),
        ('water_pump', IRON_GRAY, 'riveted'),
        ('air_compressor', IRON_GRAY, 'riveted'),
        ('heavy_grate', IRON_GRAY, 'riveted'),
        ('steam_pipe', IRON_GRAY, 'riveted'),
        ('brass_casing', BRASS_GOLD, 'riveted'),
        ('cast_iron_block', IRON_GRAY, 'riveted'),
        ('flywheel_clutch', IRON_GRAY, 'riveted'),
        ('pressure_valve', BRASS_GOLD, 'riveted'),
        ('ventilation_fan', IRON_GRAY, 'riveted'),
        ('lubricant_dispenser', IRON_GRAY, 'riveted'),
        ('mechanical_arm', IRON_GRAY, 'riveted'),
        ('steam_hammer', IRON_GRAY, 'riveted'),
        ('grinding_stone', AGED_BROWN, 'riveted'),
        ('sifter', IRON_GRAY, 'riveted'),
        ('magnetic_separator', IRON_GRAY, 'riveted'),
        ('coking_oven', AGED_BROWN, 'riveted'),
        ('steel_girder', IRON_GRAY, 'riveted'),
        ('industrial_window', WARM_STEEL_BLUE, 'riveted'),
        ('coal_chute', IRON_GRAY, 'riveted'),
        ('ash_bin', IRON_GRAY, 'riveted'),
        ('fluid_coupler', BRASS_GOLD, 'riveted'),
        ('belt_tensioner', IRON_GRAY, 'riveted'),
        ('clutch_lever', IRON_GRAY, 'riveted'),
        ('governor_valve', BRASS_GOLD, 'riveted'),
        ('whistle', BRASS_GOLD, 'riveted'),
        ('indicator_panel', BRASS_GOLD, 'gauge'),
        ('junction_box', IRON_GRAY, 'riveted'),
        ('transformer_lv', IRON_GRAY, 'riveted'),
        ('transformer_mv', IRON_GRAY, 'riveted'),
        ('transformer_hv', IRON_GRAY, 'riveted'),
        ('steam_condenser', IRON_GRAY, 'tank'),
    ]
    
    # 80 blocks exactly
    while len(blocks) < 80:
        blocks.append((f'industrial_block_{len(blocks)}', IRON_GRAY, 'riveted'))

    for name, color, style in blocks:
        if style == 'gauge':
            data = generator.gen_gauge(*color)
        elif style == 'tank':
            data = generator.gen_tank(*color)
        else:
            data = generator.gen_riveted(*color)
        
        with open(f'{BASE}/block/{name}.png', 'wb') as f:
            f.write(make_png(WIDTH, HEIGHT, data))
        print(f"Generated block: {name}")

    items = [
        ('gear_iron', IRON_GRAY, 'gear'),
        ('gear_steel', WARM_STEEL_BLUE, 'gear'),
        ('circuit_basic', (50, 90, 70), 'plate'),
        ('circuit_advanced', (50, 70, 90), 'plate'),
        ('motor', IRON_GRAY, 'blob'),
        ('rotor', IRON_GRAY, 'gear'),
        ('bearing', IRON_GRAY, 'gear'),
        ('plate_steel', WARM_STEEL_BLUE, 'plate'),
        ('wire_copper', COPPER_ORANGE, 'blob'),
        ('battery_cell', BRASS_GOLD, 'blob'),
        ('plate_brass', BRASS_GOLD, 'plate'),
        ('spring_steel', WARM_STEEL_BLUE, 'blob'),
        ('coil_copper', COPPER_ORANGE, 'blob'),
        ('vacuum_tube', (200, 200, 255), 'blob'),
        ('rubber_seal', (40, 40, 40), 'gear'),
        ('glass_tube', (200, 220, 255), 'blob'),
        ('thermocouple', BRASS_GOLD, 'blob'),
        ('graphite_rod', (30, 30, 30), 'blob'),
        ('piston_ring', IRON_GRAY, 'gear'),
        ('crankshaft', IRON_GRAY, 'blob'),
    ]

    # 20 items exactly
    while len(items) < 20:
        items.append((f'industrial_item_{len(items)}', IRON_GRAY, 'blob'))

    for name, color, shape in items:
        data = generator.gen_item(*color, shape=shape)
        with open(f'{BASE}/item/{name}.png', 'wb') as f:
            f.write(make_png(WIDTH, HEIGHT, data))
        print(f"Generated item: {name}")

if __name__ == "__main__":
    main()
