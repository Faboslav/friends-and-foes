package com.faboslav.friendsandfoes.util;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.platform.ModVersion;
import com.faboslav.friendsandfoes.platform.Platform;
import com.faboslav.friendsandfoes.platform.ProjectUrlProvider;
import com.google.gson.*;
import net.minecraft.SharedConstants;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
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

	public static void checkForNewUpdatesInGame(ClientPlayerEntity clientPlayerEntity) {
		CompletableFuture.runAsync(() -> {
			if (FriendsAndFoes.getConfig().checkForNewUpdates == false) {
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
			MutableText updateMessage = Text.literal(updateMessageString);
			MutableText curseforgeLink = Text.literal("CurseForge").styled(arg -> arg.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, ProjectUrlProvider.getCurseForgeProjectLink())).withUnderline(true));
			MutableText updateMessageSecondPart = Text.literal(" or ");
			MutableText modrinthLink = Text.literal("Modrinth").styled(arg -> arg.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, ProjectUrlProvider.getModrinthProjectLink())).withUnderline(true));
			MutableText updateMessageThirdPart = Text.literal(".");
			updateMessage.append(curseforgeLink).append(updateMessageSecondPart).append(modrinthLink).append(updateMessageThirdPart);
			clientPlayerEntity.sendMessage(updateMessage, false);
			FriendsAndFoes.getLogger().info(
				"[Friends&Foes] An update is available! You are using {} version, but the latest version is {}.",
				modVersion,
				latestVersion.toString()
			);
		});
	}

	public static void checkForNewUpdates() {
		CompletableFuture.runAsync(() -> {
			if (FriendsAndFoes.getConfig().checkForNewUpdates == false) {
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

		String gameVersion = SharedConstants.getGameVersion().getName();

		if (releases.containsKey(gameVersion) == false) {
			return null;
		}

		return Version.parse(releases.get(gameVersion));
	}
}

