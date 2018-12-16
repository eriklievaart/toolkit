package com.eriklievaart.toolkit.reflect.api;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

/**
 * This class makes it easy to read modifiers from a Member or literal. Java stores modifiers internally as XOR'ed bits.
 * This utility class allows the programmer to extract modifiers as boolean flags.
 *
 * @author Erik Lievaart
 */
public class Modifiers {

	private final int mod;

	private Modifiers(final int modifiers) {
		this.mod = modifiers;
	}

	/**
	 * public flag.
	 */
	public boolean isPublic() {
		return Modifier.isPublic(mod);
	}

	/**
	 * protected flag.
	 */
	public boolean isProtected() {
		return Modifier.isProtected(mod);
	}

	/**
	 * private flag.
	 */
	public boolean isPrivate() {
		return Modifier.isPrivate(mod);
	}

	/**
	 * package flag.
	 */
	public boolean isPackage() {
		return !isPublic() && !isProtected() && !isPrivate();
	}

	/**
	 * Returns true iff the public OR the protected flag is true.
	 */
	public boolean isPublicOrProtected() {
		return isPublic() || isProtected();
	}

	/**
	 * final flag.
	 */
	public boolean isFinal() {
		return Modifier.isFinal(mod);
	}

	/**
	 * static flag.
	 */
	public boolean isStatic() {
		return Modifier.isStatic(mod);
	}

	/**
	 * abstract flag.
	 */
	public boolean isAbstract() {
		return Modifier.isAbstract(mod);
	}

	/**
	 * Factory Method for the modifiers of a class literal.
	 */
	public static Modifiers of(final Class<?> literal) {
		return new Modifiers(literal.getModifiers());
	}

	/**
	 * Factory Method for the modifiers of a class member.
	 */
	public static Modifiers of(final Member member) {
		return new Modifiers(member.getModifiers());
	}

	public boolean containsNone(Modifiers other) {
		return (mod | other.mod) == 0;
	}
}
