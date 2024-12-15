package com.faboslav.friendsandfoes.common.util;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.platform.ModVersion;
import com.faboslav.friendsandfoes.common.platform.Platform;
import com.faboslav.friendsandfoes.common.platform.ProjectUrlProvider;
import com.google.gson.*;
import net.minecraft.SharedConstants;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.module.ModuleDescriptor.Version;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public final class UpdateChecker
{
	private static final Gson gson = new Gson();

	public static void checkForNewUpdatesInGame(LocalPlayer clientPlayerEntity) {
		CompletableFuture.runAsync(() -> {
			if (!FriendsAndFoes.getConfig().checkForNewUpdates) {
				return;
			}

			Version latestVersion = UpdateChecker.getLatestVersion();
			if (latestVersion == null) {
				return;
			}

			String modVersion = ModVersion.getModVersion();
			if (modVersion == null) {
				return;
			}

			if (latestVersion.compareTo(Version.parse(modVersion)) <= 0) {
				return;
			}

			String updateMessageString = MessageFormat.format(
				"Friend&Foes update is available! You are using {0} version, but the latest version is {1}. You can download it at ",
				modVersion,
				latestVersion.toString()
			);
			MutableComponent updateMessage = Component.literal(updateMessageString);
			MutableComponent curseforgeLink = Component.literal("CurseForge").withStyle(arg -> arg.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, ProjectUrlProvider.getCurseForgeProjectLink())).withUnderlined(true));
			MutableComponent updateMessageSecondPart = Component.literal(" or ");
			MutableComponent modrinthLink = Component.literal("Modrinth").withStyle(arg -> arg.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, ProjectUrlProvider.getModrinthProjectLink())).withUnderlined(true));
			MutableComponent updateMessageThirdPart = Component.literal(".");
			updateMessage.append(curseforgeLink).append(updateMessageSecondPart).append(modrinthLink).append(updateMessageThirdPart);
			clientPlayerEntity.displayClientMessage(updateMessage, false);
			FriendsAndFoes.getLogger().info(
				"[Friends&Foes] An update is available! You are using {} version, but the latest version is {}.",
				modVersion,
				latestVersion.toString()
			);
		});
	}

	public static void checkForNewUpdates() {
		CompletableFuture.runAsync(() -> {
			if (!FriendsAndFoes.getConfig().checkForNewUpdates) {
				return;
			}

			Version latestVersion = UpdateChecker.getLatestVersion();
			if (latestVersion == null) {
				return;
			}

			String modVersion = ModVersion.getModVersion();
			if (modVersion == null) {
				return;
			}

			if (latestVersion.compareTo(Version.parse(modVersion)) <= 0) {
				return;
			}

			FriendsAndFoes.getLogger().info(
				"[Friends&Foes] An update is available! You are using {} version, but the latest version is {}.",
				modVersion,
				latestVersion.toString()
			);
		});
	}

	@Nullable
	public static Version getLatestVersion() {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(
			URI.create("https://api.modrinth.com/v2/project/" + Platform.getProjectSlug() + "/version")
		).build();

		HttpResponse<String> response;

		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			return null;
		}

		if (response.statusCode() != 200) {
			return null;
		}

		Map<String, String> releases = new HashMap<>();

		try {
			JsonArray jsonArray = gson.fromJson(response.body(), JsonArray.class);

			for (int i = 0; i < jsonArray.size(); i++) {
				JsonObject versionObject = jsonArray.get(i).getAsJsonObject();
				String versionNumber = versionObject.get("version_number").getAsString();
				String modVersion = versionNumber.substring(versionNumber.lastIndexOf('-') + 1);
				JsonArray gameVersions = versionObject.getAsJsonArray("game_versions");

				for (JsonElement gameVersion : gameVersions) {
					if (releases.containsKey(gameVersion.getAsString())) {
						continue;
					}

					releases.put(gameVersion.getAsString(), modVersion);
				}
			}
		} catch (JsonSyntaxException e) {
			return null;
		}

		String gameVersion = SharedConstants.getCurrentVersion().getName();

		if (!releases.containsKey(gameVersion)) {
			return null;
		}

		return Version.parse(releases.get(gameVersion));
	}
}

