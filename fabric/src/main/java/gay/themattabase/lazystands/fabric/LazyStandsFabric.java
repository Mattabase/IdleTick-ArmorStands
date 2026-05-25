package gay.themattabase.lazystands.fabric;

import gay.themattabase.lazystands.config.LazyStandsConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class LazyStandsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        LazyStandsConfig.load(FabricLoader.getInstance().getConfigDir());
    }
}
