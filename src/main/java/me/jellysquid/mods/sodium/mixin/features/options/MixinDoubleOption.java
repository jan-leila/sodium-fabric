package me.jellysquid.mods.sodium.mixin.features.options;

import me.jellysquid.mods.sodium.client.SodiumClientMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.VideoOptionsScreen;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.options.GameOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BiConsumer;

// For reduce log while changing render distance or another double options
@Mixin(DoubleOption.class)
public abstract class MixinDoubleOption {

    @Shadow
    @Final
    private BiConsumer<GameOptions, Double> setter;

    @Inject(method = "set", at = @At("HEAD"), cancellable = true)
    public void onUpdateValue(GameOptions options, double value, CallbackInfo ci) {
        if (MinecraftClient.getInstance().currentScreen instanceof VideoOptionsScreen) {
            DoubleOption doubleOption = (DoubleOption) ((Object) this);
            SodiumClientMod.DOUBLE_OPTIONS_RUNNABLE.put(doubleOption, () -> this.setter.accept(options, value));
            SodiumClientMod.DOUBLE_OPTIONS_CHANGES.put(doubleOption, value);
            ci.cancel();
        }
    }

    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    public void onGetValue(GameOptions options, CallbackInfoReturnable<Double> cir) {
        DoubleOption doubleOption = (DoubleOption) ((Object) this);
        if (MinecraftClient.getInstance().currentScreen instanceof VideoOptionsScreen
                && SodiumClientMod.DOUBLE_OPTIONS_CHANGES.containsKey(doubleOption)) {
            cir.setReturnValue(SodiumClientMod.DOUBLE_OPTIONS_CHANGES.get(doubleOption));
        }
    }
}
