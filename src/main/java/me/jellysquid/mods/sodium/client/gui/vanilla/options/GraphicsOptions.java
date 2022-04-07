package me.jellysquid.mods.sodium.client.gui.vanilla.options;

import net.minecraft.client.options.GraphicsMode;

import java.util.EnumMap;
import java.util.Map;

public enum GraphicsOptions implements IndexedOption {
    FAST(GraphicsMode.FAST, 0),
    FANCY(GraphicsMode.FANCY, 1),
    FABULOUS(GraphicsMode.FABULOUS, 2);

    private final GraphicsMode graphicsMode;
    private final int index;
    GraphicsOptions(GraphicsMode graphicsMode, int index){
        this.graphicsMode = graphicsMode;
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }

    public GraphicsMode getGraphicsMode() {
        return graphicsMode;
    }

    private static final Map<GraphicsMode, GraphicsOptions> MODE_MAP = new EnumMap<>(GraphicsMode.class);
    static {
        for(GraphicsOptions option : GraphicsOptions.values()){
            MODE_MAP.put(option.getGraphicsMode(), option);
        }
    }

    public static GraphicsOptions getOption(GraphicsMode mode){
        return MODE_MAP.get(mode);
    }
}
