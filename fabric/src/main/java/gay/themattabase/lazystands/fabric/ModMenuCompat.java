package gay.themattabase.lazystands.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import gay.themattabase.lazystands.client.LazyStandsConfigScreen;

public class ModMenuCompat implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return LazyStandsConfigScreen::new;
    }
}
