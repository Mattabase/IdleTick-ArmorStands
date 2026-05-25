package gay.themattabase.lazystands.neoforge;

import gay.themattabase.lazystands.client.LazyStandsConfigScreen;
import gay.themattabase.lazystands.config.LazyStandsConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod("lazystands")
public class LazyStandsNeoForge {
    public LazyStandsNeoForge(IEventBus eventBus, ModContainer container) {
        LazyStandsConfig.load(FMLPaths.CONFIGDIR.get());

        if (FMLLoader.getCurrent().getDist().isClient()) {
            container.registerExtensionPoint(IConfigScreenFactory.class,
                    (mc, parent) -> new LazyStandsConfigScreen(parent));
        }
    }
}
