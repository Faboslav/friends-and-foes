package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.entity.pose.TuffGolemEntityPose;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityTypes;
import net.minecraft.enchantment.EnchantmentHelper;
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
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(StrongholdGenerator.Library.class)
public abstract class StrongholdGeneratorMixin extends StructurePiece
{
	@Final
	@Shadow
	private boolean tall;
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
			this.isTuffGolemGenerated
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

		float randomSpawnYaw = 90.0F * (float) random.nextBetween(1, 3);
		tuffGolem.setSpawnYaw(randomSpawnYaw);

		ItemStack itemStack = Items.BOOK.getDefaultStack();
		itemStack.setCount(1);
		ItemStack enchantedItemStack = EnchantmentHelper.enchant(
			random,
			itemStack,
			random.nextBetween(1, 3),
			true
		);

		tuffGolem.equipStack(EquipmentSlot.MAINHAND, enchantedItemStack);

		tuffGolem.setPrevPose(TuffGolemEntityPose.STANDING_WITH_ITEM);
		tuffGolem.setPoseWithoutPrevPose(TuffGolemEntityPose.SLEEPING_WITH_ITEM);

		boolean isTuffGolemSpawned = world.spawnEntity(tuffGolem);

		if (isTuffGolemSpawned) {
			this.isTuffGolemGenerated = true;
		}
	}
}
