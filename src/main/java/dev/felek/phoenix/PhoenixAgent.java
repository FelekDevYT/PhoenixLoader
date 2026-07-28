package dev.felek.phoenix;

import dev.felek.phoenix.hooks.BootstrapHook;
import dev.felek.phoenix.hooks.CommandRegistrationHook;
import dev.felek.phoenix.hooks.MinecraftHook;
import dev.felek.phoenix.hooks.PackRepositoryHook;
import dev.felek.phoenix.hooks.TickHook;
import dev.felek.phoenix.hooks.event.BlockEventsHooks;
import dev.felek.phoenix.hooks.event.EntityEventsHooks;
import dev.felek.phoenix.hooks.event.ItemEventsHooks;
import dev.felek.phoenix.hooks.event.PlayerEventsHooks;
import dev.felek.phoenix.hooks.event.ServerEventsHooks;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.lang.instrument.Instrumentation;

/**
 * @className: PhoenixAgent
 * @author: Felek
 * @date: 25.07.2026 13:48
 */

public class PhoenixAgent {
    public static void premain(String agentArgs, Instrumentation I) throws ScriptException, IOException {
        System.out.println("Loading agent...");

        System.out.println("Finding possible script engines...");
        for (var eng : new ScriptEngineManager().getEngineFactories()) {
            System.out.println("[ FOUND ] - " + eng.getEngineName() + " v" + eng.getEngineVersion() + " Lv" + eng.getLanguageVersion());
        }

        System.out.println("Initializing mods...");
        Phoenix.getManager().loadMods();
        System.out.println("Mods initialized.");

        new AgentBuilder.Default()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .type(ElementMatchers.named("net.minecraft.client.Minecraft"))
                .transform((b, t, c, mod, prDom) -> b
                        .method(ElementMatchers.named("run")).intercept(MethodDelegation.to(MinecraftHook.class))
                        .method(ElementMatchers.named("tick")).intercept(MethodDelegation.to(TickHook.class))
                ).installOn(I);
        new AgentBuilder.Default()
                .type(ElementMatchers.named("net.minecraft.server.Bootstrap"))
                .transform((b, t, c, mod, prDom) -> b
                        .method(ElementMatchers.named("bootStrap")).intercept(MethodDelegation.to(BootstrapHook.class))
                ).installOn(I);
        new AgentBuilder.Default()
                .type(ElementMatchers.named("net.minecraft.server.packs.repository.PackRepository"))
                .transform((b, t, c, mod, prDom) -> b
                        .method(ElementMatchers.named("openAllSelected")).intercept(MethodDelegation.to(PackRepositoryHook.class))
                ).installOn(I);
        new AgentBuilder.Default()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .type(ElementMatchers.named("net.minecraft.commands.Commands"))
                .transform((b, t, c, mod, prDom) -> b
                        .visit(Advice.to(CommandRegistrationHook.class).on(ElementMatchers.isConstructor()))
                ).installOn(I);

        new AgentBuilder.Default()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .type(ElementMatchers.named("net.minecraft.client.Minecraft"))
                .transform((b, t, c, mod, prDom) -> b
                        .method(ElementMatchers.named("run")).intercept(MethodDelegation.to(MinecraftHook.class))
                        .method(ElementMatchers.named("tick")).intercept(MethodDelegation.to(TickHook.class))
                )
                .type(ElementMatchers.named("net.minecraft.server.Bootstrap"))
                .transform((b, t, c, mod, prDom) -> b
                        .method(ElementMatchers.named("bootStrap")).intercept(MethodDelegation.to(BootstrapHook.class))
                )
                .type(ElementMatchers.named("net.minecraft.server.packs.repository.PackRepository"))
                .transform((b, t, c, mod, prDom) -> b
                        .method(ElementMatchers.named("openAllSelected")).intercept(MethodDelegation.to(PackRepositoryHook.class))
                )
                .type(ElementMatchers.named("net.minecraft.commands.Commands"))
                .transform((b, t, c, mod, prDom) -> b
                        .visit(Advice.to(CommandRegistrationHook.class).on(ElementMatchers.isConstructor()))
                )
                .type(ElementMatchers.named("net.minecraft.server.MinecraftServer"))
                .transform((b, t, c, mod, prDom) -> b
                        .visit(Advice.to(ServerEventsHooks.ServerStartingHook.class).on(ElementMatchers.named("runServer")))
                        .visit(Advice.to(ServerEventsHooks.ServerStoppingHook.class).on(ElementMatchers.named("stopServer")))
                        .visit(Advice.to(ServerEventsHooks.ServerTickHook.class).on(ElementMatchers.named("tickServer")))
                )
                .type(ElementMatchers.named("net.minecraft.server.players.PlayerList"))
                .transform((b, t, c, mod, prDom) -> b
                        .visit(Advice.to(PlayerEventsHooks.PlayerJoinHook.class).on(ElementMatchers.named("placeNewPlayer")))
                        .visit(Advice.to(PlayerEventsHooks.PlayerLeaveHook.class).on(ElementMatchers.named("remove")))
                )
                .type(ElementMatchers.named("net.minecraft.server.level.ServerPlayerGameMode"))
                .transform((b, t, c, mod, prDom) -> b
                        .visit(Advice.to(BlockEventsHooks.BlockBreakHook.class).on(ElementMatchers.named("destroyBlock")))
                )
                .type(ElementMatchers.named("net.minecraft.world.entity.LivingEntity"))
                .transform((b, t, c, mod, prDom) -> b
                        .visit(Advice.to(EntityEventsHooks.EntityDamageHook.class).on(ElementMatchers.named("hurt")))
                        .visit(Advice.to(EntityEventsHooks.EntityDeathHook.class).on(ElementMatchers.named("die")))
                )
                .type(ElementMatchers.named("net.minecraft.world.item.ItemStack"))
                .transform((b, t, c, mod, prDom) -> b
                        .visit(Advice.to(ItemEventsHooks.ItemUseHook.class).on(ElementMatchers.named("use")))
                )
                .type(ElementMatchers.named("net.minecraft.server.network.ServerGamePacketListenerImpl"))
                .transform((b, t, c, mod, prDom) -> b
                        .visit(Advice.to(PlayerEventsHooks.PlayerChatHook.class).on(ElementMatchers.named("broadcastChatMessage")))
                )
                .installOn(I);

        System.out.println("All hooks has been installed.");
    }
}
