package com.faboslav.friendsandfoes.entity.passive.ai.goal;

import com.faboslav.friendsandfoes.block.CopperButtonBlock;
import com.faboslav.friendsandfoes.entity.passive.CopperGolemEntity;
import com.faboslav.friendsandfoes.registry.BlockTagRegistry;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.function.Predicate;

public class CopperGolemPressButtonGoal extends Goal
{
    private final double COPPER_BUTTON_SEARCH_DISTANCE = 12.0D;
    protected CopperGolemEntity copperGolem;
    private BlockPos copperButtonToPress;
    private BlockPos positionToStandOn;
    private BlockPos positionToLookAt;
    private Path currentPath;
    private int standingNearToCopperButtonTicks;
    private int idleTicksBeforeButtonPress;
    private int idleTicksAfterButtonPress;
    private int ticksAfterButtonPress;
    private int runTicks;
    private boolean copperButtonWasSuccessfullyPressed;

    private final Predicate<BlockState> copperButtonPredicate = (state) -> {
        return state.isIn(BlockTagRegistry.COPPER_BUTTONS);
    };

    public CopperGolemPressButtonGoal(CopperGolemEntity copperGolem) {
        this.copperGolem = copperGolem;
        this.setControls(EnumSet.of(
                Control.MOVE,
                Control.LOOK
        ));
    }

    @Override
    public boolean canStart() {
        if (this.copperGolem.getTicksUntilCanPressButton() > 0) {
            return false;
        }

        if (this.copperGolem.isOxidized()) {
            return false;
        } else if (this.copperGolem.getNavigation().isFollowingPath()) {
            return false;
        } else if(this.copperGolem.isSpinningHead()) {
            return false;
        }

        ArrayList<BlockPos> copperButtons = this.findCopperButtons();
        this.copperButtonToPress = this.getRandomCopperButtonToPress(copperButtons);

        if (this.copperButtonToPress == null) {
            return false;
        }

        this.calculatePositionToStandOn();
        this.calculatePositionToLookAt();

        if (this.positionToStandOn == null || this.positionToLookAt == null) {
            return false;
        }

        return true;
    }

    @Override
    public boolean shouldContinue() {
        if (this.copperGolem.isOxidized()) {
            return false;
        }

        if(this.runTicks >= 1200) {
            return false;
        }

        if (this.copperButtonWasSuccessfullyPressed && this.ticksAfterButtonPress >= this.idleTicksAfterButtonPress) {
            return false;
        }

        if (this.copperButtonToPress == null || this.positionToStandOn == null || this.positionToLookAt == null) {
            return false;
        }

        BlockState copperButtonBlockState = this.getCopperButtonBlockState(this.copperButtonToPress);
        if (copperButtonBlockState == null) {
            return false;
        }

        return true;
    }

    @Override
    public void start() {
        this.runTicks = 0;
        this.standingNearToCopperButtonTicks = 0;
        this.ticksAfterButtonPress = 0;
        this.copperButtonWasSuccessfullyPressed = false;
        int minIdleTicks = 25;
        int maxIdleTicks = 50;
        this.idleTicksBeforeButtonPress = RandomGenerator.generateInt(minIdleTicks, maxIdleTicks);
        this.idleTicksAfterButtonPress = RandomGenerator.generateInt(minIdleTicks, maxIdleTicks);
    }

    @Override
    public void stop() {
        // Reset goal
        this.copperButtonToPress = null;
        this.positionToStandOn = null;
        this.positionToLookAt = null;
        this.currentPath = null;

        // Update entity data
        this.copperGolem.setIsPressingButton(false);
        int minCooldownTicks = CopperGolemEntity.MIN_TICKS_UNTIL_NEXT_HEAD_SPIN;
        if (this.copperGolem.isDegraded()) {
            minCooldownTicks *= this.copperGolem.getOxidationLevel().ordinal();
        }

        int maxCooldownTicks = CopperGolemEntity.MAX_TICKS_UNTIL_CAN_PRESS_BUTTON;
        int cooldown = RandomGenerator.generateInt(minCooldownTicks, maxCooldownTicks);
        this.copperGolem.setTicksUntilCanPressButton(cooldown);
    }

    @Override
    public void tick() {
        this.runTicks++;

        EntityNavigation navigation = this.copperGolem.getNavigation();
        double distanceToButton = this.copperGolem.getPos().squaredDistanceTo(this.positionToStandOn.getX(),
                this.positionToStandOn.getY(),
                this.positionToStandOn.getZ()
        );

        this.currentPath = navigation.findPathTo(
                this.positionToStandOn.getX(),
                this.positionToStandOn.getY(),
                this.positionToStandOn.getZ(),
                0
        );

        if (this.currentPath != null) {
            this.copperGolem.getNavigation().startMovingAlong(
                    this.currentPath,
                    this.copperGolem.getMovementSpeed()
            );
        }

        if (distanceToButton <= 3.0D) {
            this.copperGolem.getLookControl().lookAt(
                    this.positionToLookAt.getX(),
                    this.positionToLookAt.getY(),
                    this.positionToLookAt.getZ()
            );
        }

        if (distanceToButton >= 1.0D) {
            if (this.copperGolem.isPressingButton()) {
                this.copperGolem.setIsPressingButton(false);
            }
            return;
        }

        this.standingNearToCopperButtonTicks++;

        if (this.standingNearToCopperButtonTicks >= this.idleTicksBeforeButtonPress / 3 &&
                !this.copperGolem.isPressingButton()) {
            this.copperGolem.setIsPressingButton(true);
        }

        if (standingNearToCopperButtonTicks <= this.idleTicksBeforeButtonPress) {
            return;
        }

        if (!this.copperButtonWasSuccessfullyPressed) {
            this.copperButtonWasSuccessfullyPressed = this.pressCopperButton();
        } else {
            this.ticksAfterButtonPress++;
        }
    }

    private boolean pressCopperButton() {
        BlockState copperButtonBlockState = this.getCopperButtonBlockState(this.copperButtonToPress);
        if (copperButtonBlockState==null) {
            return false;
        } else if (copperButtonBlockState.get(CopperButtonBlock.POWERED)) {
            return false;
        }

        CopperButtonBlock copperButtonBlock = (CopperButtonBlock) copperButtonBlockState.getBlock();
        copperButtonBlock.powerOn(
                copperButtonBlockState,
                this.copperGolem.getEntityWorld(),
                this.copperButtonToPress
        );
        copperGolem.getEntityWorld().playSound(null,
                this.copperButtonToPress,
                copperButtonBlock.getClickSound(true),
                SoundCategory.BLOCKS,
                0.3F,
                0.6F
        );
        copperGolem.getEntityWorld().emitGameEvent(this.copperGolem,
                GameEvent.BLOCK_PRESS,
                this.copperButtonToPress
        );
        return true;
    }

    private ArrayList<BlockPos> findCopperButtons() {
        BlockPos blockPos = this.copperGolem.getBlockPos();
        ArrayList<BlockPos> copperButtonBlocks = new ArrayList<>();

        for (int i = 0; (double) i <= COPPER_BUTTON_SEARCH_DISTANCE; i = i > 0 ? -i:1 - i) {
            for (int j = 0; (double) j < COPPER_BUTTON_SEARCH_DISTANCE; ++j) {
                for (int k = 0; k <= j; k = k > 0 ? -k:1 - k) {
                    for (int l = k < j && k > -j ? j:0; l <= j; l = l > 0 ? -l:1 - l) {
                        BlockPos.Mutable mutable = new BlockPos.Mutable();
                        mutable.set(blockPos, k, i - 1, l);
                        if (blockPos.isWithinDistance(mutable, COPPER_BUTTON_SEARCH_DISTANCE) && copperButtonPredicate.test(this.copperGolem.getEntityWorld().getBlockState(mutable))) {
                            copperButtonBlocks.add(mutable);
                        }
                    }
                }
            }
        }

        return copperButtonBlocks;
    }

    @Nullable
    private BlockPos getRandomCopperButtonToPress(ArrayList<BlockPos> copperButtons) {
        int copperButtonsCount = copperButtons.size();

        if (copperButtonsCount==0) {
            return null;
        }

        int randomCopperButtonIndex = this.copperGolem.getRandom().nextInt(copperButtonsCount);

        return copperButtons.get(randomCopperButtonIndex);
    }

    private void calculatePositionToStandOn() {
        BlockState copperButtonBlockState = this.getCopperButtonBlockState(this.copperButtonToPress);
        if (copperButtonBlockState==null) {
            return;
        }

        BlockPos positionToStandOn = null;

        Direction direction = copperButtonBlockState.get(HorizontalFacingBlock.FACING);
        ServerWorld serverWorld = (ServerWorld) this.copperGolem.getEntityWorld();

        switch (direction) {
            case DOWN:
                if (serverWorld.isAir(this.copperButtonToPress.down()) &&
                        serverWorld.getBlockState(this.copperButtonToPress.down(2)).hasSolidTopSurface(serverWorld,
                                this.copperButtonToPress,
                                this.copperGolem
                        )) {
                    positionToStandOn = copperButtonToPress.add(0, -2, 0);
                }
                break;
            case UP:
                if (serverWorld.getBlockState(this.copperButtonToPress.down(1)).hasSolidTopSurface(serverWorld,
                        this.copperButtonToPress,
                        this.copperGolem
                ) && serverWorld.isAir(this.copperButtonToPress.up())) {
                    positionToStandOn = copperButtonToPress;
                }
                break;
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                if (serverWorld.isAir(this.copperButtonToPress.down()) &&
                        serverWorld.getBlockState(this.copperButtonToPress.down(2)).hasSolidTopSurface(
                                serverWorld,
                                this.copperButtonToPress,
                                this.copperGolem
                        )
                ) {
                    positionToStandOn = copperButtonToPress.add(0, -1, 0);
                } else if (
                        serverWorld.isAir(this.copperButtonToPress.up()) &&
                        serverWorld.getBlockState(
                                this.copperButtonToPress.down()).hasSolidTopSurface(
                                        serverWorld,
                                this.copperButtonToPress,
                                this.copperGolem
                        )
                ) {
                    positionToStandOn = copperButtonToPress;
                }
        }

        if (positionToStandOn!=null) {
            positionToStandOn = positionToStandOn.add(0.5, 0, 0.5);
        }

        this.positionToStandOn = positionToStandOn;
    }

    private void calculatePositionToLookAt() {
        BlockState copperButtonBlockState = this.getCopperButtonBlockState(this.copperButtonToPress);
        if (copperButtonBlockState==null) {
            return;
        }

        BlockPos positionToLookAt = copperButtonToPress;
        Direction direction = copperButtonBlockState.get(HorizontalFacingBlock.FACING);

        positionToLookAt = switch (direction) {
            case DOWN, UP -> positionToLookAt;
            case NORTH -> positionToLookAt.add(0, 0, 1);
            case EAST -> positionToLookAt.add(-1, 0, 0);
            case SOUTH -> positionToLookAt.add(0, 0, -1);
            case WEST -> positionToLookAt.add(1, 0, 0);
        };

        this.positionToLookAt = positionToLookAt;
    }

    @Nullable
    private BlockState getCopperButtonBlockState(BlockPos copperButtonBlockPos) {
        ServerWorld serverWorld = (ServerWorld) this.copperGolem.getEntityWorld();
        BlockState blockState = serverWorld.getBlockState(copperButtonBlockPos);
        if (blockState.getBlock() instanceof CopperButtonBlock) {
            return blockState;
        }

        return null;
    }
}
