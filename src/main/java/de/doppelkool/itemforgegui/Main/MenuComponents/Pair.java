package de.doppelkool.itemforgegui.Main.MenuComponents;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class Pair<A, B> {
	private final A a;

	private final B b;

	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}

	public final A getA() {
		return this.a;
	}

	public final B getB() {
		return this.b;
	}
}
