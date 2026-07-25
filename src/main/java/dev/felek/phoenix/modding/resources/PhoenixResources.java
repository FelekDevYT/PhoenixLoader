package dev.felek.phoenix.modding.resources;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.IoSupplier;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @className: PhoenixResources
 * @author: Felek
 * @date: 25.07.2026 20:01
 */

public class PhoenixResources implements PackResources {
    public static final PhoenixResources INSTANCE = new PhoenixResources();

    private final Map<Identifier, byte[]> atlas = new HashMap<>();
    private final Set<String> namespaces = new HashSet<>();

    public void addAsset(Identifier id, byte[] data) {
        atlas.put(id, data);
        namespaces.add(id.getNamespace());
    }

    @Override
    public IoSupplier<InputStream> getRootResource(String... path) {
        return null;
    }

    @Override
    public IoSupplier<InputStream> getResource(PackType type, Identifier location) {
        byte[] data = atlas.get(location);
        if (data != null) {
            return () -> new ByteArrayInputStream(data);
        }
        return null;
    }

    @Override
    public void listResources(PackType type, String namespace, String directory, ResourceOutput output) {
        atlas.forEach((id, data) -> {
            if (id.getNamespace().equals(namespace) && id.getPath().startsWith(directory)) {
                output.accept(id, () -> new ByteArrayInputStream(data));
            }
        });
    }

    @Override
    public Set<String> getNamespaces(PackType type) {
        return namespaces;
    }

    @Override
    public <T> T getMetadataSection(MetadataSectionType<T> metadataSerializer) throws IOException {
        return null;
    }

    @Override
    public PackLocationInfo location() {
        return new PackLocationInfo("phoenix_virtual_pack", Component.literal("Phoenix Virtual Pack"), PackSource.BUILT_IN, Optional.empty());
    }

    @Override
    public void close() {
    }
}
