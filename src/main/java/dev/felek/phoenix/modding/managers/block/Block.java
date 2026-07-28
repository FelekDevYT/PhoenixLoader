package dev.felek.phoenix.modding.managers.block;

import net.minecraft.world.item.BlockItem;

/**
 * @className: Block
 * @author: Felek
 * @date: 26.07.2026 14:05
 */

public class Block {
    private String name;
    private net.minecraft.world.level.block.Block block;
    private BlockItem item;

    public Block(String name, net.minecraft.world.level.block.Block block, BlockItem item) {
        this.name = name;
        this.block = block;
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public net.minecraft.world.level.block.Block getBlock() {
        return block;
    }

    public void setBlock(net.minecraft.world.level.block.Block block) {
        this.block = block;
    }

    public BlockItem getItem() {
        return item;
    }

    public void setItem(BlockItem item) {
        this.item = item;
    }
}
