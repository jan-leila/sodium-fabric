package me.jellysquid.mods.sodium.client.gui.vanilla.builders;

import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.options.OptionFlag;
import me.jellysquid.mods.sodium.client.gui.options.storage.SodiumOptionsStorage;
import net.minecraft.text.Text;
import org.apache.commons.lang3.Validate;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class OptionBuilder<P, T, U> {

    static final SodiumOptionsStorage sodiumOpts = new SodiumOptionsStorage();

    private String key;
    private String text;
    private BiFunction<U, T, Text> textGetter;
    private Function<SodiumGameOptions, U> getter;
    private BiConsumer<SodiumGameOptions, U> setter;
    private final Set<OptionFlag> localFlags = EnumSet.noneOf(OptionFlag.class);

    abstract P self();

    public P setKey(String key){
        this.key = key;
        return self();
    }

    public P setText(String text){
        this.text = text;
        return  self();
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

    public P setGetter(Function<SodiumGameOptions, U> getter){
        this.getter = getter;
        return self();
    }

    public P setSetter(BiConsumer<SodiumGameOptions, U> setter){
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

    String getText(){
        return text;
    }

    Function<SodiumGameOptions, U> getGetter(){
        Validate.notNull(getter, "Getter must be specified");
        if(!localFlags.isEmpty()){
            return (value) -> {
                flags.addAll(localFlags);
                return getter.apply(value);
            };
        }
        return getter;
    }

    BiConsumer<SodiumGameOptions, U> getSetter() {
        Validate.notNull(setter, "Setter must be specified");
        return setter;
    }

    BiFunction<SodiumGameOptions, T, Text> getTextGetter(){
        Validate.notNull(textGetter, "Text Getter must be specified");
        Function<SodiumGameOptions, U> getter = getGetter();
        return (gameOptions, option) -> {
            U value = getter.apply(gameOptions);
            return textGetter.apply(value, option);
        };
    }

    public abstract T build();
}
