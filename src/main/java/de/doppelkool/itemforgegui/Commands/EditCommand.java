package de.doppelkool.itemforgegui.Commands;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuManager;
import de.doppelkool.itemforgegui.Main.Menus.ItemEditMenu;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
			sender.sendMessage(Main.prefix + "Command cannot be executed as player");
			return true;
		}

		if(!pl.hasPermission("ifgui.use")) {
			pl.sendMessage(Main.prefix + "You cannot execute this command");
			return true;
		}

		ItemStack itemInMainHand = pl.getInventory().getItemInMainHand();

		if(itemInMainHand.getType() == Material.AIR) {
			pl.sendMessage(Main.prefix + "You cannot edit an empty item");
			return true;
		}

		new ItemEditMenu(MenuManager.getPlayerMenuUtility(pl))
			.open();

		return true;
	}
}
