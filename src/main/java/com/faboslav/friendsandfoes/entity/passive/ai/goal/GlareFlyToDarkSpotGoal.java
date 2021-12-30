package com.faboslav.friendsandfoes.entity.passive.ai.goal;

import com.faboslav.friendsandfoes.entity.passive.GlareEntity;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;

public class GlareFlyToDarkSpotGoal extends Goal
{
	private final double DARK_SPOT_SEARCH_DISTANCE = 8.0D;
	protected GlareEntity glare;
	private BlockPos darkSpot;
	private Path currentPath;
	private int runTicks;
	private int grumpyTicks;

	public GlareFlyToDarkSpotGoal(GlareEntity glareEntity) {
		this.glare = glareEntity;
		this.setControls(EnumSet.of(
			Control.MOVE,
			Control.LOOK
		));
	}

	@Override
	public boolean canStart() {
		World world = this.glare.getWorld();

		if (
			this.glare.getTicksUntilCanFindDarkSpot() > 0
			|| this.glare.isLeashed()
			|| !this.glare.isTamed()
			|| (
				world.isDay()
				&& world.isSkyVisible(this.glare.getBlockPos())
			)
		) {
			return false;
		}

		ArrayList<BlockPos> darkSpots = this.findDarkSpots(DARK_SPOT_SEARCH_DISTANCE);
		this.darkSpot = this.getRandomDarkSpot(darkSpots);

		if (this.darkSpot == null) {
			return false;
		}

		System.out.println(this.darkSpot);

		return true;
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


		boolean isSpotDarkEnough = this.glare.getWorld().getLightLevel(LightType.BLOCK, this.darkSpot) == 0;

		return isSpotDarkEnough;
	}

	@Override
	public void start() {
		System.out.println("go");
	}

	@Override
	public void stop() {
		System.out.println("stop");
		// Reset goal
		this.runTicks = 0;
		this.grumpyTicks = 0;
		this.darkSpot = null;
		this.currentPath = null;

		// Update entity data
		this.glare.setGrumpy(false);
		int minCooldownTicks = GlareEntity.MIN_TICKS_UNTIL_CAN_FIND_DARK_SPOT;
		int maxCooldownTicks = GlareEntity.MAX_TICKS_UNTIL_CAN_FIND_DARK_SPOT;
		int cooldown = RandomGenerator.generateInt(minCooldownTicks, maxCooldownTicks);
		this.glare.setTicksUntilCanFindDarkSpot(cooldown);
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
		this.glare.setGrumpy(true);
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
