package com.faboslav.friendsandfoes.util;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.platform.ModVersion;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.SharedConstants;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public final class UpdateChecker
{
	private static final Gson gson = new Gson();

	public static void checkForNewUpdates() {
		CompletableFuture.runAsync(() -> {
			if (FriendsAndFoes.getConfig().checkForNewUpdates) {
				String latestVersion = UpdateChecker.getLatestVersion();

				if (latestVersion == null) {
					return;
				}

				String modVersion = ModVersion.getModVersion();


				if (latestVersion.equals(modVersion) == false) {
					FriendsAndFoes.getLogger().info(
						"[Friends&Foes] An update is available! You're using {} version but the latest version is {}!",
						modVersion,
						latestVersion
					);
				}
			}
		});
	}

	@Nullable
	public static String getLatestVersion() {
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

		String latestModVersion = json.get(gameVersion).getAsString();

		return latestModVersion;
	}
}

