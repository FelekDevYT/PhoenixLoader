package dev.felek.phoenix.modding.managers.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: CommandManager
 * @author: Felek
 * @date: 27.07.2026 17:22
 */

public class CommandManager {
    private final Map<String, CommandExecutor> customCommands = new HashMap<>();

    public void registerCommand(String name, CommandExecutor executor) {
        customCommands.put(name, executor);
    }

    public void registerCustomCommands(CommandDispatcher dispatcher) {
        for (Map.Entry<String, CommandExecutor> entry : customCommands.entrySet()) {
            String name = entry.getKey();
            CommandExecutor executor = entry.getValue();

            LiteralArgumentBuilder<CommandSourceStack> builder = LiteralArgumentBuilder.literal(name);
            builder.executes(context -> {
                executor.execute(context.getSource(), new String[0]);
                return 1;
            });
            dispatcher.register(builder);
        }
    }
}
