package me.jellysquid.mods.sodium.client.gui.vanilla;

import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;
import me.jellysquid.mods.sodium.client.gui.vanilla.option.CyclingOption;
import me.jellysquid.mods.sodium.client.gui.vanilla.options.IndexedOption;
import org.apache.commons.lang3.Validate;

public class CycleOptionBuilder<S, V extends IndexedOption> extends OptionBuilder<CycleOptionBuilder<S, V>, S, CyclingOption<S, V>, V> {

    private V[] options;

    public CycleOptionBuilder(OptionStorage<S> storage) {
        super(storage);
    }

    @Override
    CycleOptionBuilder<S, V> self() {
        return this;
    }

    public CycleOptionBuilder<S, V> setOptions(V[] options){
        this.options = options;
        return self();
    }

    V[] getOptions(){
        Validate.notNull(options, "Options must be specified");
        return options;
    }

    @Override
    public CyclingOption<S, V> build() {
        return new CyclingOption<>(
                getKey(),
                getStorage(),
                getOptions(),
                getSetter(),
                getGetter(),
                getTextGetter()
        );
    }
}
