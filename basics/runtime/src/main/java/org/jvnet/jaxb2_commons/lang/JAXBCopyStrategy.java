package org.jvnet.jaxb2_commons.lang;

import static org.jvnet.jaxb2_commons.locator.util.LocatorUtils.item;
import static org.jvnet.jaxb2_commons.locator.util.LocatorUtils.property;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.JAXBElement;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.w3c.dom.Node;

public class JAXBCopyStrategy extends DefaultCopyStrategy {
	@SuppressWarnings("unchecked")
	@Override
	protected Object copyInternal(ObjectLocator locator, Object object) {
		if (object instanceof Node) {
			final Node node = (Node) object;
			return copyInternal(locator, node);
		} else if (object instanceof JAXBElement) {
			final JAXBElement jaxbElement = (JAXBElement) object;
			return copyInternal(locator, jaxbElement);
		} else if (object instanceof List) {
			List list = (List) object;
			return copyInternal(locator, list);

		} else

		{
			return super.copyInternal(locator, object);
		}
	}

	protected Object copyInternal(ObjectLocator locator, final Node node) {
		return node.cloneNode(true);
	}

	@SuppressWarnings("unchecked")
	protected Object copyInternal(ObjectLocator locator,
			final JAXBElement jaxbElement) {
		final Object sourceObject = jaxbElement.getValue();
		final Object copyObject = copy(property(locator, "value", sourceObject), sourceObject);
		final JAXBElement copyElement = new JAXBElement(jaxbElement.getName(),
				jaxbElement.getDeclaredType(), jaxbElement.getScope(),
				copyObject);
		return copyElement;
	}

	@SuppressWarnings("unchecked")
	protected Object copyInternal(ObjectLocator locator, List list) {
		final List copy = new ArrayList(list.size());
		for (int index = 0; index < list.size(); index++) {
			final Object element = list.get(index);
			final Object copyElement = copy(item(locator, index, element), element);
			copy.add(copyElement);
		}
		return copy;
	}
	
	public static final CopyStrategy INSTANCE = new JAXBCopyStrategy();

}
