package me.jellysquid.mods.sodium.mixin.features.options;

import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.VanillaOptions;
import me.jellysquid.mods.sodium.client.gui.options.OptionFlag;
import me.jellysquid.mods.sodium.client.gui.vanilla.builders.OptionBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.VideoOptionsScreen;
import net.minecraft.client.gui.screen.options.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;
import java.util.Set;

@Mixin(VideoOptionsScreen.class)
public class MixinVideoOptionsScreen extends GameOptionsScreen {

    public MixinVideoOptionsScreen(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }

    private static final Option[] OPTIONS = {
            Option.GRAPHICS,
            Option.RENDER_DISTANCE,
            VanillaOptions.SMOOTH_LIGHTING,
            Option.FRAMERATE_LIMIT,
            Option.VSYNC,
            Option.VIEW_BOBBING,
            Option.GUI_SCALE,
            Option.ATTACK_INDICATOR,
            Option.GAMMA,
            VanillaOptions.CLOUDS,
            Option.FULLSCREEN,
            Option.PARTICLES,
            Option.MIPMAP_LEVELS,
            Option.ENTITY_SHADOWS,
            Option.ENTITY_DISTANCE_SCALING
    };

    @Redirect(method = "init", at=@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonListWidget;addAll([Lnet/minecraft/client/options/Option;)V"))
    private void optionsSwap(ButtonListWidget list, Option[] old_options){
        list.addAll(OPTIONS);
    }

    @Override
    public void removed(){
        MinecraftClient client = MinecraftClient.getInstance();

        Set<OptionFlag> flags = OptionBuilder.getFlags();
        if(flags.contains(OptionFlag.REQUIRES_RENDERER_RELOAD)){
            client.worldRenderer.reload();
        }

        try {
            SodiumClientMod.options().writeChanges();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_P && (modifiers & GLFW.GLFW_MOD_SHIFT) != 0) {
            SodiumClientMod.options().settings.forceVanillaSettings = false;
            try {
                SodiumClientMod.options().writeChanges();
            } catch (IOException e) {
                e.printStackTrace();
            }
            MinecraftClient.getInstance().openScreen(new SodiumOptionsGUI(this.parent));
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
