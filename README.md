# LazyStands

**Skips 95% of armor stand ticks for stationary stands. A simple, configurable server-side performance optimization.**

Armor stands are one of the most common entities on decorated servers, yet every single one ticks every single game tick — even when they're just standing still doing nothing. LazyStands fixes that by intelligently skipping ticks for armor stands that don't need them, dramatically reducing server load with zero visual impact.

## How It Works

LazyStands uses a mixin to intercept the tick method on armor stands. When an armor stand is stationary (on the ground or has NoGravity), the mod skips the vast majority of its ticks — by default, only processing 1 out of every 20 ticks. This means **95% fewer ticks** for every idle armor stand on your server.

The mod is smart about when *not* to skip:
- Stands that are riding something or being ridden still tick normally
- Stands that are on fire still tick normally
- Stands that have been knocked or pushed still tick normally

This ensures armor stands always behave correctly in dynamic situations while saving performance when they're just sitting there looking pretty.

## Features

- **Massive tick reduction** — skips up to 95% of ticks for stationary armor stands
- **Zero visual impact** — players won't notice any difference
- **Fully configurable** — toggle every condition and adjust the skip interval
- **In-game config screen** — change settings without editing files (ModMenu on Fabric, mod config on NeoForge)
- **Server-side optimization** — works on dedicated servers and singleplayer
- **Multiloader** — supports both NeoForge and Fabric

## Configuration

All settings are stored in `config/lazystands.json` and can be edited in-game through the config screen.

| Setting | Default | Description |
|---|---|---|
| `enabled` | `true` | Master toggle for the mod |
| `skipInterval` | `20` | Only tick once every N ticks (2–100). Default 20 = 95% skip rate |
| **Skip Conditions** | | *At least one must be true to skip* |
| `skipWhenOnGround` | `true` | Skip ticks when the armor stand is on the ground |
| `skipWhenNoGravity` | `true` | Skip ticks when the armor stand has NoGravity |
| **Blacklist Conditions** | | *Prevents skipping when true* |
| `dontSkipIfPassenger` | `true` | Don't skip if the stand is riding another entity |
| `dontSkipIfHasPassengers` | `true` | Don't skip if something is riding the stand |
| `dontSkipIfOnFire` | `true` | Don't skip if the stand is on fire |
| `dontSkipIfHurtMarked` | `true` | Don't skip if the stand has been knocked/pushed |

## Installation

1. Install [NeoForge](https://neoforged.net/) or [Fabric](https://fabricmc.net/) (+ [Fabric API](https://modrinth.com/mod/fabric-api))
2. Drop the jar into your `mods` folder
3. That's it — no dependencies, no setup

On Fabric, [ModMenu](https://modrinth.com/mod/modmenu) is optional but recommended for in-game config access.

## FAQ

**Does this affect armor stand animations or poses?**
No. The armor stand still renders normally on the client. This only affects server-side ticking.

**Will this break armor stands used in map builds or datapacks?**
Stationary armor stands used purely for decoration will work perfectly. If you have armor stands involved in complex redstone or command block setups, you can lower the `skipInterval` or toggle specific conditions in the config.

**Does this work on servers?**
Yes — this is primarily a server-side optimization. Install it on the server and it works for all players.

**How much performance does this actually save?**
It depends on how many armor stands are loaded. A server with hundreds of decorative armor stands in loaded chunks will see a meaningful reduction in entity tick time. The more stands you have, the bigger the impact.

## License

This mod is licensed under the [MIT License](LICENSE).
