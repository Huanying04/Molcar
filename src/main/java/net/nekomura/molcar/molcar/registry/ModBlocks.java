package net.nekomura.molcar.molcar.registry;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.nekomura.molcar.molcar.Molcar;
import net.nekomura.molcar.molcar.blocks.LettuceBlock;

public class ModBlocks {

    public static final Block LETTUCE = new LettuceBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));

    public static void register() {
        Registry.register(Registry.BLOCK, new Identifier(Molcar.MOD_ID, "lettuce"), LETTUCE);

        BlockRenderLayerMap.INSTANCE.putBlock(LETTUCE, RenderLayer.getCutout());
    }
}
