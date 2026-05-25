package gay.themattabase.idletickarmorstands.neoforge;

import gay.themattabase.idletickarmorstands.client.IdleTickArmorStandsConfigScreen;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class ClientSetup {
    public static void init(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class,
                (mc, parent) -> new IdleTickArmorStandsConfigScreen(parent));
    }
}
