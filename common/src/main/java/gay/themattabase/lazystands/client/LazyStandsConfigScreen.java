package gay.themattabase.lazystands.client;

import gay.themattabase.lazystands.config.LazyStandsConfig;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class LazyStandsConfigScreen extends Screen {

    private final Screen parent;

    public LazyStandsConfigScreen(Screen parent) {
        super(Component.literal("LazyStands Configuration"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        LazyStandsConfig cfg = LazyStandsConfig.get();
        LinearLayout layout = LinearLayout.vertical().spacing(2);

        layout.addChild(CycleButton.onOffBuilder(cfg.enabled)
                .create(0, 0, 200, 20, Component.literal("LazyStands Enabled"),
                        (btn, val) -> cfg.enabled = val));

        layout.addChild(CycleButton.onOffBuilder(cfg.skipWhenOnGround)
                .create(0, 0, 200, 20, Component.literal("Skip When On Ground"),
                        (btn, val) -> cfg.skipWhenOnGround = val));

        layout.addChild(CycleButton.onOffBuilder(cfg.skipWhenNoGravity)
                .create(0, 0, 200, 20, Component.literal("Skip When NoGravity"),
                        (btn, val) -> cfg.skipWhenNoGravity = val));

        layout.addChild(CycleButton.onOffBuilder(cfg.dontSkipIfPassenger)
                .create(0, 0, 200, 20, Component.literal("Don't Skip If Passenger"),
                        (btn, val) -> cfg.dontSkipIfPassenger = val));

        layout.addChild(CycleButton.onOffBuilder(cfg.dontSkipIfHasPassengers)
                .create(0, 0, 200, 20, Component.literal("Don't Skip If Has Riders"),
                        (btn, val) -> cfg.dontSkipIfHasPassengers = val));

        layout.addChild(CycleButton.onOffBuilder(cfg.dontSkipIfOnFire)
                .create(0, 0, 200, 20, Component.literal("Don't Skip If On Fire"),
                        (btn, val) -> cfg.dontSkipIfOnFire = val));

        layout.addChild(CycleButton.onOffBuilder(cfg.dontSkipIfHurtMarked)
                .create(0, 0, 200, 20, Component.literal("Don't Skip If Hurt Marked"),
                        (btn, val) -> cfg.dontSkipIfHurtMarked = val));

        layout.addChild(Button.builder(
                Component.literal("Skip Interval: " + cfg.skipInterval + " ticks"),
                btn -> {
                    cfg.skipInterval = nextInterval(cfg.skipInterval);
                    btn.setMessage(Component.literal("Skip Interval: " + cfg.skipInterval + " ticks"));
                }).width(200).build());

        layout.addChild(Button.builder(CommonComponents.GUI_DONE, btn -> onClose())
                .width(200).build());

        layout.arrangeElements();
        layout.setPosition((this.width - 200) / 2, 40);
        layout.visitWidgets(this::addRenderableWidget);
    }

    @Override
    public void onClose() {
        LazyStandsConfig.save();
        this.minecraft.setScreen(this.parent);
    }

    private static int nextInterval(int current) {
        if (current < 10) return current + 1;
        if (current < 20) return current + 5;
        if (current < 50) return current + 10;
        if (current < 100) return 100;
        return 2; // wrap
    }
}
