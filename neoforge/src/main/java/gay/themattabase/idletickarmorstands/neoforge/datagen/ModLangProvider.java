package gay.themattabase.idletickarmorstands.neoforge.datagen;

import gay.themattabase.idletickarmorstands.IdleTickArmorStands;
import net.creeperhost.polylib.datagen.PolyLibLangProvider;
import net.minecraft.data.PackOutput;

public class ModLangProvider extends PolyLibLangProvider {
    public ModLangProvider(PackOutput output) {
        super(output, IdleTickArmorStands.MOD_ID);
    }

    @Override
    protected void addModTranslations() {
    }
}
