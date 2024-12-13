package Net.LumiDerp.Tileman;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

@Mod.EventBusSubscriber(modid = "tileman", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT) // Ensure correct mod ID
public class HighlightHandler {

    @SubscribeEvent
    public static void onRenderWorld(RenderLevelStageEvent event) {
        Minecraft mc = Minecraft.getInstance();

        // Only render during the AFTER_SOLID_BLOCKS stage
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS) return;

        // Ensure we're in-game and the player exists
        if (mc.level == null || mc.player == null) return;

        // Check if the player is holding a wooden shovel
        if (mc.player.getMainHandItem().getItem() == Items.WOODEN_SHOVEL) {
            // Get the player's current targeted block
            HitResult hit = mc.hitResult;

            // Check if the ray-traced hit result is a block
            if (hit != null && hit.getType() == HitResult.Type.BLOCK) {
                BlockHitResult hitResult = (BlockHitResult) hit; // Cast to BlockHitResult

                // Get the block position
                BlockPos blockPos = hitResult.getBlockPos();

                // Render a highlight around the block
                Matrix4f poseStack = event.getProjectionMatrix();
                LevelRenderer.renderLineBox(
                        poseStack,
                        mc.renderBuffers().bufferSource().getBuffer(RenderType.lines()),
                        blockPos.getX() - 0.01, blockPos.getY() - 0.01, blockPos.getZ() - 0.01,
                        blockPos.getX() + 1.01, blockPos.getY() + 1.01, blockPos.getZ() + 1.01,
                        1.0F, 1.0F, 0.0F, 1.0F // Color: yellow with full opacity
                );
            }
        }
    }
}
