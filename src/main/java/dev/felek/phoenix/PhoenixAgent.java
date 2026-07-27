package dev.felek.phoenix;

import dev.felek.phoenix.hooks.BootstrapHook;
import dev.felek.phoenix.hooks.MinecraftHook;
import dev.felek.phoenix.hooks.PackRepositoryHook;
import dev.felek.phoenix.hooks.TickHook;
import net.bytebuddy.agent.builder.AgentBuilder;
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

        System.out.println("All hooks has been installed.");
    }
}
