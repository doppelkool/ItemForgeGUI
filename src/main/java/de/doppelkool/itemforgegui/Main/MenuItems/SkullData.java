package de.doppelkool.itemforgegui.Main.MenuItems;

import lombok.Getter;

/**
 * A simple storage class to hold encoded {@link org.bukkit.block.Skull} information containing a link to its texture
 *
 * @author doppelkool | github.com/doppelkool
 */
@Getter
public enum SkullData {

	QUARTZ_CHECK("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzdiNjJkMjc1ZDg3YzA5Y2UxMGFjYmNjZjM0YzRiYTBiNWYxMzVkNjQzZGM1MzdkYTFmMWRmMzU1YTIyNWU4MiJ9fX0="),
	QUARTZ_X("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzkxZDZlZGE4M2VkMmMyNGRjZGNjYjFlMzNkZjM2OTRlZWUzOTdhNTcwMTIyNTViZmM1NmEzYzI0NGJjYzQ3NCJ9fX0="),
	QUARTZ_ARROW_RIGHT("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTNmYzUyMjY0ZDhhZDllNjU0ZjQxNWJlZjAxYTIzOTQ3ZWRiY2NjY2Y2NDkzNzMyODliZWE0ZDE0OTU0MWY3MCJ9fX0="),
	QUARTZ_ARROW_LEFT("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWYxMzNlOTE5MTlkYjBhY2VmZGMyNzJkNjdmZDg3YjRiZTg4ZGM0NGE5NTg5NTg4MjQ0NzRlMjFlMDZkNTNlNiJ9fX0="),
	REDSTONE_BLOCK_LEFT("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjZkOGVmZjRjNjczZTA2MzY5MDdlYTVjMGI1ZmY0ZjY0ZGMzNWM2YWFkOWI3OTdmMWRmNjYzMzUxYjRjMDgxNCJ9fX0="),
	REDSTONE_BLOCK_ARROW_LEFT("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTJiYTgxYjQ3ZDVlZTA2YjQ4NGVhOWJkZjIyOTM0ZTZhYmNhNWU0Y2VkN2JlMzkwNWQ2YWU2ZWNkNmZjZWEyYSJ9fX0="),

	REDSTONE_M("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQzMGIwMmIxNTVlNjY5MjczYTc3ODQwYjA3ZjQ1M2U3OWIzOTQ4OTBhNGQ2MGI0MGM3YWMzMGFhNzhmZTkzNiJ9fX0="),
	REDSTONE_ONE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTM5Yzg0NmY2NWQ1ZjI3MmE4MzlmZDljMmFlYjExYmRjOGUzZjgyMjlmYmUzNTgzNDg2ZTc4ZjRjMjNjOGI1YiJ9fX0="),
	REDSTONE_FIVE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmUxMDA4NTkyZTNhZDI0ZDY1ZGZhNGZmNWEzYzgwMGQ3OGEzZGIxMzRjYmQ4ZTllYzNjYmFjMWVhODM5MWI5ZCJ9fX0="),
	REDSTONE_TWENTY("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2M1OTdmMTRlYzE4NmQ2ZWQ1NTM3NDM1Yjk5MjFmYTlkMTQyZjRkZDNlYTBiNGE5YjliMTk2NjU0YTliN2JiNCJ9fX0="),
	REDSTONE_TEN("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmYzMjIyMmRlZjFjN2IzYmQwNDUxM2IxYTQwNDkzNDA3YzQyODdiNmVjMzk0M2Y3MzMzZjcxNTc3M2Q0Y2I2MSJ9fX0="),
	REDSTONE_FIFTY("ewogICJ0aW1lc3RhbXAiIDogMTczNzQ5MTUzNjQ2MSwKICAicHJvZmlsZUlkIiA6ICI3MzE4MWQxZDRjYWQ0ZmU0YTcxNWNjNmUxOGNjYzVkNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJaZmVybjRuZGl0byIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS84ZjYwZjNmYjM1NDg4MDY0NDI5NDU3MzY0MTIzZDRiMTgxMDVlMzE2OTIxZDhlOWFjMDE4ZGM4ZjJlMWMzOGU5IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0="),
	REDSTONE_HUNDRED("ewogICJ0aW1lc3RhbXAiIDogMTczNzU3NzA5OTU1OCwKICAicHJvZmlsZUlkIiA6ICI1MDMyYTA2NWQ5MWQ0NTgyYjZmODM0MDRlMGYyOTA4MiIsCiAgInByb2ZpbGVOYW1lIiA6ICJNYWNCb29rUHJvTTIiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTRiMTc0Yzc5ZmUyMDlhYWU3OWQ0ODBmMjBlODc2NDRiNmViMjJhZmI1ZmVmZjlmMzQzMDJjZjkxYzFhNWVhMSIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9"),

	LIME_HUNDRED("ewogICJ0aW1lc3RhbXAiIDogMTczNzU2NDE2ODcxNiwKICAicHJvZmlsZUlkIiA6ICJjYmNkNDQzZGE1NTI0OGU3ODM3NWNmZjYwMmQzZWI0NyIsCiAgInByb2ZpbGVOYW1lIiA6ICJPX1JlaSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS80MWVlOGYyYmYxYTMzOWQwOWFmZWEzODc4NTJiZjc0NWY1NGRiM2YyZTc2ZjIxMDRkYjQ0ZjljNjg0NzcyOGRmIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0="),
	LIME_FIFTY("ewogICJ0aW1lc3RhbXAiIDogMTczNzU2NDY4MDIyNywKICAicHJvZmlsZUlkIiA6ICJjNmViMzdjNmE4YjM0MDI3OGJjN2FmZGE3ZjMxOWJmMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJFbFJleUNhbGFiYXphbCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82ZDkyODVmNmU5MGYyOGFjYTE4MWI5ZWIyOTEwMjc0MTg1NGU4MTc2NDZlN2JkYjg5NmM5OTM2MDNkZjkzZDEwIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0="),
	LIME_TEN("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGJmNjhjNzMxMDg3NTI2N2VlYzJhYzVhYTZjZGIzYTkxZjIyZDU5OThhMzgwYTRjZWM5MmFhZmFmNmQ3MCJ9fX0="),
	LIME_TWENTY("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzk5NzY3OTlmNDY5OGY3Zjc4YjQ1Yjg3NGVmZTFhMTBjZjhkOTI4NGNmMmYzYTRlNWY5OGVjMjdhZmJhN2ZjOCJ9fX0="),
	LIME_FIVE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTJjMTQyYWY1OWYyOWViMzVhYjI5YzZhNDVlMzM2MzVkY2ZjMmE5NTZkYmQ0ZDJlNTU3MmIwZDM4ODkxYjM1NCJ9fX0="),
	LIME_ONE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODg5OTE2OTc0Njk2NTNjOWFmODM1MmZkZjE4ZDBjYzljNjc3NjNjZmU2NjE3NWMxNTU2YWVkMzMyNDZjNyJ9fX0="),
	LIME_M("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDhhNGFiZTE3ODEwNDI0NTJlN2I4Njg5MTAxNzU3ZmVkNTcyODMxNmNkN2Y1Mzk1NWJlODgzMmM0ODVlYjAifX19"),

	;

	private final String base64encoded;

	SkullData(String base64encoded) {
		this.base64encoded = base64encoded;
	}
}
