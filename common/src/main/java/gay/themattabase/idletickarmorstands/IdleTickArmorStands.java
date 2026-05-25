package gay.themattabase.idletickarmorstands;

import net.creeperhost.polylib.data.lang.PolyLangContributions;

public final class IdleTickArmorStands {

    public static final String MOD_ID = "idletick_armor_stands";

    private IdleTickArmorStands() {}

    public static void init() {
        registerTranslations();
    }

    private static void registerTranslations() {
        contribute("idletick_armor_stands.config.title", "IdleTick - Armor Stands Configuration");

        contribute("idletick_armor_stands.config.enabled", "Enabled");
        contribute("idletick_armor_stands.config.enabled.tooltip", "Master switch — disables all optimisations when off.");

        contribute("idletick_armor_stands.config.skip_when_on_ground", "Skip When On Ground");
        contribute("idletick_armor_stands.config.skip_when_on_ground.tooltip",
                "Allow skipping ticks for armor stands resting on the ground.");
        contribute("idletick_armor_stands.config.skip_when_no_gravity", "Skip When NoGravity");
        contribute("idletick_armor_stands.config.skip_when_no_gravity.tooltip",
                "Allow skipping ticks for armor stands with NoGravity set.");

        contribute("idletick_armor_stands.config.dont_skip_if_passenger", "Don't Skip If Passenger");
        contribute("idletick_armor_stands.config.dont_skip_if_passenger.tooltip",
                "Never skip ticks for armor stands riding another entity.");
        contribute("idletick_armor_stands.config.dont_skip_if_has_passengers", "Don't Skip If Has Riders");
        contribute("idletick_armor_stands.config.dont_skip_if_has_passengers.tooltip",
                "Never skip ticks for armor stands carrying passengers.");
        contribute("idletick_armor_stands.config.dont_skip_if_on_fire", "Don't Skip If On Fire");
        contribute("idletick_armor_stands.config.dont_skip_if_on_fire.tooltip",
                "Never skip ticks for burning armor stands.");
        contribute("idletick_armor_stands.config.dont_skip_if_hurt_marked", "Don't Skip If Hurt Marked");
        contribute("idletick_armor_stands.config.dont_skip_if_hurt_marked.tooltip",
                "Never skip ticks for armor stands flagged for knockback/damage sync.");
        contribute("idletick_armor_stands.config.dont_skip_if_moving", "Don't Skip If Moving");
        contribute("idletick_armor_stands.config.dont_skip_if_moving.tooltip",
                "Never skip ticks for armor stands currently in motion.");

        contribute("idletick_armor_stands.config.skip_interval", "Skip Interval");
        contribute("idletick_armor_stands.config.skip_interval.tooltip",
                "Full-tick every Nth tick (2–100). Lower = more responsive, higher = more savings.");

        contribute("gamerule.idletick_armor_stands.enabled", "Enable armor stand tick optimisation");
        contribute("gamerule.idletick_armor_stands.skip_interval", "Ticks between full armor stand ticks (2–100)");
    }

    private static void contribute(String key, String english) {
        PolyLangContributions.contribute(key, english);
    }
}
