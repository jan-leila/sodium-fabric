package me.jellysquid.mods.sodium.client.gui;

import me.jellysquid.mods.sodium.client.gui.options.OptionFlag;
import me.jellysquid.mods.sodium.client.gui.vanilla.factorys.BooleanOptionFactory;
import me.jellysquid.mods.sodium.client.gui.vanilla.factorys.CycleOptionFactory;
import me.jellysquid.mods.sodium.client.gui.vanilla.factorys.DoubleOptionFactory;
import me.jellysquid.mods.sodium.client.gui.vanilla.options.GUIScaleOptions;
import me.jellysquid.mods.sodium.client.gui.vanilla.options.GraphicsOptions;
import me.jellysquid.mods.sodium.client.gui.vanilla.options.SmoothLightingOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.*;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.EnumMap;
import java.util.Map;

public class VanillaOptions {

    public static final DoubleOption BIOME_BLEND_RADIUS = new DoubleOptionFactory().vanilla()
            .setKey("options.biomeBlendRadius")
            .setTextGetter((value) -> new TranslatableText("options.biomeBlendRadius." + value.intValue()))
            .setMax(7)
            .setSetter((gameOptions, value) -> gameOptions.biomeBlendRadius = value.intValue())
            .setGetter((gameOptions) -> (double) gameOptions.biomeBlendRadius)
            .flag(OptionFlag.REQUIRES_RENDERER_RELOAD)
            .build();

    public static final DoubleOption RENDER_DISTANCE = new DoubleOptionFactory().vanilla()
            .setKey("options.renderDistance")
            .setMin(2)
            .setMax(16)
            .setTextGetter((value) -> new TranslatableText("options.chunks", value.intValue()))
            .setSetter((gameOptions, value) -> gameOptions.viewDistance = value.intValue())
            .setGetter((gameOptions) -> (double) gameOptions.viewDistance)
            .build();

    private static final Map<GraphicsMode, Text> GRAPHICS_TOOLTIP = new EnumMap<>(GraphicsMode.class);
    static {
        GRAPHICS_TOOLTIP.put(GraphicsMode.FAST, new TranslatableText("options.graphics.fast.tooltip"));
        GRAPHICS_TOOLTIP.put(GraphicsMode.FANCY, new TranslatableText("options.graphics.fancy.tooltip"));
        GRAPHICS_TOOLTIP.put(GraphicsMode.FABULOUS, new TranslatableText("options.graphics.fabulous.tooltip", (new TranslatableText("options.graphics.fabulous")).formatted(Formatting.ITALIC)));
    }

    public static final Option GRAPHICS_OPTION = new CycleOptionFactory<GraphicsOptions>().vanilla()
            .setKey("options.graphics")
            .setOptions(GraphicsOptions.values())
            .setGetter((options) -> GraphicsOptions.getOption(options.graphicsMode))
            .setSetter(((options, graphicsOptions) -> options.graphicsMode = graphicsOptions.getGraphicsMode()))
            .setTextGetter((value, self) -> {
                GraphicsMode graphicsMode = value.getGraphicsMode();
                Text tooltip = GRAPHICS_TOOLTIP.get(graphicsMode);
                self.setTooltip(MinecraftClient.getInstance().textRenderer.wrapLines(tooltip, 200));

                MutableText text = new TranslatableText(graphicsMode.getTranslationKey());
                if(graphicsMode == GraphicsMode.FABULOUS){
                    text = text.formatted(Formatting.ITALIC);
                }
                return self.getDisplayPrefix().append(text);
            })
            .flag(OptionFlag.REQUIRES_RENDERER_RELOAD)
            .build();

    public static final Option SMOOTH_LIGHTING = new CycleOptionFactory<SmoothLightingOptions>().sodium()
            .setKey("options.ao")
            .setOptions(SmoothLightingOptions.values())
            .setGetter((options) -> SmoothLightingOptions.getOption(options.quality.smoothLighting))
            .setSetter(((options, smoothLightingOption) -> options.quality.smoothLighting = smoothLightingOption.getSmoothLighting()))
            .setTextGetter((value, self) -> self.getDisplayPrefix().append(value.getText()))
            .flag(OptionFlag.REQUIRES_RENDERER_RELOAD)
            .build();

    public static final Option FRAMERATE_LIMIT = new DoubleOptionFactory().vanilla()
            .setKey("options.framerateLimit")
            .setMin(10)
            .setMax(260)
            .setStep(10)
            .setGetter((options) -> (double) options.maxFps)
            .setSetter((options, value) -> {
                options.maxFps = value.intValue();
                MinecraftClient.getInstance().getWindow().setFramerateLimit(options.maxFps);
            })
            .setTextGetter((value, self) -> value == self.getMax()? new TranslatableText("options.framerateLimit.max") : new TranslatableText("options.framerate", value.intValue()))
            .build();

    public static final Option VSYNC = new BooleanOptionFactory().vanilla()
            .setKey("options.vsync")
            .setGetter((options) -> options.enableVsync)
            .setSetter((options, value) -> {
                options.enableVsync = value;
                MinecraftClient.getInstance().getWindow().setVsync(options.enableVsync);
            })
            .build();

    public static final Option VIEW_BOBBING = new BooleanOptionFactory().vanilla()
            .setKey("options.viewBobbing")
            .setGetter((options) -> options.bobView)
            .setSetter((options, value) -> options.bobView = value)
            .build();


    public static final Option GUI_SCALE = new CycleOptionFactory<GUIScaleOptions>().vanilla()
            .setKey("options.guiScale")
            .setOptions(GUIScaleOptions.values())
            .setSetter((options, value) -> options.guiScale = Integer.remainderUnsigned(value.getIndex(), MinecraftClient.getInstance().getWindow().calculateScaleFactor(0, MinecraftClient.getInstance().forcesUnicodeFont()) + 1))
            .setGetter((options) -> GUIScaleOptions.getOption(options.guiScale))
            .setTextGetter((value, self) -> self.getDisplayPrefix().append(value.getIndex() == 0 ? new TranslatableText("options.guiScale.auto") : new LiteralText(Integer.toString(value.getIndex()))))
            .build();
}
