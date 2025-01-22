package me.craftinators.barium;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.regex.Pattern;

// Cache for UUIDs to prevent generating multiple UUIDs for the same texture.
public final class Base64 {
    // Regular expression to validate Base64 strings.
    private static final Predicate<String> BASE_64_REGEX = Pattern.compile(
            "^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$"
    ).asMatchPredicate();

    // Regular expression to validate Minecraft texture URLs.
    private static final Predicate<String> TEXTURE_URL_REGEX = Pattern.compile(
            "^http://textures\\.minecraft\\.net/texture/[a-f0-9]{64}$"
    ).asMatchPredicate();

    private static final Map<String, UUID> UUID_CACHE = new HashMap<>();

    private Base64() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    /**
     * Retrieves a player head {@link ItemStack} with a custom texture.
     * The texture can either be a valid Base64-encoded string or a texture URL.
     * If a texture URL is provided, then it will be first parsed into the appropriate JSON format before being
     * converted into Base64
     * <pre>{@code
     *   "textures": {
     *     "SKIN":{
     *       "url": "http://textures.minecraft.net/texture/<your-texture-hash>"
     *     }
     *   }
     * }</pre>
     *
     * @param textureData The Base64 string or texture URL.
     * @return A player head {@link ItemStack} with the specified texture.
     * @throws IllegalArgumentException If the provided texture data is an invalid Base64 or texture URL.
     */
    public @NotNull ItemStack getTexturedHead(@NotNull String textureData) {
        // Check if the textureData is a valid texture URL or Base64, and handle accordingly.
        if (TEXTURE_URL_REGEX.test(textureData)) {
            return createHeadFromURL(textureData);
        }

        if (BASE_64_REGEX.test(textureData)) {
            return createHeadFromBase64(textureData);
        }

        // Throw an exception if the input format is invalid.
        throw new IllegalArgumentException("Provided texture data is neither a valid Base64 string nor a texture URL.");
    }

    private static @NotNull ItemStack createHeadFromURL(@NotNull String textureURL) {
        // Convert the texture URL into a JSON structure and encode it in Base64.
        String textureJSON = buildTextureJson(textureURL);
        String base64 = java.util.Base64.getEncoder().encodeToString(textureJSON.getBytes(StandardCharsets.UTF_8));

        // Delegate to the Base64-based head creation method.
        return createHeadFromBase64(base64);
    }

    private static @NotNull ItemStack createHeadFromBase64(@NotNull String base64Texture) {
        // Create the player head item.
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();

        // Retrieve or generate a UUID for the texture.
        UUID uuid = UUID_CACHE.computeIfAbsent(base64Texture, key -> UUID.randomUUID());

        // Create a player profile with the texture.
        PlayerProfile playerProfile = Bukkit.createProfile(uuid, null);
        playerProfile.setProperty(new ProfileProperty("textures", base64Texture));

        // Apply the player profile to the head's metadata.
        skullMeta.setPlayerProfile(playerProfile);
        playerHead.setItemMeta(skullMeta);

        return playerHead;
    }

    private static @NotNull String buildTextureJson(@NotNull String url) {
        return "{\"textures\":{\"SKIN\":{\"url\":\"" +
                url +
                "\"}}}";
    }
}
