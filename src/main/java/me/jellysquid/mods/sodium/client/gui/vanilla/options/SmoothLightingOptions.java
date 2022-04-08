package me.jellysquid.mods.sodium.client.gui.vanilla.options;

import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions.LightingQuality;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.EnumMap;
import java.util.Map;

public enum SmoothLightingOptions implements IndexedOption {
    OFF(0, LightingQuality.OFF, "options.ao.off"),
    LOW(1, LightingQuality.LOW,  "options.ao.min"),
    HIGH(2, LightingQuality.HIGH, "options.ao.max");

    private final int index;
    private final LightingQuality option;
    private final Text text;
    SmoothLightingOptions(int index, LightingQuality option, String translationKey){
        this.index = index;
        this.option = option;
        this.text = new TranslatableText(translationKey);
    }

    @Override
    public int getIndex() {
        return index;
    }

    public Text getText() {
        return text;
    }

    public LightingQuality getSmoothLighting() {
        return option;
    }

    private static final Map<LightingQuality, SmoothLightingOptions> MODE_MAP = new EnumMap<>(LightingQuality.class);
    static {
        for(SmoothLightingOptions option : SmoothLightingOptions.values()){
            MODE_MAP.put(option.getSmoothLighting(), option);
        }
    }

    public static SmoothLightingOptions getOption(LightingQuality ao) {
        return MODE_MAP.get(ao);
    }
}
