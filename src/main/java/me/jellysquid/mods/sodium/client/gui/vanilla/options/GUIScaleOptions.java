package me.jellysquid.mods.sodium.client.gui.vanilla.options;

public enum GUIScaleOptions implements IndexedOption {
    AUTO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4);

    private final int index;
    GUIScaleOptions(int index){
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }

    static final GUIScaleOptions[] options = GUIScaleOptions.values();
    public static GUIScaleOptions getOption(int scale){
        return options[scale];
    }
}
