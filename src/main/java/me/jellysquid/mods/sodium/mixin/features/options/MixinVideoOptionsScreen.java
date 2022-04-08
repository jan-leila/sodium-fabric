package me.jellysquid.mods.sodium.mixin.features.options;

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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Set;

@Mixin(VideoOptionsScreen.class)
public class MixinVideoOptionsScreen extends GameOptionsScreen {

    public MixinVideoOptionsScreen(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }

    private static final Option[] OPTIONS = {
            VanillaOptions.GRAPHICS_OPTION,
            VanillaOptions.RENDER_DISTANCE,
            VanillaOptions.SMOOTH_LIGHTING,
            VanillaOptions.FRAMERATE_LIMIT,
            VanillaOptions.VSYNC,
            VanillaOptions.VIEW_BOBBING,
//            VanillaOptions.GUI_SCALE,
//            VanillaOptions.ATTACK_INDICATOR,
//            VanillaOptions.GAMMA,
//            VanillaOptions.CLOUDS,
//            VanillaOptions.FULLSCREEN,
//            VanillaOptions.PARTICLES,
//            VanillaOptions.MIPMAP_LEVELS,
//            VanillaOptions.ENTITY_SHADOWS,
//            VanillaOptions.ENTITY_DISTANCE_SCALING
    };

//    @Redirect(method = "init", at=@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonListWidget;addSingleOptionEntry(Lnet/minecraft/client/options/Option;)I", ordinal = 0))
//    private int fullScreenOption(ButtonListWidget list, Option old_option){
//        return list.addSingleOptionEntry(fullScreenOption);
//    }

    @Redirect(method = "init", at=@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonListWidget;addSingleOptionEntry(Lnet/minecraft/client/options/Option;)I", ordinal = 1))
    private int biomesBlendOption(ButtonListWidget list, Option old_option){
        return list.addSingleOptionEntry(VanillaOptions.BIOME_BLEND_RADIUS);
    }

    @Redirect(method = "init", at=@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonListWidget;addAll([Lnet/minecraft/client/options/Option;)V"))
    private void optionsSwap(ButtonListWidget list, Option[] old_options){
        list.addAll(OPTIONS);
    }

    @Override
    public void removed(){
//        final HashSet<OptionStorage<?>> dirtyStorages = new HashSet<>();
        MinecraftClient client = MinecraftClient.getInstance();

        Set<OptionFlag> flags = OptionBuilder.getFlags();

        System.out.println("closing");

        if(flags.contains(OptionFlag.REQUIRES_RENDERER_RELOAD)){
            System.out.println("reload render");
            client.worldRenderer.reload();
        }

        if(flags.contains(OptionFlag.REQUIRES_ASSET_RELOAD)) {
            client.resetMipmapLevels(client.options.mipmapLevels);
            client.reloadResourcesConcurrently();
        }

//        for (OptionStorage<?> storage : dirtyStorages) {
//            storage.save();
//        }
    }
}
