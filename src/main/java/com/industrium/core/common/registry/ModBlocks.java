package com.industrium.core.common.registry;

import com.industrium.core.common.fluid.block.BoilerBlock;
import com.industrium.core.common.fluid.block.CheckValveBlock;
import com.industrium.core.common.fluid.block.CondenserBlock;
import com.industrium.core.common.fluid.block.CopperPipeBlock;
import com.industrium.core.common.fluid.block.ElectricPumpBlock;
import com.industrium.core.common.fluid.block.FlowMeterBlock;
import com.industrium.core.common.fluid.block.FluidTankBlock;
import com.industrium.core.common.fluid.block.ManualValveBlock;
import com.industrium.core.common.fluid.block.SteelPipeBlock;
import com.industrium.core.common.heat.block.CoalBurnerBlock;
import com.industrium.core.common.heat.block.HeatMeterBlock;
import com.industrium.core.common.heat.block.HeatPipeBlock;
import com.industrium.core.common.heat.block.InsulatedHeatPipeBlock;
import com.industrium.core.common.heat.block.RadiatorBlock;
import com.industrium.core.common.heat.block.SteamBoilerBlock;
import com.industrium.core.common.heat.block.ThermalBatteryBlock;
import com.industrium.core.common.heat.block.WaterCoolerBlock;
import com.industrium.core.common.info.block.CentralConsoleBlock;
import com.industrium.core.common.info.block.EmergencyStopSwitchBlock;
import com.industrium.core.common.info.block.IndicatorLampBlock;
import com.industrium.core.common.info.block.NumericDisplayBlock;
import com.industrium.core.common.info.block.PowerSensorBlock;
import com.industrium.core.common.info.block.RelayBoxBlock;
import com.industrium.core.common.info.block.SignalCableBlock;
import com.industrium.core.common.info.block.TemperatureSensorBlock;
import com.industrium.core.common.info.block.ThresholdControllerBlock;
import com.industrium.core.common.info.block.TimerUnitBlock;
import com.industrium.core.common.integration.block.HeatExchangerBlock;
import com.industrium.core.common.integration.block.MotorPumpBlock;
import com.industrium.core.common.integration.block.SmartBreakerBlock;
import com.industrium.core.common.integration.block.SteamTurbineBlock;
import com.industrium.core.common.logistics.block.ConveyorBeltBlock;
import com.industrium.core.common.logistics.block.CrateBlock;
import com.industrium.core.common.logistics.block.FastConveyorBeltBlock;
import com.industrium.core.common.logistics.block.LoaderBlock;
import com.industrium.core.common.logistics.block.MergerBlock;
import com.industrium.core.common.logistics.block.SmartSorterBlock;
import com.industrium.core.common.logistics.block.SplitterBlock;
import com.industrium.core.common.logistics.block.UnloaderBlock;
import com.industrium.core.common.logistics.block.VerticalChuteBlock;
import com.industrium.core.common.logistics.block.WarehouseBinBlock;
import com.industrium.core.common.machine.block.ElectricFurnaceBlock;
import com.industrium.core.common.power.block.BatteryBoxBlock;
import com.industrium.core.common.power.block.CoalGeneratorBlock;
import com.industrium.core.common.power.block.PowerCableBlock;
import com.industrium.core.common.rotation.block.BeltPulleyBlock;
import com.industrium.core.common.rotation.block.ChainDriveBlock;
import com.industrium.core.common.rotation.block.ClutchBlock;
import com.industrium.core.common.rotation.block.CrusherBlock;
import com.industrium.core.common.rotation.block.FlywheelBlock;
import com.industrium.core.common.rotation.block.GearboxBlock;
import com.industrium.core.common.rotation.block.MechanicalPumpBlock;
import com.industrium.core.common.rotation.block.ShaftBlock;
import com.industrium.core.common.rotation.block.SteamEngineBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    // Power
    public static final RegistryObject<Block> COAL_GENERATOR = ModRegistry.BLOCKS.register("coal_generator", CoalGeneratorBlock::new);
    public static final RegistryObject<Block> BATTERY_BOX = ModRegistry.BLOCKS.register("battery_box", BatteryBoxBlock::new);
    public static final RegistryObject<Block> POWER_CABLE = ModRegistry.BLOCKS.register("power_cable", PowerCableBlock::new);

    // Machine
    public static final RegistryObject<Block> ELECTRIC_FURNACE = ModRegistry.BLOCKS.register("electric_furnace", ElectricFurnaceBlock::new);

    // Fluid
    public static final RegistryObject<Block> BOILER = ModRegistry.BLOCKS.register("boiler", BoilerBlock::new);
    public static final RegistryObject<Block> CHECK_VALVE = ModRegistry.BLOCKS.register("check_valve", CheckValveBlock::new);
    public static final RegistryObject<Block> CONDENSER = ModRegistry.BLOCKS.register("condenser", CondenserBlock::new);
    public static final RegistryObject<Block> COPPER_PIPE = ModRegistry.BLOCKS.register("copper_pipe", CopperPipeBlock::new);
    public static final RegistryObject<Block> ELECTRIC_PUMP = ModRegistry.BLOCKS.register("electric_pump", ElectricPumpBlock::new);
    public static final RegistryObject<Block> FLOW_METER = ModRegistry.BLOCKS.register("flow_meter", FlowMeterBlock::new);
    public static final RegistryObject<Block> FLUID_TANK = ModRegistry.BLOCKS.register("fluid_tank", FluidTankBlock::new);
    public static final RegistryObject<Block> MANUAL_VALVE = ModRegistry.BLOCKS.register("manual_valve", ManualValveBlock::new);
    public static final RegistryObject<Block> STEEL_PIPE = ModRegistry.BLOCKS.register("steel_pipe", SteelPipeBlock::new);

    // Heat
    public static final RegistryObject<Block> COAL_BURNER = ModRegistry.BLOCKS.register("coal_burner", CoalBurnerBlock::new);
    public static final RegistryObject<Block> ELECTRIC_HEATER = ModRegistry.BLOCKS.register("electric_heater", com.industrium.core.common.heat.block.ElectricHeaterBlock::new);
    public static final RegistryObject<Block> HEAT_METER = ModRegistry.BLOCKS.register("heat_meter", HeatMeterBlock::new);
    public static final RegistryObject<Block> HEAT_PIPE = ModRegistry.BLOCKS.register("heat_pipe", HeatPipeBlock::new);
    public static final RegistryObject<Block> INSULATED_HEAT_PIPE = ModRegistry.BLOCKS.register("insulated_heat_pipe", InsulatedHeatPipeBlock::new);
    public static final RegistryObject<Block> RADIATOR = ModRegistry.BLOCKS.register("radiator", RadiatorBlock::new);
    public static final RegistryObject<Block> STEAM_BOILER = ModRegistry.BLOCKS.register("steam_boiler", SteamBoilerBlock::new);
    public static final RegistryObject<Block> THERMAL_BATTERY = ModRegistry.BLOCKS.register("thermal_battery", ThermalBatteryBlock::new);
    public static final RegistryObject<Block> WATER_COOLER = ModRegistry.BLOCKS.register("water_cooler", WaterCoolerBlock::new);

    // Logistics
    public static final RegistryObject<Block> CONVEYOR_BELT = ModRegistry.BLOCKS.register("conveyor_belt", ConveyorBeltBlock::new);
    public static final RegistryObject<Block> CRATE = ModRegistry.BLOCKS.register("crate", CrateBlock::new);
    public static final RegistryObject<Block> FAST_CONVEYOR_BELT = ModRegistry.BLOCKS.register("fast_conveyor_belt", FastConveyorBeltBlock::new);
    public static final RegistryObject<Block> LOADER = ModRegistry.BLOCKS.register("loader", LoaderBlock::new);
    public static final RegistryObject<Block> MERGER = ModRegistry.BLOCKS.register("merger", MergerBlock::new);
    public static final RegistryObject<Block> SMART_SORTER = ModRegistry.BLOCKS.register("smart_sorter", SmartSorterBlock::new);
    public static final RegistryObject<Block> SPLITTER = ModRegistry.BLOCKS.register("splitter", SplitterBlock::new);
    public static final RegistryObject<Block> UNLOADER = ModRegistry.BLOCKS.register("unloader", UnloaderBlock::new);
    public static final RegistryObject<Block> VERTICAL_CHUTE = ModRegistry.BLOCKS.register("vertical_chute", VerticalChuteBlock::new);
    public static final RegistryObject<Block> WAREHOUSE_BIN = ModRegistry.BLOCKS.register("warehouse_bin", WarehouseBinBlock::new);

    // Rotation
    public static final RegistryObject<Block> BELT_PULLEY = ModRegistry.BLOCKS.register("belt_pulley", BeltPulleyBlock::new);
    public static final RegistryObject<Block> CHAIN_DRIVE = ModRegistry.BLOCKS.register("chain_drive", ChainDriveBlock::new);
    public static final RegistryObject<Block> CLUTCH = ModRegistry.BLOCKS.register("clutch", ClutchBlock::new);
    public static final RegistryObject<Block> CRUSHER = ModRegistry.BLOCKS.register("crusher", CrusherBlock::new);
    public static final RegistryObject<Block> ELECTRIC_MOTOR = ModRegistry.BLOCKS.register("electric_motor", com.industrium.core.common.rotation.block.ElectricMotorBlock::new);
    public static final RegistryObject<Block> FLYWHEEL = ModRegistry.BLOCKS.register("flywheel", FlywheelBlock::new);
    public static final RegistryObject<Block> GEARBOX = ModRegistry.BLOCKS.register("gearbox", GearboxBlock::new);
    public static final RegistryObject<Block> MECHANICAL_PUMP = ModRegistry.BLOCKS.register("mechanical_pump", MechanicalPumpBlock::new);
    public static final RegistryObject<Block> SHAFT = ModRegistry.BLOCKS.register("shaft", ShaftBlock::new);
    public static final RegistryObject<Block> STEAM_ENGINE = ModRegistry.BLOCKS.register("steam_engine", SteamEngineBlock::new);

    // Info
    public static final RegistryObject<Block> CENTRAL_CONSOLE = ModRegistry.BLOCKS.register("central_console", CentralConsoleBlock::new);
    public static final RegistryObject<Block> EMERGENCY_STOP_SWITCH = ModRegistry.BLOCKS.register("emergency_stop_switch", EmergencyStopSwitchBlock::new);
    public static final RegistryObject<Block> INDICATOR_LAMP = ModRegistry.BLOCKS.register("indicator_lamp", IndicatorLampBlock::new);
    public static final RegistryObject<Block> NUMERIC_DISPLAY = ModRegistry.BLOCKS.register("numeric_display", NumericDisplayBlock::new);
    public static final RegistryObject<Block> POWER_SENSOR = ModRegistry.BLOCKS.register("power_sensor", PowerSensorBlock::new);
    public static final RegistryObject<Block> RELAY_BOX = ModRegistry.BLOCKS.register("relay_box", RelayBoxBlock::new);
    public static final RegistryObject<Block> SIGNAL_CABLE = ModRegistry.BLOCKS.register("signal_cable", SignalCableBlock::new);
    public static final RegistryObject<Block> TEMPERATURE_SENSOR = ModRegistry.BLOCKS.register("temperature_sensor", TemperatureSensorBlock::new);
    public static final RegistryObject<Block> THRESHOLD_CONTROLLER = ModRegistry.BLOCKS.register("threshold_controller", ThresholdControllerBlock::new);
    public static final RegistryObject<Block> TIMER_UNIT = ModRegistry.BLOCKS.register("timer_unit", TimerUnitBlock::new);

    // Integration
    public static final RegistryObject<Block> INTEGRATION_ELECTRIC_HEATER = ModRegistry.BLOCKS.register("integration_electric_heater", com.industrium.core.common.integration.block.ElectricHeaterBlock::new);
    public static final RegistryObject<Block> INTEGRATION_ELECTRIC_MOTOR = ModRegistry.BLOCKS.register("integration_electric_motor", com.industrium.core.common.integration.block.ElectricMotorBlock::new);
    public static final RegistryObject<Block> HEAT_EXCHANGER = ModRegistry.BLOCKS.register("heat_exchanger", HeatExchangerBlock::new);
    public static final RegistryObject<Block> MOTOR_PUMP = ModRegistry.BLOCKS.register("motor_pump", MotorPumpBlock::new);
    public static final RegistryObject<Block> SMART_BREAKER = ModRegistry.BLOCKS.register("smart_breaker", SmartBreakerBlock::new);
    public static final RegistryObject<Block> STEAM_TURBINE = ModRegistry.BLOCKS.register("steam_turbine", SteamTurbineBlock::new);

    public static void init() {}
}
