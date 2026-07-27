package dev.felek.phoenix.modding.managers.command;

import net.minecraft.commands.CommandSourceStack;

/**
 * @className: CommandExecutor
 * @author: Felek
 * @date: 27.07.2026 17:29
 */

@FunctionalInterface
public interface CommandExecutor {
    void execute(CommandSourceStack stack, String[] args);
}
