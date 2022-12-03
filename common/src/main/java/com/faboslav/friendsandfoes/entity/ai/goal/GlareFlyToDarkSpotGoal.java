package com.faboslav.friendsandfoes.entity.ai.goal;

import com.faboslav.friendsandfoes.entity.GlareEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;

public final class GlareFlyToDarkSpotGoal extends Goal
{
	public boolean isRunning = false;
	private final double DARK_SPOT_SEARCH_DISTANCE = 8.0D;
	private final GlareEntity glare;
	private BlockPos darkSpot;
	private Path currentPath;
	private int runTicks;
	private int grumpyTicks;

	public GlareFlyToDarkSpotGoal(GlareEntity glareEntity) {
		this.glare = glareEntity;
		this.setControls(EnumSet.of(
			Control.MOVE
		));
	}

	@Override
	public boolean canStart() {
		World world = this.glare.getWorld();

		if (
			this.glare.getTicksUntilCanFindDarkSpot() > 0
			|| this.glare.getRandom().nextInt(10) != 0
			|| this.glare.isLeashed() == true
			|| this.glare.isSitting() == true
			|| this.glare.isTamed() == false
			|| this.glare.isBaby()
			|| (
				world.isDay()
				&& world.isSkyVisible(this.glare.getBlockPos())
			)
		) {
			return false;
		}

		ArrayList<BlockPos> darkSpots = this.findDarkSpots(DARK_SPOT_SEARCH_DISTANCE);
		this.darkSpot = this.getRandomDarkSpot(darkSpots);

		return this.darkSpot != null;
	}

	@Override
	public boolean shouldContinue() {

		if (
			this.runTicks >= 1200
			|| this.darkSpot == null
			|| this.grumpyTicks >= 120
		) {
			return false;
		}

		return this.glare.getWorld().getLightLevel(LightType.BLOCK, this.darkSpot) == 0;
	}

	@Override
	public void start() {
		this.isRunning = true;
		this.grumpyTicks = 0;
		this.runTicks = 0;
	}

	@Override
	public void stop() {
		this.glare.playGrumpinessShortSound();

		this.darkSpot = null;
		this.currentPath = null;

		this.glare.setTicksUntilCanFindDarkSpot(
			this.glare.generateRandomTicksUntilCanFindDarkSpot()
		);
		this.glare.setGrumpy(false);
		this.isRunning = false;
	}

	@Override
	public void tick() {
		this.runTicks++;

		EntityNavigation navigation = this.glare.getNavigation();
		double distanceToDarkSpot = this.glare.getPos().squaredDistanceTo(this.darkSpot.getX(),
			this.darkSpot.getY(),
			this.darkSpot.getZ()
		);

		this.currentPath = navigation.findPathTo(
			this.darkSpot.getX(),
			this.darkSpot.getY(),
			this.darkSpot.getZ(),
			0
		);

		if (this.currentPath != null) {
			this.glare.getNavigation().startMovingAlong(
				this.currentPath,
				this.glare.getMovementSpeed()
			);
		}

		if (distanceToDarkSpot >= 1.0D) {
			if (this.glare.isGrumpy()) {
				this.glare.setGrumpy(false);
			}

			return;
		}

		LivingEntity owner = this.glare.getOwner();

		if (owner == null) {
			return;
		}

		this.grumpyTicks++;

		if (!this.glare.isGrumpy()) {
			this.glare.setGrumpy(true);
		}

		if (grumpyTicks == 10) {
			this.glare.playGrumpinessSound();
		}

		if (grumpyTicks % 5 == 0) {
			this.glare.playRustleSound();
		}

		if (grumpyTicks % 10 == 0) {
			this.glare.spawnParticles(
				new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.AZALEA.getDefaultState()),
				7
			);
		}

		this.glare.getLookControl().lookAt(owner.getPos());
	}

	private ArrayList<BlockPos> findDarkSpots(double searchDistance) {
		ServerWorld serverWorld = (ServerWorld) this.glare.getEntityWorld();
		BlockPos blockPos = this.glare.getBlockPos();
		ArrayList<BlockPos> darkSpots = new ArrayList<>();

		for (int i = 0; (double) i <= searchDistance; i = i > 0 ? -i:1 - i) {
			for (int j = 0; (double) j < searchDistance; ++j) {
				for (int k = 0; k <= j; k = k > 0 ? -k:1 - k) {
					for (int l = k < j && k > -j ? j:0; l <= j; l = l > 0 ? -l:1 - l) {
						BlockPos.Mutable possibleDarkSpotBlockPos = new BlockPos.Mutable();
						possibleDarkSpotBlockPos.set(blockPos, k, i - 1, l);

						boolean isBlockWithinDistance = blockPos.isWithinDistance(
							possibleDarkSpotBlockPos,
							searchDistance
						);
						boolean isSpotDarkEnough = this.glare.getWorld().getLightLevel(LightType.BLOCK, possibleDarkSpotBlockPos) == 0;
						boolean isBlockSolidSurface = serverWorld.getBlockState(possibleDarkSpotBlockPos.down()).hasSolidTopSurface(serverWorld,
							possibleDarkSpotBlockPos,
							this.glare
						);
						boolean isBlockAccessible = serverWorld.isAir(possibleDarkSpotBlockPos) && serverWorld.isAir(possibleDarkSpotBlockPos.up());

						if (
							isBlockWithinDistance
							&& isBlockSolidSurface
							&& isBlockAccessible
							&& isSpotDarkEnough
						) {
							darkSpots.add(possibleDarkSpotBlockPos);
						}
					}
				}
			}
		}

		return darkSpots;
	}

	@Nullable
	private BlockPos getRandomDarkSpot(ArrayList<BlockPos> darkSpots) {
		int darkSpotsCount = darkSpots.size();

		if (darkSpotsCount == 0) {
			return null;
		}

		int randomDarkSpotIndex = this.glare.getRandom().nextInt(darkSpotsCount);

		return darkSpots.get(randomDarkSpotIndex);
	}
}
