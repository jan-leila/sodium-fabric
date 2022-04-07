package me.jellysquid.mods.sodium.client.gui.vanilla.factorys;

import me.jellysquid.mods.sodium.client.gui.options.storage.MinecraftOptionsStorage;
import me.jellysquid.mods.sodium.client.gui.options.storage.SodiumOptionsStorage;

public abstract class BuilderFactory<V> {
    static final SodiumOptionsStorage sodiumOpts = new SodiumOptionsStorage();
    static final MinecraftOptionsStorage vanillaOpts = new MinecraftOptionsStorage();

    public abstract V vanilla();
    public abstract V sodium();
}
