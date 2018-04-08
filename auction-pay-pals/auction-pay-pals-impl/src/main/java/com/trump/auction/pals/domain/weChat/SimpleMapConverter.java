package com.trump.auction.pals.domain.weChat;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class SimpleMapConverter extends AbstractCollectionConverter {

	public SimpleMapConverter(Mapper mapper) {
		this(mapper, null);
	}

	@SuppressWarnings("rawtypes")
	public SimpleMapConverter(Mapper mapper, Class type) {
		super(mapper);
		if (type != null && !Map.class.isAssignableFrom(type)) {
			throw new IllegalArgumentException(type + " not of type " + Map.class);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return type.equals(HashMap.class) || type.equals(Hashtable.class)
				|| type.getName().equals("java.util.LinkedHashMap")
				|| type.getName().equals("java.util.concurrent.ConcurrentHashMap")
				|| type.getName().equals("java.util.TreeMap") // Used by
																// java.awt.Font in
																// JDK 6
		;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		Map map = (Map) source;
		for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			ExtendedHierarchicalStreamWriterHelper.startNode(writer, entry.getKey().toString(), entry.getClass());
			writer.setValue(entry.getValue().toString());
			writer.endNode();
		}

	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		Map<String, String> map = new HashMap<String, String>();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			String key = reader.getNodeName();
			String value = reader.getValue();
			map.put(key, value);
			reader.moveUp();
		}
		return map;
	}

}
