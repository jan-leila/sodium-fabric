package me.jellysquid.mods.sodium.client.gui;

import me.jellysquid.mods.sodium.client.gui.options.OptionFlag;
import me.jellysquid.mods.sodium.client.gui.vanilla.builders.CycleOptionBuilder;
import me.jellysquid.mods.sodium.client.gui.vanilla.options.CloudsOptions;
import me.jellysquid.mods.sodium.client.gui.vanilla.options.SmoothLightingOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.options.*;

public class VanillaOptions {

    public static final Option SMOOTH_LIGHTING = new CycleOptionBuilder<SmoothLightingOptions>()
            .setKey("options.ao")
            .setOptions(SmoothLightingOptions.values())
            .setGetter((options) -> SmoothLightingOptions.getOption(options.quality.smoothLighting))
            .setSetter(((options, smoothLightingOption) -> options.quality.smoothLighting = smoothLightingOption.getSmoothLighting()))
            .setTextGetter((value, self) -> value.getText())
            .flag(OptionFlag.REQUIRES_RENDERER_RELOAD)
            .build();

    public static final Option CLOUDS = new CycleOptionBuilder<CloudsOptions>()
            .setKey("options.renderClouds")
            .setOptions(CloudsOptions.values())
            .setGetter((options) -> CloudsOptions.getOption(options.quality.enableClouds, options.quality.cloudQuality))
            .setSetter((options, value) -> {
                options.quality.enableClouds = value.isEnabled();
                options.quality.cloudQuality = value.getQuality();

                if (MinecraftClient.isFabulousGraphicsOrBetter()) {
                    Framebuffer framebuffer = MinecraftClient.getInstance().worldRenderer.getCloudsFramebuffer();
                    if (framebuffer != null) {
                        framebuffer.clear(MinecraftClient.IS_SYSTEM_MAC);
                    }
                }
            })
            .setTextGetter((value, self) -> value.getText())
            .build();
}
