package me.jellysquid.mods.sodium.mixin.features.debug;

import com.google.common.collect.Lists;
import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.render.SodiumWorldRenderer;
import me.jellysquid.mods.sodium.client.render.chunk.ChunkRenderBackend;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

@Mixin(DebugHud.class)
public abstract class MixinDebugHud {
    @Shadow
    private static long toMiB(long bytes) {
        throw new UnsupportedOperationException();
    }

    @Redirect(method = "renderRightText", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/DebugHud;getRightText()Ljava/util/List;"))
    private List<String> redirectRightTextEarly(DebugHud instance) {
        List<String> strings = ((DebugHudAccessor)instance).invokeGetRightText();
        strings.add("");
        strings.add("Sodium Speedrunning Build");
        strings.add(Formatting.UNDERLINE + getFormattedVersionText());
        strings.add("");

        for (int i = 0; i < strings.size(); i++) {
            String str = strings.get(i);

            if (str.startsWith("Allocated:")) {
                strings.add(i + 1, getNativeMemoryString());

                break;
            }
        }

        return strings;
    }

    private static String getFormattedVersionText() {
        String version = SodiumClientMod.getVersion();
        Formatting color;

        if (version.endsWith("-dirty")) {
            color = Formatting.RED;
        } else if (version.contains("+rev.")) {
            color = Formatting.LIGHT_PURPLE;
        } else {
            color = Formatting.GREEN;
        }

        return color + version;
    }

    private static String getNativeMemoryString() {
        return "Off-Heap: +" + toMiB(ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getUsed()) + "MB";
    }
}
