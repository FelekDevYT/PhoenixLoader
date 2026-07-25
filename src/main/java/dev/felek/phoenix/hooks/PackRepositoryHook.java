package dev.felek.phoenix.hooks;

import dev.felek.phoenix.modding.resources.PhoenixResources;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.minecraft.server.packs.PackResources;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @className: PackRepositoryHook
 * @author: Felek
 * @date: 25.07.2026 20:13
 */

public class PackRepositoryHook {
    public static List<PackResources> intercept(@SuperCall Callable<List<PackResources>> original) throws Exception {
        List<PackResources> packs = new ArrayList<>(original.call());
        packs.add(PhoenixResources.INSTANCE);
        return packs;
    }
}
