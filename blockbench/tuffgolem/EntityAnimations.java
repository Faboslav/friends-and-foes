
import animation;
import animationHelper;
import keyframe;
import transformation;

/**
 * Made with Blockbench %(bb_version)
 * Exported for Minecraft version 1.18.x with Yarn mappings
 */
public final class EntityAnimations
{
	public static final Animation SHOWITEM;
	public static final Animation HIDEITEM;
	public static final Animation SLEEP;
	public static final Animation SLEEPWITHITEM;
	public static final Animation WAKE;
	public static final Animation WAKEWITHITEM;
	public static final Animation WAKEANDHIDEITEM;
	public static final Animation WAKEANDSHOWITEM;

    public EntityAnimations() {
    }
    
    static {
		SHOWITEM = Animation.Builder.create(0.4583333333333333F).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, -4F, -2F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(0F, 0F, -5.75F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.SCALE,
				new Keyframe(0F, AnimationHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.0833F, AnimationHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0F, -2F, -1.5F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0F, -2F, -1.5F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.0833F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, AnimationHelper.createTranslationalVector(0F, 0F, 2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Targets.SCALE,
				new Keyframe(0F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.0833F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, AnimationHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.0417F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, AnimationHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).build();
		HIDEITEM = Animation.Builder.create(0.4583333333333333F).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, AnimationHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createRotationalVector(90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, AnimationHelper.createTranslationalVector(0F, 0F, -5.75F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createTranslationalVector(0F, -4F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.SCALE,
				new Keyframe(0.125F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.375F, AnimationHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, AnimationHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.125F, AnimationHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0F, -2F, -1.5F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, AnimationHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.125F, AnimationHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0F, -2F, -1.5F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0F, 0F, 2F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.375F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Targets.SCALE,
				new Keyframe(0.2917F, AnimationHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.375F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).build();
		SLEEP = Animation.Builder.create(0.9166666666666666F).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-75F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-80F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-75F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-80F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(20F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(25F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR)
			)
		).build();
		SLEEPWITHITEM = Animation.Builder.create(0.9166666666666666F).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-75F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-80F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-75F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-80F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(20F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(25F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(15F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(10F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0F, -1F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR)
			)
		).build();
		WAKE = Animation.Builder.create(0.9166666666666666F).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-45F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-50F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-45F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-50F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(25F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).build();
		WAKEWITHITEM = Animation.Builder.create(0.9166666666666666F).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-75F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-75F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(25F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Targets.SCALE,
				new Keyframe(0F, AnimationHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(15F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(10F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0F, -1F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR)
			)
		).build();
		WAKEANDHIDEITEM = Animation.Builder.create(1.375F).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-75F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0417F, AnimationHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0417F, AnimationHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.1667F, AnimationHelper.createTranslationalVector(0F, -2F, -1.5F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-75F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0417F, AnimationHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0417F, AnimationHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.1667F, AnimationHelper.createTranslationalVector(0F, -2F, -1.5F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(25F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.1667F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.3333F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(1.2083F, AnimationHelper.createTranslationalVector(0F, 0F, 2F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.2917F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Targets.SCALE,
				new Keyframe(0F, AnimationHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.2083F, AnimationHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.2917F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(15F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(10F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0417F, AnimationHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, AnimationHelper.createRotationalVector(90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0F, -1F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0417F, AnimationHelper.createTranslationalVector(0F, 0F, -5.75F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, AnimationHelper.createTranslationalVector(0F, -4F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.SCALE,
				new Keyframe(1.0417F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.25F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.2917F, AnimationHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, AnimationHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).build();
		WAKEANDSHOWITEM = Animation.Builder.create(1.375F).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-45F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-50F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.25F, AnimationHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.1667F, AnimationHelper.createTranslationalVector(0F, -2F, -1.5F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-45F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-50F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.25F, AnimationHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.1667F, AnimationHelper.createTranslationalVector(0F, -2F, -1.5F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(25F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(1F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0417F, AnimationHelper.createTranslationalVector(0F, 0F, 2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Targets.SCALE,
				new Keyframe(0.9167F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(1F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0417F, AnimationHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.25F, AnimationHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(0F, -4F, -2F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0F, 0F, -5.75F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, AnimationHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.SCALE,
				new Keyframe(0.9167F, AnimationHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1F, AnimationHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0417F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.25F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR)
			)
		).build();
    }
}