package de.doppelkool.itemforgegui.Commands;

import de.doppelkool.itemforgegui.Main.MenuComponents.MenuManager;
import de.doppelkool.itemforgegui.Main.Menus.ItemEditMenu;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
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
			MessageManager.message(sender, Messages.EDIT_COMMAND_AS_CONSOLE);
			return true;
		}

		if (!pl.hasPermission("ifgui.use")) {
			MessageManager.message(pl, Messages.EDIT_COMMAND_NO_PERMISSIONS);
			return true;
		}

		if (pl.getInventory()
			.getItemInMainHand()
			.getType() == Material.AIR) {
			MessageManager.message(pl, Messages.EDIT_COMMAND_EMPTY_ITEM);
			return true;
		}

		new ItemEditMenu(MenuManager.getPlayerMenuUtility(pl))
			.open();

		return true;
	}
}
