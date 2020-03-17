package com.eriklievaart.toolkit.io.api.ini;

import java.util.Collection;
import java.util.List;

import com.eriklievaart.toolkit.io.api.ini.schema.IniSchemaException;
import com.eriklievaart.toolkit.io.api.ini.schema.IniSchemaNode;
import com.eriklievaart.toolkit.io.api.ini.schema.IniSchemaPropertyEnum;
import com.eriklievaart.toolkit.io.api.ini.schema.IniSchemaPropertyToken;
import com.eriklievaart.toolkit.io.api.ini.schema.IniSchemaPropertyTokenizer;
import com.eriklievaart.toolkit.io.api.ini.schema.IniSchemaToken;
import com.eriklievaart.toolkit.io.api.ini.schema.IniSchemaTokenizer;
import com.eriklievaart.toolkit.lang.api.FormattedException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.logging.api.LogTemplate;

public class IniNodeValidator {

	private static LogTemplate log = new LogTemplate(IniNodeValidator.class);

	public static void validate(Collection<IniNode> contentNodes, IniNode schemaNode) throws IniSchemaException {
		IniNode root = new IniNode("root");
		for (IniNode node : contentNodes) {
			root.addChild(node);
		}
		validate(root, schemaNode);
	}

	public static void validate(IniNode contentNode, IniNode schemaNode) throws IniSchemaException {
		Check.notNull(schemaNode);
		validateSingleNode(contentNode, schemaNode);

		for (IniNode child : contentNode.getChildren()) {

			if (schemaNode.hasChild(child.getName())) {
				validate(child, schemaNode.getChild(child.getName()));

			} else if (!child.getChildren().isEmpty()) {
				throw new IniSchemaException("Schema permits no children for $", child);
			}
		}
	}

	private static void validateSingleNode(IniNode contentNode, IniNode schemaNode) throws IniSchemaException {
		log.trace("Verifying $ against $", contentNode, schemaNode);

		List<IniSchemaNode> permittedChildren = getPermittedChildrenForNode(schemaNode);
		outer: for (IniNode child : contentNode.getChildren()) {
			for (IniSchemaNode permittedChild : permittedChildren) {
				if (permittedChild.match(child)) {
					String name = child.getName();
					IniNode schemaProperties = schemaNode.hasChild(name) ? schemaNode.getChild(name) : null;
					validateProperties(child, schemaProperties);
					continue outer;
				}
			}
			throw new IniSchemaException("$ does not match schema $", child, permittedChildren);
		}
		for (IniSchemaNode permitted : permittedChildren) {
			if (permitted.isNotSatisfied()) {
				throw new IniSchemaException("Missing node $", permitted);
			}
		}
	}

	private static List<IniSchemaNode> getPermittedChildrenForNode(IniNode schemaNode) {
		String nodes = schemaNode.getProperty("nodes");
		try {
			List<IniSchemaNode> permittedChildren = NewCollection.list();
			for (IniSchemaToken token : new IniSchemaTokenizer(nodes).getTokens()) {
				permittedChildren.add(new IniSchemaNode(token.getRaw(), token.getMultiplicity()));
			}
			return permittedChildren;
		} catch (RuntimeException e) {
			throw new FormattedException("Cannot create schema for $=>%", e, schemaNode, nodes);
		}
	}

	private static void validateProperties(IniNode contentNode, IniNode schemaNode) throws IniSchemaException {
		IniSchemaPropertyToken token = getSchemaPropertyToken(schemaNode);
		token.getType().verifyProperty(contentNode, token);
		verifyRequiredPropertiesExist(contentNode, schemaNode);
	}

	private static IniSchemaPropertyToken getSchemaPropertyToken(IniNode schemaNode) {
		if (schemaNode != null) {
			if (schemaNode.hasProperty("keys")) {
				String keys = schemaNode.getProperty("keys").trim();
				return IniSchemaPropertyTokenizer.parse(keys);
			}
		}
		return new IniSchemaPropertyToken(IniSchemaPropertyEnum.NONE);
	}

	private static void verifyRequiredPropertiesExist(IniNode contentNode, IniNode schemaNode) {
		if (schemaNode == null) {
			return;
		}
		if (!schemaNode.hasProperty("required")) {
			return;
		}
		IniSchemaPropertyToken required = IniSchemaPropertyTokenizer.parse(schemaNode.getProperty("required"));
		IniSchemaPropertyEnum type = required.getType();
		if (type != IniSchemaPropertyEnum.EXACT) {
			throw new FormattedException("required must be of type EXACT, but was $", type);
		}
		for (String property : required.getValueAsList()) {
			if (!contentNode.hasProperty(property)) {
				throw new IniSchemaException("node $ does not have property %", contentNode, property);
			}
		}
	}

	public static boolean isValid(IniNode contentNode, IniNode schemaNode) {
		try {
			validate(contentNode, schemaNode);
			return true;

		} catch (IniSchemaException e) {
			return false;
		}
	}
}