package de.doppelkool.itemforgegui.Main.MenuComponents;

import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class MenuManager {
	private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();

	public static PlayerMenuUtility getPlayerMenuUtility(Player pl) {
		PlayerMenuUtility playerMenuUtility;
		if (!(playerMenuUtilityMap.containsKey(pl))) {

			playerMenuUtility = new PlayerMenuUtility(pl);
			playerMenuUtilityMap.put(pl, playerMenuUtility);

			return playerMenuUtility;
		}
		return playerMenuUtilityMap.get(pl);
	}

	public static void removePlayerMenuUtility(Player pl) {
		playerMenuUtilityMap.remove(pl);
	}

}
