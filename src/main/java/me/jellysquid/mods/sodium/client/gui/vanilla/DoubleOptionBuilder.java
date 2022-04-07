package me.jellysquid.mods.sodium.client.gui.vanilla;

import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.util.math.MathHelper;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class DoubleOptionBuilder<S> extends OptionBuilder<DoubleOptionBuilder<S>, S, DoubleOption, Double> {

    private double min = 0;
    private double max = 10;
    private float step = 1;

    public DoubleOptionBuilder(OptionStorage<S> storage) {
        super(storage);
    }

    public DoubleOptionBuilder<S> setMin(double min){
        this.min = min;
        return this;
    }

    public DoubleOptionBuilder<S> setMax(double max){
        this.max = max;
        return this;
    }

    public DoubleOptionBuilder<S> setStep(float step){
        this.step = step;
        return this;
    }

    @Override
    DoubleOptionBuilder<S> self() {
        return this;
    }

    public DoubleOption build() {
        S options = getStorage();
        Function<S, Double> getter = getGetter();
        BiConsumer<S, Double> setter = getSetter();
        double min = this.min;
        double max = this.max;
        return new DoubleOption(getKey(), min, max, step,
                (gameOptions) -> getter.apply(options),
                (gameOptions, value) -> setter.accept(options, MathHelper.clamp(value, min, max)),
                (gameOptions, self) -> self.getDisplayPrefix().append(getTextGetter().apply(options, self)));
    }
}
