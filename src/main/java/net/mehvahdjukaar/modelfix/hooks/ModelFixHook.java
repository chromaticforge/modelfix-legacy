package net.mehvahdjukaar.modelfix.hooks;

import net.minecraft.client.render.model.block.BlockElement;
import net.minecraft.client.render.model.block.ItemModelGenerator;
import net.minecraft.util.math.Direction;
import org.lwjgl.util.vector.Vector3f;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Set;

public class ModelFixHook {
    private static final float EXPANSION = 0.007F;
    private static final float RECESS = 0.008F;

    public static void enlargeFaces(CallbackInfoReturnable<List<BlockElement>> cir) {
        for (BlockElement e : cir.getReturnValue()) {
            Vector3f from = e.from;
            Vector3f to = e.to;

            Set<Direction> set = e.faces.keySet();
            if (set.size() == 1) {
                Direction dir = set.stream().findAny().get();
                switch (dir) {
                    case UP:
                        from.set(from.x - RECESS, from.y - EXPANSION, from.z - RECESS);
                        to.set(to.x + RECESS, to.y - EXPANSION, to.z + RECESS);
                        break;
                    case DOWN:
                        from.set(from.x - RECESS, from.y + EXPANSION, from.z - RECESS);
                        to.set(to.x + RECESS, to.y + EXPANSION, to.z + RECESS);
                        break;
                    case WEST:
                        from.set(from.x - EXPANSION, from.y + RECESS, from.z - RECESS);
                        to.set(to.x - EXPANSION, to.y - RECESS, to.z + RECESS);
                        break;
                    case EAST:
                        from.set(from.x + EXPANSION, from.y + RECESS, from.z - RECESS);
                        to.set(to.x + EXPANSION, to.y - RECESS, to.z + RECESS);
                        break;
                }
            }
        }
    }

    public static void expandSpan(List<ItemModelGenerator.Span> listSpans, ItemModelGenerator.Facing spanFacing, int pixelX, int pixelY) {
        int length;
        ItemModelGenerator.Span existingSpan = null;
        for (ItemModelGenerator.Span span2 : listSpans) {
            if (span2.getFacing() == spanFacing) {
                int i = spanFacing.isVertical() ? pixelY : pixelX;
                if (span2.getAnchor() != i) continue;
                if (span2.getMax() != (!spanFacing.isVertical() ? pixelY : pixelX) - 1)
                    continue;
                existingSpan = span2;
                break;
            }
        }


        length = spanFacing.isVertical() ? pixelX : pixelY;
        if (existingSpan == null) {
            int newStart = spanFacing.isVertical() ? pixelY : pixelX;
            listSpans.add(new ItemModelGenerator.Span(spanFacing, length, newStart));
        } else {
            existingSpan.expand(length);
        }
    }
}
