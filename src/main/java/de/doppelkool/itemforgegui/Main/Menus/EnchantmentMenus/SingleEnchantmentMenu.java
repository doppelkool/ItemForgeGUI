package de.doppelkool.itemforgegui.Main.Menus.EnchantmentMenus;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ItemInfoManager;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuComponents.EditNumberMenu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuComponents.SignNumberEditor;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Submenu as part of the main function of this plugin.
 * It provides the way to add a single enchantment with a defined strength to the item.
 * The enchantment can be unsafe
 *
 * @author doppelkool | github.com/doppelkool
 */
public class SingleEnchantmentMenu extends EditNumberMenu {
	private final ItemStack itemToBeEnchanted;
	private final ItemMeta itemToBeEnchantedMeta;
	private final Enchantment enchantmentToEdit;

	public SingleEnchantmentMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
		this.itemToBeEnchanted = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
		this.itemToBeEnchantedMeta = itemToBeEnchanted.getItemMeta();
		this.enchantmentToEdit = this.playerMenuUtility.getTargetEnchantment();
	}

	@Override
	public String getMenuName() {
		return ItemStackHelper.formatCAPSName(enchantmentToEdit.getTranslationKey());
	}

	@Override
	protected void handleBack() {
		if (itemToBeEnchantedMeta.hasEnchants()) {
			new ActivatedEnchantmentsMenu(this.playerMenuUtility)
				.open();
		} else {
			new DeactivatedEnchantmentsMenu(this.playerMenuUtility)
				.open();
		}
	}

	@Override
	protected void handleToZero() {
		itemToBeEnchantedMeta.removeEnchant(enchantmentToEdit);
		itemToBeEnchanted.setItemMeta(itemToBeEnchantedMeta);
		new ItemInfoManager(itemToBeEnchanted).updateItemInfo();
	}

	@Override
	protected void handleToMax() {
		itemToBeEnchantedMeta.addEnchant(enchantmentToEdit, 255, true);
		itemToBeEnchanted.setItemMeta(itemToBeEnchantedMeta);
		new ItemInfoManager(itemToBeEnchanted).updateItemInfo();
	}

	@Override
	protected void handleMinus100() {

		if (itemToBeEnchantedMeta.getEnchantLevel(enchantmentToEdit) - 100 <= 0) {
			itemToBeEnchantedMeta.removeEnchant(enchantmentToEdit);
			itemToBeEnchanted.setItemMeta(itemToBeEnchantedMeta);
			new ItemInfoManager(itemToBeEnchanted).updateItemInfo();
			return;
		}

		int enchantLevel = itemToBeEnchantedMeta.getEnchantLevel(enchantmentToEdit);
		int targetLevel = Math.max(enchantLevel - 100, 0);
		itemToBeEnchantedMeta.addEnchant(enchantmentToEdit, targetLevel, true);
		itemToBeEnchanted.setItemMeta(itemToBeEnchantedMeta);
		new ItemInfoManager(itemToBeEnchanted).updateItemInfo();
	}

	@Override
	protected void handleMinus10() {

		if (itemToBeEnchantedMeta.getEnchantLevel(enchantmentToEdit) - 10 <= 0) {
			itemToBeEnchantedMeta.removeEnchant(enchantmentToEdit);
			itemToBeEnchanted.setItemMeta(itemToBeEnchantedMeta);
			new ItemInfoManager(itemToBeEnchanted).updateItemInfo();
			return;
		}

		int enchantLevel = itemToBeEnchantedMeta.getEnchantLevel(enchantmentToEdit);
		int targetLevel = Math.max(enchantLevel - 10, 0);
		itemToBeEnchantedMeta.addEnchant(enchantmentToEdit, targetLevel, true);
		itemToBeEnchanted.setItemMeta(itemToBeEnchantedMeta);
		new ItemInfoManager(itemToBeEnchanted).updateItemInfo();
	}

	@Override
	protected void handleMinus1() {

		if (itemToBeEnchantedMeta.getEnchantLevel(enchantmentToEdit) - 1 <= 0) {
			itemToBeEnchantedMeta.removeEnchant(enchantmentToEdit);
			itemToBeEnchanted.setItemMeta(itemToBeEnchantedMeta);
			new ItemInfoManager(itemToBeEnchanted).updateItemInfo();
			return;
		}

		int enchantLevel = itemToBeEnchantedMeta.getEnchantLevel(enchantmentToEdit);
		int targetLevel = Math.max(enchantLevel - 1, 0);
		itemToBeEnchantedMeta.addEnchant(enchantmentToEdit, targetLevel, true);
		itemToBeEnchanted.setItemMeta(itemToBeEnchantedMeta);
		new ItemInfoManager(itemToBeEnchanted).updateItemInfo();
	}

	@Override
	protected void handlePlus1() {
		int enchantLevel = itemToBeEnchantedMeta.getEnchantLevel(enchantmentToEdit);
		itemToBeEnchantedMeta.addEnchant(enchantmentToEdit, enchantLevel + 1, true);
		itemToBeEnchanted.setItemMeta(itemToBeEnchantedMeta);
		new ItemInfoManager(itemToBeEnchanted).updateItemInfo();
	}

	@Override
	protected void handlePlus10() {
		int enchantLevel = itemToBeEnchantedMeta.getEnchantLevel(enchantmentToEdit);
		itemToBeEnchantedMeta.addEnchant(enchantmentToEdit, enchantLevel + 10, true);
		itemToBeEnchanted.setItemMeta(itemToBeEnchantedMeta);
		new ItemInfoManager(itemToBeEnchanted).updateItemInfo();
	}

	@Override
	protected void handlePlus100() {
		int enchantLevel = itemToBeEnchantedMeta.getEnchantLevel(enchantmentToEdit);
		itemToBeEnchantedMeta.addEnchant(enchantmentToEdit, enchantLevel + 100, true);
		itemToBeEnchanted.setItemMeta(itemToBeEnchantedMeta);
		new ItemInfoManager(itemToBeEnchanted).updateItemInfo();
	}

	@Override
	protected void handleCustomNumber(InventoryClickEvent e) {
		playerMenuUtility.getOwner().closeInventory();
		playerMenuUtility.setSignNumberEditor(new SignNumberEditor(playerMenuUtility.getOwner())
			.editEnchantment(itemToBeEnchanted, enchantmentToEdit)
			.openSign());
		MessageManager.message(playerMenuUtility.getOwner(), "sign-editor.edit.enchantment.information");
	}
}
