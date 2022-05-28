package me.jellysquid.mods.sodium.client.gui.vanilla.options;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public enum EntityCulling implements IndexedOption {

    ON(0, true, "options.on"),
    OFF(1, false, "options.off");

    private final int index;
    private final boolean enabled;
    private final Text text;
    EntityCulling(int index, boolean enabled, String translationKey){
        this.index = index;
        this.enabled = enabled;
        this.text = new TranslatableText(translationKey);
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

    public static EntityCulling getOption(boolean value){
        if(value){
            return ON;
        }
        return OFF;
    }
}
