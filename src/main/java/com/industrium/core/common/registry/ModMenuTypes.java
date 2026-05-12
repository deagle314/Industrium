package com.industrium.core.common.registry;

import com.industrium.core.common.menu.BoilerMenu;
import com.industrium.core.common.menu.CrusherMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final RegistryObject<MenuType<BoilerMenu>> BOILER = register("boiler", BoilerMenu::new);
    public static final RegistryObject<MenuType<CrusherMenu>> CRUSHER = register("crusher", CrusherMenu::new);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String name, IContainerFactory<T> factory) {
        return ModRegistry.MENU_TYPES.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void init() {}
}
