package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.common.entity.pose.TuffGolemEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.provider.EnchantmentProviders;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StrongholdGenerator;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(StrongholdGenerator.Library.class)
public abstract class StrongholdGeneratorMixin extends StructurePiece
{
	private boolean isTuffGolemGenerated;

	protected StrongholdGeneratorMixin(StructurePieceType type, int length, BlockBox boundingBox) {
		super(type, length, boundingBox);
		this.isTuffGolemGenerated = false;
	}

	@Inject(
		at = @At("TAIL"),
		method = "generate"
	)
	private void friendsandfoes_generate(
		StructureWorldAccess world,
		StructureAccessor structureAccessor,
		ChunkGenerator chunkGenerator,
		Random random,
		BlockBox chunkBox,
		ChunkPos chunkPos,
		BlockPos pivot,
		CallbackInfo ci
	) {
		if (
			!FriendsAndFoes.getConfig().generateTuffGolemInStronghold
			|| this.isTuffGolemGenerated
			|| random.nextFloat() > 0.75F
		) {
			return;
		}

		ServerWorld serverWorld = world.toServerWorld();

		TuffGolemEntity tuffGolem = FriendsAndFoesEntityTypes.TUFF_GOLEM.get().create(serverWorld);

		if (tuffGolem == null) {
			return;
		}

		ArrayList<Pair> possibleSpawnPositions = new ArrayList<>();

		for (int x = 5; x < 8; x = x + 3) {
			for (int y = 4; y < 12; y = y + 2) {
				possibleSpawnPositions.add(new Pair(x, y));
			}
		}

		Pair<Integer, Integer> pickedXYPair = possibleSpawnPositions.get(random.nextBetween(0, possibleSpawnPositions.size() - 1));
		BlockPos.Mutable tuffGolemPos = this.offsetPos(
			pickedXYPair.getLeft(),
			1,
			pickedXYPair.getRight()
		);
		tuffGolem.setPosition(
			tuffGolemPos.getX() + 0.5F,
			tuffGolemPos.getY(),
			tuffGolemPos.getZ() + 0.5F
		);

		float randomSpawnYaw = 90.0F * (float) random.nextBetween(0, 3);
		tuffGolem.setSpawnYaw(randomSpawnYaw);

		ItemStack enchantedBook = Items.ENCHANTED_BOOK.getDefaultStack();
		enchantedBook.setCount(1);

		enchantedBook.set(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);
		EnchantmentHelper.applyEnchantmentProvider(enchantedBook, serverWorld.getRegistryManager(), EnchantmentProviders.MOB_SPAWN_EQUIPMENT, new LocalDifficulty(serverWorld.getDifficulty(), 0, 0, 0.0F), random);

		tuffGolem.equipStack(EquipmentSlot.MAINHAND, enchantedBook);

		tuffGolem.setPrevPose(TuffGolemEntityPose.STANDING_WITH_ITEM.get());
		tuffGolem.setPoseWithoutPrevPose(TuffGolemEntityPose.SLEEPING_WITH_ITEM.get());

		boolean isTuffGolemSpawned = world.spawnEntity(tuffGolem);

		if (isTuffGolemSpawned) {
			this.isTuffGolemGenerated = true;
		}
	}
}
