package dev.felek.phoenix.modding.managers.mixin;

import dev.felek.phoenix.hooks.MixinDispatcher;
import dev.felek.phoenix.modding.mixin.Inject;
import dev.felek.phoenix.modding.mixin.Mixin;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Handler;
import java.util.stream.Stream;

/**
 * @className: MixinManager
 * @author: Felek
 * @date: 02.08.2026 19:11
 */

public class MixinManager {
    private static Map<String, List<MixinAction>> injections = new HashMap<>();
    private static Set<String> hooked = new HashSet<>();
    private static Instrumentation I;

    public static void init(Instrumentation I) {
        MixinManager.I = I;
    }

    public static void registerMixins() {
        try {
            for (Class<?> main : findMixinClasses()) {
                Mixin m = main.getAnnotation(Mixin.class);
                Class<?> target = m.target();

                for (Method meth : main.getDeclaredMethods()) {
                    Inject inject = meth.getAnnotation(Inject.class);
                    if (inject == null)return;

                    if (!Modifier.isStatic(meth.getModifiers()) || meth.getParameterCount() != 0) {
                        throw new RuntimeException("@Inject method " + main.getName() + "#" + meth.getName() + " must be public static with no parameters");
                    }
                    meth.setAccessible(true);

                    registerMixin(target, inject.method(), () -> {
                        try {
                            meth.invoke(null);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });

                    System.out.println("Registered mixin for: " + main.getName());
                }
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerMixin(Class<?> target, String methodName, MixinAction action) {
        String key = target.getName() + "#" + methodName;
        injections.computeIfAbsent(key, k -> new ArrayList<>()).add(action);
        if (!hooked.add(key))
            return;

        new AgentBuilder.Default()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .type(ElementMatchers.is(target))
                .transform((b, t, cl, m, pd) -> b
                        .visit(Advice.to(MixinDispatcher.class).on(ElementMatchers.named(methodName)))
                ).installOn(I);
    }

    public static List<MixinAction> getInjections(String key) {
        return injections.get(key);
    }

    public static List<Class<?>> findMixinClasses() throws Exception {
        List<Class<?>> found = new ArrayList<>();
        URI loc = MixinManager.class.getProtectionDomain().getCodeSource().getLocation().toURI();
        File f = new File(loc);
        List<String> names = new ArrayList<>();
        if (f.isDirectory()) {
            Path root = f.toPath();
            try (Stream<Path> walker = Files.walk(root)) {
                walker.filter(p -> p.toString().endsWith(".class")).forEach(p -> names.add(root.relativize(p).toString().replace(File.separatorChar, '.').replace(".class", "")));
            }
        } else {
            try (JarFile jar = new JarFile(f)) {
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    names.add(entry.getName().replace('/', '.').substring(0, entry.getName().length() - 6));
                }
            }
        }

        for (String cn : names) {
            try {
                Class<?> clazz = Class.forName(cn, false, MixinManager.class.getClassLoader());
                if (clazz.isAnnotationPresent(Mixin.class)) {
                    found.add(clazz);
                }
            } catch (Exception _) {
            }
        }
        return found;
    }
}
