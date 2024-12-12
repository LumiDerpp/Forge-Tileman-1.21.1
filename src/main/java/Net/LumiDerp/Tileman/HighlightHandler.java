package Net.LumiDerp.Tileman;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.BlockHitResult; // Correct import for BlockHitResult
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent; // Correct import for RenderWorldLastEvent
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.renderer.RenderType; // Import RenderType

@Mod.EventBusSubscriber(modid = Tileman.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HighlightHandler {

    @SubscribeEvent
    public static void onRenderWorld(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getInstance();

        // Ensure we're in-game and the player exists
        if (mc.level == null || mc.player == null) return;

        // Check if the player is holding a wooden shovel
        if (mc.player.getMainHandItem().getItem() == Items.WOODEN_SHOVEL) {
            HitResult hitResult = mc.hitResult;

            // Check if the player is looking at a block
            if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHitResult = (BlockHitResult) hitResult; // Explicit cast
                BlockPos blockPos = blockHitResult.getBlockPos();
                PoseStack poseStack = event.getPoseStack();

                // Render a highlight around the block
                LevelRenderer.renderLineBox(poseStack, mc.renderBuffers().bufferSource().getBuffer(RenderType.lines()),
                        blockPos.getX() - 0.01, blockPos.getY() - 0.01, blockPos.getZ() - 0.01,
                        blockPos.getX() + 1.01, blockPos.getY() + 1.01, blockPos.getZ() + 1.01,
                        1.0F, 1.0F, 0.0F, 1.0F); // Color: yellow with full opacity
            }
        }
    }
}
