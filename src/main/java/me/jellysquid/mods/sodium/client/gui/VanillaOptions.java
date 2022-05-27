package me.jellysquid.mods.sodium.client.gui;

import me.jellysquid.mods.sodium.client.gui.options.OptionFlag;
import me.jellysquid.mods.sodium.client.gui.vanilla.builders.CycleOptionBuilder;
import me.jellysquid.mods.sodium.client.gui.vanilla.options.CloudsOptions;
import me.jellysquid.mods.sodium.client.gui.vanilla.options.SmoothLightingOptions;
import net.minecraft.client.options.*;

import java.util.HashSet;
import java.util.Set;

public class VanillaOptions {

    public static boolean inRun = false;

    // For reduce log while changing render distance or another double options
    private static final Set<Runnable> DOUBLE_OPTIONS_RUNNABLE = new HashSet<>();

    public static void clearSettingsChanges(){
        DOUBLE_OPTIONS_RUNNABLE.clear();
    }

    public static void applySettingsChanges(){
        DOUBLE_OPTIONS_RUNNABLE.forEach(Runnable::run);
        clearSettingsChanges();
    }

    public static void addSettingsChange(Runnable apply){
        DOUBLE_OPTIONS_RUNNABLE.add(apply);
    }

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
            })
            .flag(OptionFlag.REQUIRES_CLOUD_RELOAD)
            .setTextGetter((value, self) -> value.getText())
            .build();
}
