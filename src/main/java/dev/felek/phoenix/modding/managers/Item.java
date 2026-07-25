package dev.felek.phoenix.modding.managers;

/**
 * @className: Item
 * @author: Felek
 * @date: 25.07.2026 18:47
 */

public class Item {
    private String name;
    private net.minecraft.world.item.Item item;

    public Item(String name, net.minecraft.world.item.Item item) {
        this.name = name;
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public net.minecraft.world.item.Item getItem() {
        return item;
    }

    public void setItem(net.minecraft.world.item.Item item) {
        this.item = item;
    }
}
