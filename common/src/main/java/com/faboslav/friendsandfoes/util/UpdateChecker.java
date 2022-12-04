package com.faboslav.friendsandfoes.util;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.platform.ModVersion;
import com.faboslav.friendsandfoes.platform.ProjectUrlProvider;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
			URI.create("https://raw.githubusercontent.com/Faboslav/friends-and-foes/master/.github/versions.json")
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

		JsonObject json = gson.fromJson(response.body(), JsonObject.class);

		String gameVersion = SharedConstants.getGameVersion().getName();

		if (json.has(gameVersion) == false) {
			return null;
		}

		return Version.parse(json.get(gameVersion).getAsString());
	}
}

