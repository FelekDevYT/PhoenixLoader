package dev.felek.phoenix.hooks;

import com.mojang.brigadier.CommandDispatcher;
import dev.felek.phoenix.Phoenix;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import java.lang.reflect.Field;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @className: CommandRegistrationHook
 * @author: Felek
 * @date: 27.07.2026 17:34
 */

public class CommandRegistrationHook {
    @Advice.OnMethodExit
    public static void onExit(@Advice.This Object commandsObj) {
        if (commandsObj instanceof Commands commands) {
            CommandDispatcher<CommandSourceStack> dispatcher = commands.getDispatcher();
            Phoenix.getManager().commandManager.registerCustomCommands(dispatcher);
        }
    }
}
