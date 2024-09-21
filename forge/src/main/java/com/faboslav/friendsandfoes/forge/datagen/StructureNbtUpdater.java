package com.faboslav.friendsandfoes.forge.datagen;

import com.google.common.hash.Hashing;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DataFixerUpper;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.datafixer.Schemas;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resource.LifecycledResourceManagerImpl;
import net.minecraft.resource.Resource;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.Identifier;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;

public class StructureNbtUpdater implements DataProvider {
    private final String basePath;
    private final String modid;
    private final DataGenerator gen;
    private final LifecycledResourceManagerImpl resources;

    public StructureNbtUpdater(
            String basePath, String modid, ExistingFileHelper helper, DataGenerator gen
    ) {
        this.basePath = basePath;
        this.modid = modid;
        this.gen = gen;
        try {
            Field serverData = ExistingFileHelper.class.getDeclaredField("serverData");
            serverData.setAccessible(true);
            resources = (LifecycledResourceManagerImpl) serverData.get(helper);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run(@Nonnull DataWriter cache) throws IOException {
        for (var entry : resources.findResources(basePath, $ -> true).entrySet())
            if (entry.getKey().getNamespace().equals(modid))
                process(entry.getKey(), entry.getValue(), cache);
    }

    private void process(Identifier loc, Resource resource, DataWriter cache) throws IOException {
        NbtCompound inputNBT = NbtIo.readCompressed(resource.getInputStream());
        NbtCompound converted = updateNBT(inputNBT);
        if (!converted.equals(inputNBT)) {
            Class<? extends DataFixer> fixerClass = Schemas.getFixer().getClass();
            if (!fixerClass.equals(DataFixerUpper.class))
                throw new RuntimeException("Structures are not up to date, but unknown data fixer is in use: " + fixerClass.getName());
            writeNBTTo(loc, converted, cache);
        }
    }

    private void writeNBTTo(Identifier loc, NbtCompound data, DataWriter cache) throws IOException {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        NbtIo.writeCompressed(data, bytearrayoutputstream);
        byte[] bytes = bytearrayoutputstream.toByteArray();
        Path outputPath = gen.getOutput().resolve("data/" + loc.getNamespace() + "/" + loc.getPath());
        cache.write(outputPath, bytes, Hashing.sha1().hashBytes(bytes));
    }

    private static NbtCompound updateNBT(NbtCompound nbt) {
        final NbtCompound updatedNBT = NbtHelper.update(
                Schemas.getFixer(), DataFixTypes.STRUCTURE, nbt, nbt.getInt("DataVersion")
        );
        StructureTemplate template = new StructureTemplate();
        template.readNbt(updatedNBT);
        return template.writeNbt(new NbtCompound());
    }

    @Nonnull
    @Override
    public String getName() {
        return "Update structure files in " + basePath;
    }
}
