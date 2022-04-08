package me.jellysquid.mods.sodium.client.gui.vanilla.builders;

import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;
import net.minecraft.client.options.BooleanOption;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class BooleanOptionBuilder<S> extends OptionBuilder<BooleanOptionBuilder<S>, S, BooleanOption, Boolean> {

    public BooleanOptionBuilder(OptionStorage<S> storage) {
        super(storage);
    }

    @Override
    BooleanOptionBuilder<S> self() {
        return this;
    }

    @Override
    public BooleanOption build() {
        S options = getStorage();
        Function<S, Boolean> getter = getGetter();
        BiConsumer<S, Boolean> setter = getSetter();

        return new BooleanOption(
                getKey(),
                (gameOptions) -> getter.apply(options),
                (gameOptions, value) -> setter.accept(options, value)
        );
    }
}
