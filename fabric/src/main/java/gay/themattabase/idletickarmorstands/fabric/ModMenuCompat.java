package gay.themattabase.idletickarmorstands.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import gay.themattabase.idletickarmorstands.client.IdleTickArmorStandsConfigScreen;

public class ModMenuCompat implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return IdleTickArmorStandsConfigScreen::new;
    }
}
