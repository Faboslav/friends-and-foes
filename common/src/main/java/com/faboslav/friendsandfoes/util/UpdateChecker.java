package com.faboslav.friendsandfoes.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.SharedConstants;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class UpdateChecker
{
	private static final Gson gson = new Gson();

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

