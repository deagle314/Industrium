package com.industrium.core;

import com.industrium.core.common.network.NetworkManager;
import com.industrium.core.common.power.network.PowerNetwork;
import com.industrium.core.common.network.heat.HeatNetwork;
import com.industrium.core.common.network.rotation.RotationNetwork;
import com.industrium.core.api.network.IPowerNode;
import com.industrium.core.api.network.IHeatNode;
import com.industrium.core.api.network.IFluidNode;
import com.industrium.core.api.network.IRotationNode;
import com.industrium.core.common.registry.ModRegistry;
import com.industrium.core.common.util.RegistryValidator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Industrium.MOD_ID)
public class Industrium {

    public static final String MOD_ID = "industrium";
    
    public static final NetworkManager<IPowerNode, PowerNetwork> POWER_NETWORK_MANAGER = 
        new NetworkManager<>(PowerNetwork::new);
    
    public static final NetworkManager<IHeatNode, HeatNetwork> HEAT_NETWORK_MANAGER = 
        new NetworkManager<>(HeatNetwork::new);

    public static final NetworkManager<IFluidNode, FluidNetwork> FLUID_NETWORK_MANAGER = 
        new NetworkManager<>(FluidNetwork::new);

    public static final NetworkManager<IRotationNode, RotationNetwork> ROTATION_NETWORK_MANAGER = 
        new NetworkManager<>(RotationNetwork::new);

    public Industrium() {
        ModRegistry.register(
            FMLJavaModLoadingContext.get().getModEventBus()
        );

        // Run validation suite
        RegistryValidator.validate();
    }
}
