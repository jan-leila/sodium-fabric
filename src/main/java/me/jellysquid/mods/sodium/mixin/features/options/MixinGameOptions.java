package me.jellysquid.mods.sodium.mixin.features.options;

import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import net.minecraft.client.options.CloudRenderMode;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.GraphicsMode;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameOptions.class)
public abstract class MixinGameOptions {
    @Shadow
    public int viewDistance;

    @Shadow
    public GraphicsMode graphicsMode;

    /**
     * @author JellySquid
     * @reason Make the cloud render mode user-configurable
     */
    @Overwrite
    public CloudRenderMode getCloudRenderMode() {
        SodiumGameOptions options = SodiumClientMod.options();

        if (this.viewDistance < 4 || !options.quality.enableClouds) {
            return CloudRenderMode.OFF;
        }

        return options.quality.cloudQuality.isFancy(this.graphicsMode) ? CloudRenderMode.FANCY : CloudRenderMode.FAST;
    }

    private static float parseFloat(String string) {
        if (string.equals("true")) {
            return 1.0F;
        }
        if (string.equals("false")) {
            return 0.0F;
        }
        return Float.parseFloat(string);
    }

    @Redirect(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/options/GameOptions;parseFloat(Ljava/lang/String;)F", ordinal = 2))
    float maxGamma(String string){
        return MathHelper.clamp(parseFloat(string), 0 , 5);
    }
}
