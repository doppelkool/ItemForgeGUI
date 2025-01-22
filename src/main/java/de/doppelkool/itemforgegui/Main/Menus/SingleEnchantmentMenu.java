package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.MenuComponents.EditNumberMenu;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper;
import de.doppelkool.itemforgegui.Main.PlayerMenuUtility;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Class Description
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
		this.playerMenuUtility.setTargetEnchantment(null);
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
	}

	@Override
	protected void handleToMax() {
		itemToBeEnchantedMeta.addEnchant(enchantmentToEdit, 255, true);
		itemToBeEnchanted.setItemMeta(itemToBeEnchantedMeta);
	}

	@Override
	protected void handleMinus100() {

		if (itemToBeEnchantedMeta.getEnchantLevel(this.enchantmentToEdit) -100 <= 0) {
			itemToBeEnchantedMeta.removeEnchant(enchantmentToEdit);
			itemToBeEnchanted.setItemMeta(itemToBeEnchantedMeta);
			return;
		}

		int enchantLevel = itemToBeEnchantedMeta.getEnchantLevel(enchantmentToEdit);
		int targetLevel = Math.max(enchantLevel - 100, 0);
		itemToBeEnchantedMeta.addEnchant(enchantmentToEdit, targetLevel, true);
		itemToBeEnchanted.setItemMeta(itemToBeEnchantedMeta);
	}

	@Override
	protected void handleMinus10() {

		if (itemToBeEnchantedMeta.getEnchantLevel(this.enchantmentToEdit) -10 <= 0) {
			itemToBeEnchantedMeta.removeEnchant(enchantmentToEdit);
			itemToBeEnchanted.setItemMeta(itemToBeEnchantedMeta);
			return;
		}

		int enchantLevel = itemToBeEnchantedMeta.getEnchantLevel(enchantmentToEdit);
		int targetLevel = Math.max(enchantLevel - 10, 0);
		itemToBeEnchantedMeta.addEnchant(enchantmentToEdit, targetLevel, true);
		itemToBeEnchanted.setItemMeta(itemToBeEnchantedMeta);
	}

	@Override
	protected void handleMinus1() {

		if (itemToBeEnchantedMeta.getEnchantLevel(this.enchantmentToEdit) -1 <= 0) {
			itemToBeEnchantedMeta.removeEnchant(enchantmentToEdit);
			itemToBeEnchanted.setItemMeta(itemToBeEnchantedMeta);
			return;
		}

		int enchantLevel = itemToBeEnchantedMeta.getEnchantLevel(enchantmentToEdit);
		int targetLevel = Math.max(enchantLevel - 1, 0);
		itemToBeEnchantedMeta.addEnchant(enchantmentToEdit, targetLevel, true);
		itemToBeEnchanted.setItemMeta(itemToBeEnchantedMeta);
	}

	@Override
	protected void handlePlus1() {
		int enchantLevel = itemToBeEnchantedMeta.getEnchantLevel(enchantmentToEdit);
		itemToBeEnchantedMeta.addEnchant(enchantmentToEdit, enchantLevel + 1, true);
		itemToBeEnchanted.setItemMeta(itemToBeEnchantedMeta);
	}

	@Override
	protected void handlePlus10() {
		int enchantLevel = itemToBeEnchantedMeta.getEnchantLevel(enchantmentToEdit);
		itemToBeEnchantedMeta.addEnchant(enchantmentToEdit, enchantLevel + 10, true);
		itemToBeEnchanted.setItemMeta(itemToBeEnchantedMeta);
	}

	@Override
	protected void handlePlus100() {
		int enchantLevel = itemToBeEnchantedMeta.getEnchantLevel(enchantmentToEdit);
		itemToBeEnchantedMeta.addEnchant(enchantmentToEdit, enchantLevel + 100, true);
		itemToBeEnchanted.setItemMeta(itemToBeEnchantedMeta);
	}
}
