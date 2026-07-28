package dev.felek.phoenix.modding.managers.block;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: BlockManager
 * @author: Felek
 * @date: 26.07.2026 14:07
 */

public class BlockManager {
    private List<Block> registeredBlocks;

    public BlockManager() {
        registeredBlocks = new ArrayList<>();
    }

    public void registerBlock(Block block) {
        registeredBlocks.add(block);
    }

    public void registerAll() {
        for (Block b : registeredBlocks) {
            Identifier id = Identifier.fromNamespaceAndPath(b.getName().split(":")[0], b.getName().split(":")[1]);

            Registry.register(
                    BuiltInRegistries.BLOCK,
                    id,
                    b.getBlock()
            );

            for (BlockState state : b.getBlock().getStateDefinition().getPossibleStates()) {
                net.minecraft.world.level.block.Block.BLOCK_STATE_REGISTRY.add(state);
                state.initCache();
            }

            Registry.register(
                    BuiltInRegistries.ITEM,
                    id,
                    b.getItem()
            );
        }
    }
}
