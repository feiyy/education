package com.neuedu.servlet;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.neu.edu.util.WeixinUtils;
import com.neuedu.pojo.WeiXin;

import net.sf.json.JSONObject;




@WebServlet("/PayServlet")
public class PayServlet extends HttpServlet {
	
	private String appid;
	private String appsecret;
	private String partner;
	private String partnerkey;
	private String weixinpaycallback;
	
	 public static DefaultHttpClient httpclient;

	  static
	  {
	    httpclient = new DefaultHttpClient();
	    httpclient = (DefaultHttpClient)HttpClientConnectionManager.getSSLInstance(httpclient);
	  }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1. 获得openid
		String openid = request.getParameter("openid");
		
		System.out.println("PayServlet==="+openid);
		//2. 获得nonceStr
		String nonceStr = this.getNonceStr();
		
		String finalpackage= this.getPackage(openid,nonceStr,request);
		
		finalpackage= "{\"result\":\"true\","+finalpackage+"}";
		
		System.out.println(finalpackage);
		
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.write(finalpackage);
		out.flush();
				
		//5. ajax影响 {result:"", package:"", paySign:""}
		
	}
	
	public String createSign(SortedMap<String, String> packageParams) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + partnerkey);
		
		System.out.println("sign-------------------加密前："+sb.toString());
		String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8")
				.toUpperCase();
		
		System.out.println("sign-------------------加密后："+sign);
		return sign;

	}
	
	public String getPayNo(String url,String xmlParam){
		  DefaultHttpClient client = new DefaultHttpClient();
		  client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
		  HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
		  String prepay_id = "";
	     try {
			 httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			 HttpResponse response = httpclient.execute(httpost);
		     String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
		     System.out.println("weixin fanhui preid:"+jsonStr);
		    if(jsonStr.indexOf("FAIL")!=-1){
		    	return prepay_id;
		    }
		    Map map = doXMLParse(jsonStr);
		    prepay_id  = (String) map.get("prepay_id");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prepay_id;
	  }
	
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
		
		//关闭流		
		
		return m;
	}
	
	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */
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
	
	
	
	public String getNonceStr() {
		// 随机数
		String currTime = this.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = this.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		return strTime + strRandom;
	}
	
	public String getCurrTime() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}
	
	public int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}

	private String getPackage(String openId, String nonce_str,HttpServletRequest request)
	{		
		String money = request.getParameter("money");
		String money1 = String.valueOf(Double.parseDouble(money)*100);
		money1 = money1.substring(0, money.indexOf("."));
		
		String qid = request.getParameter("qid");		
		
        WeiXin wx = WeixinUtils.getAppInfo(Integer.parseInt(qid));
		
		appid = wx.getAppid();
		appsecret = wx.getAppsecret();
		partner = wx.getPartner();
		partnerkey = wx.getPartnerkey();
		weixinpaycallback = wx.getWeixinpaycallback();
						
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		
		String mch_id = partner;		
		String body = "购物订单";
		String out_trade_no = request.getParameter("oid");
		String attach = "";
		String totalFee = money1;
		String spbill_create_ip = request.getRemoteAddr();
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		//String notify_url = basePath+"/NotifyServlet";
		String notify_url = weixinpaycallback;
		String trade_type = "JSAPI";
				
		
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", body);
		packageParams.put("out_trade_no", out_trade_no);		
		packageParams.put("attach", attach);

		// 这里写的金额为1 分到时修改
		packageParams.put("total_fee", totalFee);
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("notify_url", notify_url);
		packageParams.put("trade_type", trade_type);
		packageParams.put("openid", openId);


		String sign = this.createSign(packageParams);
		String xml = "<xml>" +
								"<appid>" + appid + "</appid>" + 
								"<mch_id>"+ mch_id + "</mch_id>" + 
								"<nonce_str>" + nonce_str + "</nonce_str>" +
								"<sign>" + sign + "</sign>"+ 
								"<body><![CDATA[" + body + "]]></body>" + 
								"<out_trade_no>" + out_trade_no+ "</out_trade_no>" +
								"<attach>" + attach + "</attach>"+ 
								"<total_fee>" + totalFee + "</total_fee>"+ 
								"<spbill_create_ip>" + spbill_create_ip+ "</spbill_create_ip>"+ 
								"<notify_url>" + notify_url+ "</notify_url>" + 
								"<trade_type>" + trade_type+ "</trade_type>" +
								"<openid>" + openId + "</openid>"+ 
								"</xml>";
		System.out.println("xml======"+xml);
		String prepay_id = "";
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		
		prepay_id = this.getPayNo(createOrderURL, xml);

		System.out.println("商城获取到的预支付ID：" + prepay_id);
		
		//获取prepay_id后，拼接最后请求支付所需要的package
		
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		String packages = "prepay_id="+prepay_id;
		finalpackage.put("appId", appid);  
		finalpackage.put("timeStamp", timestamp);  
		finalpackage.put("nonceStr", nonce_str);  
		finalpackage.put("package", packages);  
		finalpackage.put("signType", "MD5");
		//要签名
		String finalsign = this.createSign(finalpackage);
		
		String finaPackage = "\"appId\":\"" + appid + "\",\"timeStamp\":\"" + timestamp
		+ "\",\"nonceStr\":\"" + nonce_str + "\",\"package\":\""
		+ packages + "\",\"signType\":\"MD5" + "\",\"paySign\":\""
		+ finalsign + "\"";

		System.out.println("V3 jsApi package:"+finaPackage);
		return finaPackage;
				
	}

}
