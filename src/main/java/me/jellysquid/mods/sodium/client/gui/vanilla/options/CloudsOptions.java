package me.jellysquid.mods.sodium.client.gui.vanilla.options;

import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions.GraphicsQuality;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public enum CloudsOptions implements IndexedOption {
    OFF(0, false, GraphicsQuality.DEFAULT, "options.off"),
    FAST(1, true, GraphicsQuality.FAST, "options.clouds.fast"),
    FANCY(2, true, GraphicsQuality.FANCY, "options.clouds.fancy");

    private final int index;
    private final boolean enabled;
    private final GraphicsQuality quality;
    private final Text text;
    CloudsOptions(int index, boolean enabled, GraphicsQuality quality, String translationKey){
        this.index = index;
        this.enabled = enabled;
        this.quality = quality;
        this.text = new TranslatableText(translationKey);
    }

    public GraphicsQuality getQuality() {
        return quality;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public int getIndex() {
        return index;
    }

    public Text getText() {
        return text;
    }

    public static CloudsOptions getOption(boolean enabled, GraphicsQuality quality){
        if(enabled){
            if(quality == GraphicsQuality.FANCY){
                return FANCY;
            }
            return FAST;
        }
        return OFF;
    }
}
