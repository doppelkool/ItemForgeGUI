package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuComponents.EditNumberMenu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuComponents.SignNumberEditor;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class DurabilityMenu extends EditNumberMenu {

	private final ItemStack damageableItem;
	private final Damageable damageableItemMeta;
	private final short damageableItemMaxDurability;

	public DurabilityMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
		this.damageableItem = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
		this.damageableItemMeta = (Damageable) damageableItem.getItemMeta();
		this.damageableItemMaxDurability = damageableItem.getType().getMaxDurability();
	}

	@Override
	public String getMenuName() {
		return "Edit Durability";
	}

	@Override
	protected void handleToZero() {
		damageableItemMeta.setDamage(damageableItemMaxDurability);
		damageableItem.setItemMeta(damageableItemMeta);
	}

	@Override
	protected void handleToMax() {
		damageableItemMeta.setDamage(0);
		damageableItem.setItemMeta(damageableItemMeta);
	}

	@Override
	protected void handleMinus100() {
		damageableItemMeta.setDamage(
			clampDurability(
				damageableItemMeta.getDamage() + 100));
		damageableItem.setItemMeta(damageableItemMeta);
	}

	@Override
	protected void handleMinus10() {
		damageableItemMeta.setDamage(
			clampDurability(
				damageableItemMeta.getDamage() + 10));
		damageableItem.setItemMeta(damageableItemMeta);
	}

	@Override
	protected void handleMinus1() {
		damageableItemMeta.setDamage(
			clampDurability(
				damageableItemMeta.getDamage() + 1));
		damageableItem.setItemMeta(damageableItemMeta);
	}

	@Override
	protected void handlePlus1() {
		damageableItemMeta.setDamage(
			clampDurability(
				damageableItemMeta.getDamage() - 1));
		damageableItem.setItemMeta(damageableItemMeta);
	}

	@Override
	protected void handlePlus10() {
		damageableItemMeta.setDamage(
			clampDurability(
				damageableItemMeta.getDamage() - 10));
		damageableItem.setItemMeta(damageableItemMeta);
	}

	@Override
	protected void handlePlus100() {
		damageableItemMeta.setDamage(
			clampDurability(
				damageableItemMeta.getDamage() - 100));
		damageableItem.setItemMeta(damageableItemMeta);
	}

	@Override
	protected void handleCustomNumber(InventoryClickEvent e) {
		String message = Main.prefix + "\n" +
			ChatColor.GRAY + "-" + ChatColor.GRAY + " Please edit the content to the items future durability and click \"Done\".";

		playerMenuUtility.getOwner().closeInventory();
		Damageable itemMeta = (Damageable) this.damageableItem.getItemMeta();
		playerMenuUtility.setSignNumberEditor(new SignNumberEditor(playerMenuUtility.getOwner())
			.editDurability(
				this.damageableItem.getType().getMaxDurability() - itemMeta.getDamage(),
				this.damageableItem.getType().getMaxDurability())
			.openSign());
		playerMenuUtility.getOwner().sendMessage(message);
	}

	private int clampDurability(int durability) {
		// ensure 0 <= durability <= MAX
		return Math.max(0, Math.min(durability, damageableItemMaxDurability));
	}
}
