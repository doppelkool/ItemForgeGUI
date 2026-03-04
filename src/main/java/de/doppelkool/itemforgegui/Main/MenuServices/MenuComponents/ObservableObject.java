package de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ObservableObject<T> {

	private T value;
	private final List<Consumer<T>> listeners = new ArrayList<>();

	public ObservableObject(T initial) {
		this.value = initial;
	}

	public void set(T newValue) {
		this.value = newValue;
		listeners.forEach(l -> l.accept(newValue));
	}

	public T get() {
		return value;
	}

	public void onChange(Consumer<T> listener) {
		listeners.add(listener);
	}

}
