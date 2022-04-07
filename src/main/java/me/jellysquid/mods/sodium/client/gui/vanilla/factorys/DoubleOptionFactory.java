package me.jellysquid.mods.sodium.client.gui.vanilla.factorys;

import me.jellysquid.mods.sodium.client.gui.vanilla.DoubleOptionBuilder;
import net.minecraft.client.options.GameOptions;

public class DoubleOptionFactory extends BuilderFactory<DoubleOptionBuilder<?>>  {
    public DoubleOptionBuilder<GameOptions> vanilla(){
        return new DoubleOptionBuilder<>(vanillaOpts);
    }

    public DoubleOptionBuilder<GameOptions> sodium(){
        return new DoubleOptionBuilder<>(vanillaOpts);
    }
}