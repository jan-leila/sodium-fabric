package me.jellysquid.mods.sodium.mixin.features.options;

import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.VanillaOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.VideoOptionsScreen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(OptionsScreen.class)
public class MixinOptionsScreen extends Screen {
    @Shadow
    @Final
    private Screen parent;

    protected MixinOptionsScreen(Text title) {
        super(title);
    }

    @Dynamic
    @Inject(method = "method_19828(Lnet/minecraft/client/gui/widget/ButtonWidget;)V", at = @At("HEAD"), cancellable = true)
    private void open(ButtonWidget widget, CallbackInfo ci) {
        if(this.client != null){
            if(SodiumClientMod.options().settings.forceVanillaSettings || VanillaOptions.inRun){
                this.client.openScreen(new VideoOptionsScreen(this, MinecraftClient.getInstance().options));
            }
            else {
                this.client.openScreen(new SodiumOptionsGUI(this));
            }

            ci.cancel();
        }
    }
}
