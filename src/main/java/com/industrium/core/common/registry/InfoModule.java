package com.industrium.core.common.registry;

import com.industrium.core.common.info.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class InfoModule {
    public static final RegistryObject<Block> CENTRAL_CONSOLE = register("central_console", CentralConsoleBlock::new);
    public static final RegistryObject<Block> EMERGENCY_STOP_SWITCH = register("emergency_stop_switch", EmergencyStopSwitchBlock::new);
    public static final RegistryObject<Block> INDICATOR_LAMP = register("indicator_lamp", IndicatorLampBlock::new);
    public static final RegistryObject<Block> NUMERIC_DISPLAY = register("numeric_display", NumericDisplayBlock::new);
    public static final RegistryObject<Block> POWER_SENSOR = register("power_sensor", PowerSensorBlock::new);
    public static final RegistryObject<Block> RELAY_BOX = register("relay_box", RelayBoxBlock::new);
    public static final RegistryObject<Block> SIGNAL_CABLE = register("signal_cable", SignalCableBlock::new);
    public static final RegistryObject<Block> TEMPERATURE_SENSOR = register("temperature_sensor", TemperatureSensorBlock::new);
    public static final RegistryObject<Block> THRESHOLD_CONTROLLER = register("threshold_controller", ThresholdControllerBlock::new);
    public static final RegistryObject<Block> TIMER_UNIT = register("timer_unit", TimerUnitBlock::new);

    private static RegistryObject<Block> register(String name, java.util.function.Supplier<Block> block) {
        RegistryObject<Block> ret = ModRegistry.BLOCKS.register(name, block);
        ModRegistry.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties()));
        return ret;
    }

    public static void init() {}
}
