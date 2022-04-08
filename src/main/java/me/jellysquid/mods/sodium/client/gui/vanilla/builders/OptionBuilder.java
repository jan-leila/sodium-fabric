package me.jellysquid.mods.sodium.client.gui.vanilla.builders;

import me.jellysquid.mods.sodium.client.gui.options.OptionFlag;
import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;
import net.minecraft.text.Text;
import org.apache.commons.lang3.Validate;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class OptionBuilder<P, S, T, U> {

    private String key;
    private final S storage;
    private BiFunction<U, T, Text> textGetter;
    private Function<S, U> getter;
    private BiConsumer<S, U> setter;
    private final Set<OptionFlag> localFlags = EnumSet.noneOf(OptionFlag.class);

    abstract P self();

    OptionBuilder(OptionStorage<S> storage){
        this.storage = storage.getData();
    }

    public P setKey(String key){
        this.key = key;
        return self();
    }

    private static Set<OptionFlag> flags;
    static {
        getFlags();
    }
    public static Set<OptionFlag> getFlags(){
        Set<OptionFlag> oldFlags = flags;
        flags = EnumSet.noneOf(OptionFlag.class);
        return oldFlags;
    }

    public P setGetter(Function<S, U> getter){
        this.getter = getter;
        return self();
    }

    public P setSetter(BiConsumer<S, U> setter){
        this.setter = setter;
        return self();
    }

    public P setTextGetter(Function<U, Text> textGetter){
        this.textGetter = (gameOptions, self) -> textGetter.apply(gameOptions);
        return self();
    }

    public P setTextGetter(BiFunction<U, T, Text> textGetter){
        this.textGetter = textGetter;
        return self();
    }

    public P flag(OptionFlag flag){
        localFlags.add(flag);
        return self();
    }

    String getKey(){
        Validate.notNull(key, "Key must be specified");
        return key;
    }

    S getStorage() {
        return storage;
    }

    Function<S, U> getGetter(){
        Validate.notNull(getter, "Getter must be specified");
        if(!localFlags.isEmpty()){
            return (value) -> {
                flags.addAll(localFlags);
                return getter.apply(value);
            };
        }
        return getter;
    }

    BiConsumer<S, U> getSetter() {
        Validate.notNull(setter, "Setter must be specified");
        return setter;
    }

    BiFunction<S, T, Text> getTextGetter(){
        Validate.notNull(textGetter, "Text Getter must be specified");
        Function<S, U> getter = getGetter();
        return (gameOptions, option) -> {
            U value = getter.apply(gameOptions);
            return textGetter.apply(value, option);
        };
    }

    public abstract T build();
}
