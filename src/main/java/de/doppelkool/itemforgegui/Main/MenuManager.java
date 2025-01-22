package de.doppelkool.itemforgegui.Main;

import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class MenuManager {
	private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();

	public static PlayerMenuUtility getPlayerMenuUtility(Player p) {
		PlayerMenuUtility playerMenuUtility;
		if (!(playerMenuUtilityMap.containsKey(p))) {

			playerMenuUtility = new PlayerMenuUtility(p);
			playerMenuUtilityMap.put(p, playerMenuUtility);

			return playerMenuUtility;
		}
		return playerMenuUtilityMap.get(p);
	}

}
