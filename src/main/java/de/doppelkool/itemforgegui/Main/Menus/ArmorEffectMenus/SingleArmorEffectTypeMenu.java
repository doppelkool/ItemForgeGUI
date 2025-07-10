package de.doppelkool.itemforgegui.Main.Menus.ArmorEffectMenus;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ArmorEffectManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ItemInfoManager;
import de.doppelkool.itemforgegui.Main.MenuComponents.EditNumberMenu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuComponents.SignNumberEditor;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class SingleArmorEffectTypeMenu extends EditNumberMenu {
	private final ItemStack itemToBeEnchanted;
	private final PotionEffectType potionEffectToEdit;

	public SingleArmorEffectTypeMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
		this.itemToBeEnchanted = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
		this.potionEffectToEdit = this.playerMenuUtility.getTargetPotionEffectType();
	}

	@Override
	public String getMenuName() {
		return ItemStackModifyHelper.formatCAPSName(potionEffectToEdit.getTranslationKey());
	}

	@Override
	protected void handleBack() {
		if (ArmorEffectManager.hasArmorEffects(itemToBeEnchanted)) {
			new SpecialsActivatedArmorEffectsMenu(this.playerMenuUtility)
				.open();
		} else {
			new SpecialsDeactivatedArmorEffectsMenu(this.playerMenuUtility)
				.open();
		}
	}

	@Override
	protected void handleToZero() {
		ArmorEffectManager.removeArmorEffect(itemToBeEnchanted, potionEffectToEdit);

		new ItemInfoManager(itemToBeEnchanted).updateItemInfo();
	}

	@Override
	protected void handleToMax() {
		ArmorEffectManager.addArmorEffect(itemToBeEnchanted, potionEffectToEdit, 255);

		new ItemInfoManager(itemToBeEnchanted).updateItemInfo();
	}

	@Override
	protected void handleMinus100() {
		Integer strength = ArmorEffectManager.getArmorEffect(itemToBeEnchanted, potionEffectToEdit);

		if (strength == null) return;

		if (strength <= 100) {
			ArmorEffectManager.removeArmorEffect(itemToBeEnchanted, potionEffectToEdit);
		} else {
			ArmorEffectManager.addArmorEffect(itemToBeEnchanted, potionEffectToEdit, strength - 100);
		}

		new ItemInfoManager(itemToBeEnchanted).updateItemInfo();
	}

	@Override
	protected void handleMinus10() {
		Integer strength = ArmorEffectManager.getArmorEffect(itemToBeEnchanted, potionEffectToEdit);

		if (strength == null) return;

		if (strength <= 10) {
			ArmorEffectManager.removeArmorEffect(itemToBeEnchanted, potionEffectToEdit);
		} else {
			ArmorEffectManager.addArmorEffect(itemToBeEnchanted, potionEffectToEdit, strength - 10);
		}

		new ItemInfoManager(itemToBeEnchanted).updateItemInfo();
	}

	@Override
	protected void handleMinus1() {
		Integer strength = ArmorEffectManager.getArmorEffect(itemToBeEnchanted, potionEffectToEdit);

		if (strength == null) return;

		if (strength <= 1) {
			ArmorEffectManager.removeArmorEffect(itemToBeEnchanted, potionEffectToEdit);
		} else {
			ArmorEffectManager.addArmorEffect(itemToBeEnchanted, potionEffectToEdit, strength - 1);
		}

		new ItemInfoManager(itemToBeEnchanted).updateItemInfo();
	}

	@Override
	protected void handlePlus1() {
		Integer strength = ArmorEffectManager.getArmorEffect(itemToBeEnchanted, potionEffectToEdit);

		if (strength != null && strength == 255) {
			return;
		}

		ArmorEffectManager.addArmorEffect(
			itemToBeEnchanted,
			potionEffectToEdit,
			strength == null
				? 1
				: Integer.min(strength + 1, 255));

		new ItemInfoManager(itemToBeEnchanted).updateItemInfo();
	}

	@Override
	protected void handlePlus10() {
		Integer strength = ArmorEffectManager.getArmorEffect(itemToBeEnchanted, potionEffectToEdit);

		if (strength != null && strength == 255) {
			return;
		}

		ArmorEffectManager.addArmorEffect(
			itemToBeEnchanted,
			potionEffectToEdit,
			strength == null
				? 10
				: Integer.min(strength + 10, 255));

		new ItemInfoManager(itemToBeEnchanted).updateItemInfo();
	}

	@Override
	protected void handlePlus100() {
		Integer strength = ArmorEffectManager.getArmorEffect(itemToBeEnchanted, potionEffectToEdit);

		if (strength != null && strength == 255) {
			return;
		}

		ArmorEffectManager.addArmorEffect(
			itemToBeEnchanted,
			potionEffectToEdit,
			strength == null
				? 100
				: Integer.min(strength + 100, 255));

		new ItemInfoManager(itemToBeEnchanted).updateItemInfo();
	}

	@Override
	protected void handleCustomNumber(InventoryClickEvent e) {
		playerMenuUtility.getOwner().closeInventory();
		playerMenuUtility.setSignNumberEditor(new SignNumberEditor(playerMenuUtility.getOwner())
			.editPotionEffect(itemToBeEnchanted, potionEffectToEdit)
			.openSign());
		MessageManager.message(playerMenuUtility.getOwner(), Messages.SIGN_EDITOR_EDIT_ARMOR_EFFECT_INFORMATION);
	}
}
