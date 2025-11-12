package net.mehvahdjukaar.modelfix.mixins;

import net.mehvahdjukaar.modelfix.hooks.ModelFixHook;
import net.minecraft.client.render.model.block.BlockElement;
import net.minecraft.client.render.model.block.ItemModelGenerator;
import net.minecraft.client.render.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemModelGenerator.class)
public abstract class ItemModelMixin {
    @Inject(method = "addSideElements", at = @At("RETURN"))
    public void increaseSide(TextureAtlasSprite sprite, String texture, int tintIndex, CallbackInfoReturnable<List<BlockElement>> cir) {
        ModelFixHook.enlargeFaces(cir);
    }

    /**
     * @author MehVahdJukaar
     * @reason fixing item models gaps
     */
    @Overwrite
    private void expandSpan(List<ItemModelGenerator.Span> spans, ItemModelGenerator.Facing facing, int x, int y) {
        ModelFixHook.expandSpan(spans, facing, x, y);
    }
}
