package me.jellysquid.mods.sodium.client.gui.vanilla.factorys;

import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.vanilla.CycleOptionBuilder;
import me.jellysquid.mods.sodium.client.gui.vanilla.options.IndexedOption;
import net.minecraft.client.options.GameOptions;

public class CycleOptionFactory<T extends IndexedOption> extends BuilderFactory<CycleOptionBuilder<?, T>> {
    public CycleOptionBuilder<GameOptions, T> vanilla(){
        return new CycleOptionBuilder<>(vanillaOpts);
    }

    public CycleOptionBuilder<SodiumGameOptions, T> sodium(){
        return new CycleOptionBuilder<>(sodiumOpts);
    }
}
