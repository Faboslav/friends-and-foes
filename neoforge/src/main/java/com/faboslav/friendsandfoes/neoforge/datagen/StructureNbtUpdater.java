package com.faboslav.friendsandfoes.neoforge.datagen;

import com.google.common.hash.Hashing;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DataFixerUpper;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.datafixer.Schemas;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.registry.Registries;
import net.minecraft.resource.LifecycledResourceManagerImpl;
import net.minecraft.resource.Resource;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.Identifier;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

// Source: https://github.com/BluSunrize/ImmersiveEngineering/blob/1.20.1/src/datagen/java/blusunrize/immersiveengineering/data/StructureUpdater.java
public class StructureNbtUpdater implements DataProvider {
    private final String basePath;
    private final String modid;
    private final DataOutput output;
    private final LifecycledResourceManagerImpl resources;

    public StructureNbtUpdater(String basePath, String modid, ExistingFileHelper helper, DataOutput output) {
        this.basePath = basePath;
        this.modid = modid;
        this.output = output;

        try {
            Field serverData = ExistingFileHelper.class.getDeclaredField("serverData");
            serverData.setAccessible(true);
            resources = (LifecycledResourceManagerImpl) serverData.get(helper);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull CompletableFuture<?> run(@Nonnull DataWriter cache) {
        try {
            for (var entry : resources.findResources(basePath, $ -> true).entrySet()) {
                if (entry.getKey().getNamespace().equals(modid)) {
                    process(entry.getKey(), entry.getValue(), cache);
                }
            }
            return CompletableFuture.completedFuture(null);
        } catch (IOException x) {
            return CompletableFuture.failedFuture(x);
        }
    }

    private void process(Identifier loc, Resource resource, DataWriter cache) throws IOException {
        NbtCompound inputNBT = NbtIo.readCompressed(resource.getInputStream());
        NbtCompound converted = updateNBT(inputNBT);
        if (!converted.equals(inputNBT)) {
            Class<? extends DataFixer> fixerClass = Schemas.getFixer().getClass();
            if (!fixerClass.equals(DataFixerUpper.class)) {
                throw new RuntimeException("Structures are not up to date, but unknown data fixer is in use: " + fixerClass.getName());
            }
            writeNBTTo(loc, converted, cache);
        }
    }

    private void writeNBTTo(Identifier loc, NbtCompound data, DataWriter cache) throws IOException {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        NbtIo.writeCompressed(data, bytearrayoutputstream);
        byte[] bytes = bytearrayoutputstream.toByteArray();
        Path outputPath = output.getPath().resolve("data/" + loc.getNamespace() + "/" + loc.getPath());
        cache.write(outputPath, bytes, Hashing.sha1().hashBytes(bytes));
    }

    private static NbtCompound updateNBT(NbtCompound nbt) {
        final NbtCompound updatedNBT = DataFixTypes.STRUCTURE.update(
                Schemas.getFixer(), nbt, nbt.getInt("DataVersion")
        );
        StructureTemplate template = new StructureTemplate();
        template.readNbt(Registries.BLOCK.getReadOnlyWrapper(), updatedNBT);
        return template.writeNbt(new NbtCompound());
    }

    @Nonnull
    @Override
    public String getName() {
        return "Update structure files in " + basePath;
    }
}