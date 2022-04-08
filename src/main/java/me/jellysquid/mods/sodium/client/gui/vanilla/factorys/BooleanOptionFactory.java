package me.jellysquid.mods.sodium.client.gui.vanilla.factorys;

import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.vanilla.builders.BooleanOptionBuilder;
import net.minecraft.client.options.GameOptions;

public class BooleanOptionFactory extends BuilderFactory<BooleanOptionBuilder<?>> {

    @Override
    public BooleanOptionBuilder<GameOptions> vanilla() {
        return new BooleanOptionBuilder<>(vanillaOpts);
    }

    @Override
    public BooleanOptionBuilder<SodiumGameOptions> sodium() {
        return new BooleanOptionBuilder<>(sodiumOpts);
    }
}
