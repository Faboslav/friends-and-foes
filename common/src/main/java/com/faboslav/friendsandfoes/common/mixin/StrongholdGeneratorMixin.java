package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.common.entity.pose.FriendsAndFoesEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.structures.StrongholdPieces;

@Mixin(StrongholdPieces.Library.class)
public abstract class StrongholdGeneratorMixin extends StructurePiece
{
	@Unique
	private boolean isTuffGolemGenerated;

	protected StrongholdGeneratorMixin(StructurePieceType type, int length, BoundingBox boundingBox) {
		super(type, length, boundingBox);
		this.isTuffGolemGenerated = false;
	}

	@WrapMethod(
		method = "postProcess"
	)
	private void friendsandfoes$generate(
		WorldGenLevel level,
		StructureManager structureManager,
		ChunkGenerator generator,
		RandomSource random,
		BoundingBox box,
		ChunkPos chunkPos,
		BlockPos pos,
		Operation<Void> original
	) {
		original.call(level, structureManager, generator, random, box, chunkPos, pos);

		if (
			!FriendsAndFoes.getConfig().generateTuffGolemInStronghold
			|| this.isTuffGolemGenerated
			|| random.nextFloat() > 0.75F
		) {
			return;
		}

		ServerLevel serverWorld = level.getLevel();

		TuffGolemEntity tuffGolem = FriendsAndFoesEntityTypes.TUFF_GOLEM.get().create(serverWorld/*? if >=1.21.3 {*/, VersionedEntitySpawnReason.STRUCTURE/*?}*/);

		if (tuffGolem == null) {
			return;
		}

		ArrayList<Tuple> possibleSpawnPositions = new ArrayList<>();

		for (int x = 5; x < 8; x = x + 3) {
			for (int y = 4; y < 12; y = y + 2) {
				possibleSpawnPositions.add(new Tuple(x, y));
			}
		}

		Tuple<Integer, Integer> pickedXYPair = possibleSpawnPositions.get(random.nextIntBetweenInclusive(0, possibleSpawnPositions.size() - 1));
		BlockPos.MutableBlockPos tuffGolemPos = this.getWorldPos(
			pickedXYPair.getA(),
			1,
			pickedXYPair.getB()
		);
		tuffGolem.setPos(
			tuffGolemPos.getX() + 0.5F,
			tuffGolemPos.getY(),
			tuffGolemPos.getZ() + 0.5F
		);

		float randomSpawnYaw = 90.0F * (float) random.nextIntBetweenInclusive(0, 3);
		tuffGolem.setSpawnYaw(randomSpawnYaw);

		ItemStack enchantedBook = Items.BOOK.getDefaultInstance();
		enchantedBook.setCount(1);

		int enchantmentLevel = 30;
		var enchantmentList = this.friendsAndFoes$getEnchantmentList(random, serverWorld.registryAccess(), enchantedBook, enchantmentLevel);

		if (!enchantmentList.isEmpty()) {
			enchantedBook = EnchantmentHelper.enchantItem(random, enchantedBook, enchantmentLevel, enchantmentList.stream());
		}

		tuffGolem.setItemSlot(EquipmentSlot.MAINHAND, enchantedBook);
		tuffGolem.setPrevEntityPose(FriendsAndFoesEntityPose.STANDING_WITH_ITEM);
		tuffGolem.setEntityPoseWithoutPrevPose(FriendsAndFoesEntityPose.SLEEPING_WITH_ITEM);

		tuffGolem.setHome(tuffGolem.getNewHome());

		boolean isTuffGolemSpawned = level.addFreshEntity(tuffGolem);

		if (isTuffGolemSpawned) {
			this.isTuffGolemGenerated = true;
		}
	}

	@Unique
	private List<Holder<Enchantment>> friendsAndFoes$getEnchantmentList(RandomSource random, RegistryAccess registryAccess, ItemStack itemStack, int cost) {
		Optional<HolderSet.Named<Enchantment>> optional = registryAccess
			.lookupOrThrow(Registries.ENCHANTMENT)
			.get(EnchantmentTags.IN_ENCHANTING_TABLE);

		if (optional.isEmpty()) {
			return List.of();
		}

		List<EnchantmentInstance> instances = EnchantmentHelper.selectEnchantment(
			random,
			itemStack,
			cost,
			optional.get().stream()
		);

		if (itemStack.is(Items.ENCHANTED_BOOK) && instances.size() > 1) {
			instances.remove(random.nextInt(instances.size()));
		}

		return instances.stream()
			//? if >= 1.21.5 {
			.map(EnchantmentInstance::enchantment)
			//?} else {
			/*.map(enchantmentInstance -> enchantmentInstance.enchantment)
			*///?}
			.toList();
	}
}
