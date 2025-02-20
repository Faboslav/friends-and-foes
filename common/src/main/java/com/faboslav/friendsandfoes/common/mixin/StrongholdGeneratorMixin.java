package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.common.entity.pose.TuffGolemEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.versions.VersionedEntitySpawnReason;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.providers.VanillaEnchantmentProviders;
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
	private boolean isTuffGolemGenerated;

	protected StrongholdGeneratorMixin(StructurePieceType type, int length, BoundingBox boundingBox) {
		super(type, length, boundingBox);
		this.isTuffGolemGenerated = false;
	}

	@Inject(
		at = @At("TAIL"),
		method = "postProcess"
	)
	private void friendsandfoes_generate(
		WorldGenLevel world,
		StructureManager structureAccessor,
		ChunkGenerator chunkGenerator,
		RandomSource random,
		BoundingBox chunkBox,
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

		ServerLevel serverWorld = world.getLevel();

		TuffGolemEntity tuffGolem = FriendsAndFoesEntityTypes.TUFF_GOLEM.get().create(serverWorld/*? >=1.21.3 {*/, VersionedEntitySpawnReason.STRUCTURE/*?}*/);

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

		ItemStack enchantedBook = Items.ENCHANTED_BOOK.getDefaultInstance();
		enchantedBook.setCount(1);

		enchantedBook.set(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
		EnchantmentHelper.enchantItemFromProvider(enchantedBook, serverWorld.registryAccess(), VanillaEnchantmentProviders.MOB_SPAWN_EQUIPMENT, new DifficultyInstance(serverWorld.getDifficulty(), 0, 0, 0.0F), random);

		tuffGolem.setItemSlot(EquipmentSlot.MAINHAND, enchantedBook);

		tuffGolem.setPrevPose(TuffGolemEntityPose.STANDING_WITH_ITEM.get());
		tuffGolem.setPoseWithoutPrevPose(TuffGolemEntityPose.SLEEPING_WITH_ITEM.get());

		boolean isTuffGolemSpawned = world.addFreshEntity(tuffGolem);

		if (isTuffGolemSpawned) {
			this.isTuffGolemGenerated = true;
		}
	}
}
