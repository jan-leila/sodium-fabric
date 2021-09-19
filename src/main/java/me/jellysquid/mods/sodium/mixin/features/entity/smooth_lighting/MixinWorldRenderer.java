package me.jellysquid.mods.sodium.mixin.features.entity.smooth_lighting;

import me.jellysquid.mods.sodium.client.render.SodiumWorldRenderer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Objects;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V"))
    void doNothing(Profiler profiler, String location) {
        if (!Objects.equals(location, "blockentities") || !SodiumWorldRenderer.getInstance().getUseEntityCulling()) {
            profiler.swap(location);
        }
    }
}

