package com.faboslav.friendsandfoes.common.config.client.gui.widget;

import dev.isxander.yacl3.gui.image.ImageRendererManager;
import dev.isxander.yacl3.gui.image.impl.AnimatedDynamicTextureImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;


import net.minecraft.util.Mth;

import java.lang.reflect.Field;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import static org.lwjgl.opengl.GL20.*;

/*? if <=1.21.1 {*/
/*import net.minecraft.util.FastColor;
 *//*?} else {*/
import net.minecraft.util.ARGB;
	/*?}*/

/**
 * Inspired by use in Sounds mod
 *
 * @author IMB11
 * <a href="https://github.com/IMB11/Sounds/blob/main/src/main/java/dev/imb11/sounds/gui/ImageButtonWidget.java"https://github.com/IMB11/Sounds/blob/main/src/main/java/dev/imb11/sounds/gui/ImageButtonWidget.java</a>
 */
public class ImageButtonWidget extends AbstractWidget
{
	float durationHovered = 1f;
	private final CompletableFuture<AnimatedDynamicTextureImage> image;
	private final Consumer<AbstractWidget> onPress;

	public ImageButtonWidget(
		int x,
		int y,
		int width,
		int height,
		Component message,
		ResourceLocation image,
		Consumer<AbstractWidget> clickEvent
	) {
		super(x, y, width, height, message);
		this.image = ImageRendererManager.registerOrGetImage(image, () -> AnimatedDynamicTextureImage.createWEBPFromTexture(image));
		this.onPress = clickEvent;
	}

	@Override
	public void onClick(double mouseX, double mouseY) {
		if (this.onPress != null) {
			this.onPress.accept(this);
		}
	}

	@Override
	protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
		context.enableScissor(getX(), getY(), getX() + width, getY() + height);
		this.isHovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;

		if (this.isHovered() || this.isFocused()) {
			durationHovered += delta / 2f;
		} else {
			if (durationHovered < 0) {
				durationHovered = 0;
			} else {
				durationHovered -= durationHovered / 4f;
			}
		}

		// Ease in out lerp.
		float alphaScale = Mth.clampedLerp(0.7f, 0.2f, Mth.clamp(durationHovered - 1f, 0.0f, 1.0f));

		if (image.isDone()) {
			int minFilterScalingTypePrev = glGetTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER);
			int magFilterScalingTypePrev = glGetTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER);

			try {
				var contentImage = image.get();
				if (contentImage != null) {
					// Using reflection, get value of contentImage.frameWidth and frameHeight
					try {
						Field frameWidthField = contentImage.getClass().getDeclaredField("frameWidth");
						frameWidthField.setAccessible(true);
						int frameWidth = frameWidthField.getInt(contentImage);

						Field frameHeightField = contentImage.getClass().getDeclaredField("frameHeight");
						frameHeightField.setAccessible(true);
						int frameHeight = frameHeightField.getInt(contentImage);

						// Use frameWidth and frameHeight as needed
						// Scale the image so that the image height is the same as the button height.
						float neededWidth = frameWidth * ((float) this.height / frameHeight);

						// Scale the image to fit within the width and height of the button.
						context.pose().pushPose();
						// gl bilinear scaling.
						glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
						glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
						contentImage.render(context, getX(), getY(), (int) Math.max(neededWidth, this.width), delta);
						context.pose().popPose();

						// reset gl scaling

					} catch (NoSuchFieldException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			} catch (InterruptedException | ExecutionException ignored) {
			} finally {
				// reset gl scaling
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minFilterScalingTypePrev);
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magFilterScalingTypePrev);
			}
		}

		/*? if <=1.21.1 {*/
		/*int greyColor = FastColor.ABGR32.color((int) (alphaScale * 255), 0, 0, 0);
		 *//*?} else {*/
		int greyColor = ARGB.color((int) (alphaScale * 255), 0, 0, 0);
		/*?}*/
		context.fill(getX(), getY(), getX() + width, getY() + height, greyColor);

		// Draw text.
		var client = Minecraft.getInstance();

		float fontScaling = 1.24f;

		int unscaledTextX = this.getX() + 5;
		int unscaledTextY = this.getY() + this.height - client.font.lineHeight - 5;
		int textX = (int) (unscaledTextX / fontScaling);
		int textY = (int) (unscaledTextY / fontScaling);
		int endX = (int) ((this.getX() + this.width - 5) / fontScaling);
		int endY = (int) ((this.getY() + this.height - 5) / fontScaling);

		context.fill(unscaledTextX - 5, unscaledTextY - 5, unscaledTextX + this.width - 5, unscaledTextY + client.font.lineHeight + 5, 0xAF000000);

		context.pose().pushPose();
		context.pose().scale(fontScaling, fontScaling, 1.0f);

		renderScrollingString(context, client.font, getMessage(), textX, textY, endX, endY, 0xFFFFFF);

		context.pose().popPose();

		// Draw border.
		context.renderOutline(getX(), getY(), width, height, 0x0FFFFFFF);
		context.disableScissor();
	}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput builder) {
		builder.add(NarratedElementType.HINT, this.getMessage());
	}
}
