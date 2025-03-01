package de.doppelkool.itemforgegui.Main.Menus.ArmorEffectMenus;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ArmorEffectManager;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuComponents.EditNumberMenu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuComponents.SignNumberEditor;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class SinglePotionEffectTypeMenu extends EditNumberMenu {
	private final ItemStack itemToBeEnchanted;
	private final PotionEffectType potionEffectToEdit;

	public SinglePotionEffectTypeMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
		this.itemToBeEnchanted = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
		this.potionEffectToEdit = this.playerMenuUtility.getTargetPotionEffectType();
	}

	@Override
	public String getMenuName() {
		return ItemStackHelper.formatCAPSName(potionEffectToEdit.getTranslationKey());
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
	}

	@Override
	protected void handleToMax() {
		ArmorEffectManager.addArmorEffect(itemToBeEnchanted, potionEffectToEdit, 255);
	}

	@Override
	protected void handleMinus100() {
		Integer strength = ArmorEffectManager.getArmorEffect(itemToBeEnchanted, potionEffectToEdit);

		if(strength == null) return;

		if (strength <= 100) {
			ArmorEffectManager.removeArmorEffect(itemToBeEnchanted, potionEffectToEdit);
		} else {
			ArmorEffectManager.addArmorEffect(itemToBeEnchanted, potionEffectToEdit, strength - 100);
		}
	}

	@Override
	protected void handleMinus10() {
		Integer strength = ArmorEffectManager.getArmorEffect(itemToBeEnchanted, potionEffectToEdit);

		if(strength == null) return;

		if (strength <= 10) {
			ArmorEffectManager.removeArmorEffect(itemToBeEnchanted, potionEffectToEdit);
		} else {
			ArmorEffectManager.addArmorEffect(itemToBeEnchanted, potionEffectToEdit, strength - 10);
		}
	}

	@Override
	protected void handleMinus1() {
		Integer strength = ArmorEffectManager.getArmorEffect(itemToBeEnchanted, potionEffectToEdit);

		if(strength == null) return;

		if (strength <= 1) {
			ArmorEffectManager.removeArmorEffect(itemToBeEnchanted, potionEffectToEdit);
		} else {
			ArmorEffectManager.addArmorEffect(itemToBeEnchanted, potionEffectToEdit, strength - 1);
		}
	}

	@Override
	protected void handlePlus1() {
		Integer strength = ArmorEffectManager.getArmorEffect(itemToBeEnchanted, potionEffectToEdit);
		ArmorEffectManager.addArmorEffect(itemToBeEnchanted, potionEffectToEdit, strength == null ? 1 : strength + 1);
	}

	@Override
	protected void handlePlus10() {
		Integer strength = ArmorEffectManager.getArmorEffect(itemToBeEnchanted, potionEffectToEdit);
		ArmorEffectManager.addArmorEffect(itemToBeEnchanted, potionEffectToEdit, strength == null ? 10 : strength + 10);
	}

	@Override
	protected void handlePlus100() {
		Integer strength = ArmorEffectManager.getArmorEffect(itemToBeEnchanted, potionEffectToEdit);
		ArmorEffectManager.addArmorEffect(itemToBeEnchanted, potionEffectToEdit, strength == null ? 100 : strength + 100);
	}

	@Override
	protected void handleCustomNumber(InventoryClickEvent e) {
		String message = Main.prefix + "\n" +
			ChatColor.GRAY + "-" + ChatColor.GRAY + " Please edit the content to the armor effects future strength and click \"Done\".";

		playerMenuUtility.getOwner().closeInventory();
		playerMenuUtility.setSignNumberEditor(new SignNumberEditor(playerMenuUtility.getOwner())
			.editPotionEffect(itemToBeEnchanted, potionEffectToEdit)
			.openSign());
		playerMenuUtility.getOwner().sendMessage(message);
	}
}
