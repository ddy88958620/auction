package com.trump.auction.cust.util.sms;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlUtil {

	private static final Logger log = LoggerFactory.getLogger(XmlUtil.class);

	/**
	 * 解析消息内容
	 * 
	 * @param objClass
	 * @param msg
	 * @return
	 */
	public static Object readMessage(Class objClass, String msg) {
		try {
			JAXBContext jc = JAXBContext.newInstance(new Class[] { objClass });
			Unmarshaller u = jc.createUnmarshaller();
			Object ebean = objClass.cast(u.unmarshal(new StringReader(msg)));
			return ebean;
		} catch (JAXBException e) {
			log.error("readMessage error", e);
		}
		return null;
	}

	/**
	 * 生成内容
	 * 
	 * @param mesgBean
	 * @return
	 */
	public static String bulidMessage(Object mesgBean) {
		Marshaller m = null;
		try {
			JAXBContext jc = JAXBContext.newInstance(new Class[] { mesgBean.getClass() });
			m = jc.createMarshaller();
			m.setProperty("jaxb.encoding", "UTF-8");
			m.setProperty("jaxb.formatted.output", Boolean.valueOf(true));

			m.setProperty("jaxb.fragment", Boolean.valueOf(true));
			// 不进行转义字符的处理,fyc2017-03-24注释,认为后台系统不会发送特殊字符，所以转义不会有问题（因为没有需要转义的字符串），mq-alljar包存在时，此代码报错
			// m.setProperty("com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler",
			// new CharacterEscapeHandler()
			// {
			// public void escape(char[] ch, int start, int length, boolean
			// isAttVal, Writer writer)
			// throws IOException
			// {
			// writer.write(ch, start, length);
			// }
			// });
			StringWriter ws = new StringWriter();
			m.marshal(mesgBean, ws);
			return ws.toString();
		} catch (JAXBException e) {
			log.error("bulidMessage error", e);
		}
		return null;
	}

	/**
	 * 解析内容
	 * 
	 * @param clazz
	 * @param xmlStr
	 * @return
	 */
	public static Object convertXmlStrToObject(Class clazz, String xmlStr) {
		Object xmlObject = null;
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			// 进行将Xml转成对象的核心接口
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader sr = new StringReader(xmlStr);
			xmlObject = unmarshaller.unmarshal(sr);
		} catch (JAXBException e) {
			log.error("convertXmlStrToObject error", e);
		}
		return xmlObject;
	}

	public static void main(String[] args) {
	}

	/**
	 * 
	 * @param clazz
	 *            xml绑定的对象的类
	 * @param object
	 *            xml绑定的对象
	 * @return 解析后字符编码为UTF-8的xml字符串
	 * @throws Exception
	 */
	public static <T> String toXml(Class<T> clazz, Object object) throws Exception {
		Map<String, JAXBContext> map = new ConcurrentHashMap<String, JAXBContext>();
		// 组装xml
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Marshaller marshaller = null;
		JAXBContext context = null;
		String returnString = null;
		try {
			try {
				context = map.get(clazz.toString());
				if (context == null) {
					context = JAXBContext.newInstance(clazz);
					map.put(clazz.toString(), context);
				}
				marshaller = context.createMarshaller();
				// marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
				// Boolean.TRUE);
				marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
				marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
				marshaller.marshal(object, os);
			} catch (Exception e) {
				log.error("组装消息报文错误：" + e.getMessage());
				throw e;
			}

			// 转换为UTF-8的字符串
			if (os != null) {
				returnString = os.toString("UTF-8");
			}
		} finally {
			marshaller = null;
			if (os != null) {
				os.close();
			}
			os = null;
		}
		return returnString;

	}

}
