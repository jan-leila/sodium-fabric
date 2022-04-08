package me.jellysquid.mods.sodium.client.gui.vanilla.factorys;

import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.vanilla.builders.DoubleOptionBuilder;
import net.minecraft.client.options.GameOptions;

public class DoubleOptionFactory extends BuilderFactory<DoubleOptionBuilder<?>>  {
    public DoubleOptionBuilder<GameOptions> vanilla(){
        return new DoubleOptionBuilder<>(vanillaOpts);
    }

    public DoubleOptionBuilder<SodiumGameOptions> sodium(){
        return new DoubleOptionBuilder<>(sodiumOpts);
    }
}