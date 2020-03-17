package com.eriklievaart.toolkit.reflect.api;

import java.io.Serializable;
import java.util.Hashtable;

import javax.swing.JLabel;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.MapTool;
import com.eriklievaart.toolkit.mock.Bomb;
import com.eriklievaart.toolkit.mock.BombSquad;
import com.eriklievaart.toolkit.reflect.api.BeanProperties;

public class BeanPropertiesU {

	@Test
	public void resolveExpression() {
		Object resolved = BeanProperties.resolveProperty("card", MapTool.of("card", "Royal Assassin"));
		Check.isEqual(resolved, "Royal Assassin");
	}

	// Accessing fields is bad programming practice here.
	@Test
	public void resolveExpressionField() {
		BombSquad.diffuse(AssertionException.class, "bean.x", new Bomb() {
			@Override
			public void explode() throws Exception {
				class Dummy implements Serializable {
					@SuppressWarnings("unused")
					private final int x = 12;
				}
				BeanProperties.resolveProperty("bean.x", MapTool.of("bean", new Dummy()));
			}
		});
	}

	@Test
	public void resolveExpressionMissingBean() {
		BombSquad.diffuse(AssertionException.class, "bogus", new Bomb() {
			@Override
			public void explode() throws Exception {
				BeanProperties.resolveProperty("bogus", MapTool.of("card", "Necromancy"));
			}
		});
	}

	@Test
	public void resolveExpressionMissingProperty() {

		BombSquad.diffuse(AssertionException.class, "tap", new Bomb() {
			@Override
			public void explode() throws Exception {
				BeanProperties.resolveProperty("card.tap", MapTool.of("card", "Elvish Archer"));
			}
		});
	}

	@Test
	public void resolveExpressionProperty() {
		Object resolved = BeanProperties.resolveProperty("label.text", MapTool.of("label", new JLabel("Equillibrium")));
		Check.isEqual(resolved, "Equillibrium");
	}

	@Test
	public void resolveExpressionNested() {
		Object resolved = BeanProperties.resolveProperty("reflect.class.name", MapTool.of("reflect", "A string"));
		Check.isEqual(resolved, "java.lang.String");
	}

	@Test
	public void resolveSecondary() {
		Hashtable<String, String> empty = new Hashtable<String, String>();

		Object resolved = BeanProperties.resolveProperty("card", empty, MapTool.of("card", "Tundra"));
		Check.isEqual(resolved, "Tundra");
	}
}