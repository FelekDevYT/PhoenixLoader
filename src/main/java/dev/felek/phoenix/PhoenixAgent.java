package dev.felek.phoenix;

import dev.felek.phoenix.hooks.MinecraftHook;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

/**
 * @className: PhoenixAgent
 * @author: Felek
 * @date: 25.07.2026 13:48
 */

public class PhoenixAgent {
    public static void premain(String agentArgs, Instrumentation I) {
        System.out.println("Loading agent...");

        new AgentBuilder.Default()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .type(ElementMatchers.named("net.minecraft.client.Minecraft"))
                .transform((b, t, c, mod, prDom) -> b.method(ElementMatchers.named("run")).intercept(MethodDelegation.to(MinecraftHook.class))
                ).installOn(I);

        System.out.println("All hooks has been installed.");
    }
}
