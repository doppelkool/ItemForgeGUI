package de.doppelkool.itemforgegui.Commands;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuComponents.MenuManager;
import de.doppelkool.itemforgegui.Main.Menus.ItemEditMenu;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class EditCommand implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if (!(sender instanceof Player pl)) {
			//edit-command-as-console
			sender.sendMessage(Main.prefix + "Command cannot be executed as console");
			return true;
		}

		if(!pl.hasPermission("ifgui.use")) {
			//edit-command-no-permissions
			pl.sendMessage(Main.prefix + "You cannot execute this command");
			return true;
		}

		if(pl.getInventory()
				.getItemInMainHand()
				.getType() == Material.AIR) {
			//edit-command-empty-item
			pl.sendMessage(Main.prefix + "You cannot edit an empty item");
			return true;
		}

		new ItemEditMenu(MenuManager.getPlayerMenuUtility(pl))
			.open();

		return true;
	}
}
