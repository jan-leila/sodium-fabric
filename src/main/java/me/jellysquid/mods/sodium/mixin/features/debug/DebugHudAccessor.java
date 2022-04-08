package me.jellysquid.mods.sodium.mixin.features.debug;

import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(DebugHud.class)
public interface DebugHudAccessor {
    @Invoker("getRightText")
    List<String> invokeGetRightText();
}
