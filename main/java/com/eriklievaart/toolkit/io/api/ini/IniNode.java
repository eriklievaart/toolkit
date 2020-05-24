package com.eriklievaart.toolkit.io.api.ini;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.ToString;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckCollection;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;

public class IniNode {
	public static final String IDENTIFIER_REGEX = "[A-Za-z0-9_.]++";

	private final String name;
	private final String identifier;
	private int line = -1;

	private Map<String, String> properties = new Hashtable<>();
	private List<IniNode> children = new ArrayList<>();

	public IniNode(String name) {
		this(name, null);
	}

	public IniNode(String name, String identifier) {
		Check.matches(name, IDENTIFIER_REGEX);
		if (identifier != null) {
			Check.matches(identifier, IDENTIFIER_REGEX);
		}
		this.name = name;
		this.identifier = identifier;
	}

	public String getName() {
		return name;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void addChild(IniNode child) {
		children.add(child);
	}

	/**
	 * Verifies that a child with the supplied name exists. Use slashes to search for a nested path of children.
	 */
	public boolean hasChild(String named) {
		Check.notNull(named);
		IniNodePath path = new IniNodePath(named);
		if (path.isNested()) {
			return hasChild(path.getHead()) && getChild(path.getHead()).hasChild(path.getTail());
		}
		return getChildren(named).size() > 0;
	}

	public List<IniNode> getChildren() {
		return Collections.unmodifiableList(children);
	}

	public List<IniNode> getChildren(String childName) {
		List<IniNode> result = NewCollection.list();
		for (IniNode child : children) {
			if (child.getName().equals(childName)) {
				result.add(child);
			}
		}
		return result;
	}

	/**
	 * Get the child with the specified name. Can retrieve child from nested nodes using slashes '/'.
	 *
	 * @param key
	 *                'tim' to retrieve the child named tim from this node, tom/tim to retrieve the child tim nested in
	 *                child tom.
	 * @return the node found.
	 * @throws AssertionException
	 *                                if the node does not exist
	 */
	public IniNode getChild(String childName) {
		IniNodePath path = new IniNodePath(childName);
		if (path.isNested()) {
			return getChild(path.getHead()).getChild(path.getTail());
		}
		return getDirectChild(childName);
	}

	private IniNode getDirectChild(String childName) {
		List<IniNode> matches = getChildren(childName);
		String message = "% expected exactly once $[$] => $";
		CheckCollection.isSize(matches, 1, message, childName, this.name, identifier, matches);
		return matches.iterator().next();
	}

	public Set<String> getPropertyNames() {
		return Collections.unmodifiableSet(properties.keySet());
	}

	/**
	 * Get the property with the specified key. Can retrieve properties from nested nodes using slashes '/'.
	 *
	 * @param key
	 *                property identified 'type' to retrieve the type property from this node, mainboard/type to
	 *                retrieve the type property from the mainboard child node.
	 * @throws AssertionException
	 *                                when property was not found
	 */
	public String getProperty(String key) {
		IniNodePath path = new IniNodePath(key);
		if (path.isNested()) {
			if (hasChild(path.getHead())) {
				return getChild(path.getHead()).getProperty(path.getTail());
			} else {
				throw new AssertionException("missing property %", key);
			}
		} else {
			return properties.get(key);
		}
	}

	public String getPropertyOrDefault(String key, String fallback) {
		String result = getProperty(key);
		return result == null ? fallback : result;
	}

	public void ifProperty(String key, Consumer<String> consumer) {
		String value = getProperty(key);
		if (value != null) {
			consumer.accept(value);
		}
	}

	public boolean hasProperty(String key) {
		AtomicBoolean result = new AtomicBoolean();
		ifProperty(key, p -> result.set(true));
		return result.get();
	}

	public Map<String, String> getPropertiesMap() {
		return Collections.unmodifiableMap(properties);
	}

	public void setProperty(String key, String value) {
		Check.matches(key, IDENTIFIER_REGEX);
		Check.notNull(value, "Value cannot be null for: " + key);
		Check.isFalse(value.contains("\n"), "Property $.$ may not contain the newline character", name, key);
		properties.put(key, value);
	}

	public String deleteProperty(String key) {
		return properties.remove(key);
	}

	public IniNode createPath(String create) {
		IniNodePath path = new IniNodePath(create);
		IniNode child = getOrCreateChild(path.getHead());
		return path.isNested() ? child.createPath(path.getTail()) : child;
	}

	private IniNode getOrCreateChild(String named) {
		if (hasChild(named)) {
			return getChild(named);
		}
		IniNode child = new IniNode(named);
		addChild(child);
		return child;
	}

	public int getLineNumber() {
		return line;
	}

	public void setLineNumber(int line) {
		this.line = line;
	}

	@Override
	public String toString() {
		if (line > 0) {
			return ToString.simple(this, "$[$:$]", line, name);
		}
		return ToString.simple(this, "$[$]", name);
	}
}