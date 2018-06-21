package com.neu.edu.util;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.neuedu.model.mapper.FreeListenBookMapper;
import com.neuedu.model.mapper.WeixinMapper;
import com.neuedu.pojo.WeiXin;

public class WeixinUtils {
	
	public static Map doXMLParse(String strxml) throws Exception {
		if(null == strxml || "".equals(strxml)) {
			return null;
		}
		
		Map m = new HashMap();		
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new ByteArrayInputStream(strxml.getBytes()));
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if(children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}
			
			m.put(k, v);
		}
		
		//¹Ø±ÕÁ÷		
		
		return m;
	}
	
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()) {
			Iterator it = children.iterator();
			while(it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if(!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		
		return sb.toString();
	}
	
	public static WeiXin getAppInfo(int qid)
	{
		
		SqlSession session = DBUtil.getSession();
		WeixinMapper weixinMapper = session.getMapper(WeixinMapper.class);
		return weixinMapper.getWeiXin(qid);
		
		/*Map<String, String> map = new HashMap<>();
		map.put("appid", "wx9a190f49bcf4e004");
		map.put("appsecret", "692d35e5260d6ac47522a2f57620f47c");
		map.put("partner", "1488036472");
		map.put("partnerkey", "J8Rr7F98u8u88gFU7y6frhjGPO3e5Yt6");
		
		return map;*/
	}

}
