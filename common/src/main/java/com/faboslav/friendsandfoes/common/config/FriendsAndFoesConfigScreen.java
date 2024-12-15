package com.faboslav.friendsandfoes.common.config;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.config.client.gui.widget.DynamicGridWidget;
import com.faboslav.friendsandfoes.common.config.client.gui.widget.ImageButtonWidget;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class FriendsAndFoesConfigScreen extends Screen
{
	private final Screen parent;
	private Screen mainConfigScreen = FriendsAndFoesConfig.HANDLER.generateGui().generateScreen(null);

	public FriendsAndFoesConfigScreen(@Nullable Screen parent) {
		super(Component.translatable("friendsandfoes"));
		this.parent = parent;
	}

	@Override
	public void onClose() {
		assert this.minecraft != null;
		this.minecraft.setScreen(this.parent);
	}

	@Override
	public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
		super.renderBackground(context, mouseX, mouseY, delta);
		super.render(context, mouseX, mouseY, delta);

		assert this.minecraft != null;
		context.drawCenteredString(this.minecraft.font, Component.translatable("yacl3.config.friendsandfoes:friendsandfoes"), this.width / 2, 10, 0xFFFFFF);
	}

	@Override
	protected void init() {
		super.init();

		int fontHeight = this.font.lineHeight;
		DynamicGridWidget grid = new DynamicGridWidget(10, 10 + fontHeight + 10, width - 13, height - 20 - fontHeight - 10 - 20);

		grid.setPadding(3);

		grid.addChild(new ImageButtonWidget(0, 0, 0, 0, Component.translatable("yacl3.config.friendsandfoes:friendsandfoes.category.mobs"), FriendsAndFoes.makeID("textures/gui/config/images/buttons/mobs.webp"), btn -> {
			this.minecraft.setScreen(this.mainConfigScreen);
		}), 2, 1);

		grid.addChild(new ImageButtonWidget(0, 0, 0, 0, Component.translatable("yacl3.config.friendsandfoes:friendsandfoes.category.general"), FriendsAndFoes.makeID("textures/gui/config/images/buttons/general.webp"), btn -> {
			this.minecraft.setScreen(this.mainConfigScreen);
		}), 2, 1);

		grid.calculateLayout();
		grid.visitWidgets(this::addRenderableWidget);

		int discordAndKoFiButtonsWidth = 100 + 100 + 30; // button widths + left margin of Ko-Fi button + right margin of Discord button
		int doneButtonWidth = this.width - discordAndKoFiButtonsWidth;
		var buttonWidget = Button.builder(CommonComponents.GUI_DONE, (btn) -> this.minecraft.setScreen(this.parent)).bounds(this.width / 2 - doneButtonWidth / 2, this.height - 30, doneButtonWidth, 20).build();
		var donateButton = Button.builder(Component.literal("Donate").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD), (btn) -> Util.getPlatform().openUri("https://ko-fi.com/faboslav")).bounds(10, this.height - 30, 100, 20).build();
		var discordButton = Button.builder(Component.literal("Discord").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD), (btn) -> Util.getPlatform().openUri("https://discord.gg/QGwFvvMQCn")).bounds(this.width - 110, this.height - 30, 100, 20).build();

		this.addRenderableWidget(buttonWidget);
		this.addRenderableWidget(donateButton);
		this.addRenderableWidget(discordButton);
	}
}