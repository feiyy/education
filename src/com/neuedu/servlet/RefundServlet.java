package com.neuedu.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyStore;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.neu.edu.util.WeixinUtils;
import com.neuedu.pojo.Sorder;
import com.neuedu.pojo.WeiXin;
import com.neuedu.service.OrderService;


@WebServlet("/RefundServlet")
public class RefundServlet extends HttpServlet {
	
	String appid;
	String appsecret;
	String partner;
	String partnerkey;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		String out_trade_no = request.getParameter("oid");
		
		//���ݶ�����ŵõ�������Ϣ
		Sorder sorder = OrderService.getOrderById(Integer.parseInt(out_trade_no));
		
		WeiXin wx = WeixinUtils.getAppInfo(sorder.getQid());
		
		appid = wx.getAppid();
		appsecret = wx.getAppsecret();
		partner = wx.getPartner();
		partnerkey = wx.getPartnerkey();
		
		String path = request.getServletContext().getRealPath("/");
		String file = path+"apiclient_cert.p12";
		
		System.out.println("certcertcertcertcertcertcertcertcertcert:"+file);
		
		String total = String.valueOf(sorder.getTotal()*100);
		total = total.substring(0, total.indexOf("."));
		
		Object ret = wxRefund(request,response,appid,appsecret,partner,partner,out_trade_no,total,total,file);
	    System.out.println("�˿�����"+ret);
	    
	    if(ret.toString().contains("�˿�ɹ�"))
	    {
	    	//update���ݿ�
	    	try {
				OrderService.updatePayStatus(Integer.parseInt(out_trade_no),null,"���˿�");
			} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	response.setCharacterEncoding("utf-8");
	    	response.setContentType("text/json");
	    	PrintWriter out = response.getWriter();
	    	out.write("{\"result\":\"�˿�ɹ�\"}");
	    	out.close();
	    }
	    else
	    {
	    	response.setCharacterEncoding("utf-8");
	    	response.setContentType("text/json");
	    	PrintWriter out = response.getWriter();
	    	out.write("{\"result\":\"�˿�ʧ��\"}");
	    	out.close();
	    }
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
	
	public String getNonceStr() {
		// �����
		String currTime = this.getCurrTime();
		// 8λ����
		String strTime = currTime.substring(8, currTime.length());
		// ��λ�����
		String strRandom = this.buildRandom(4) + "";
		// 10λ���к�,�������е�����
		return strTime + strRandom;
	}
	
	public String getUUID()
	{
		String uuid = UUID.randomUUID().toString();  

        System.out.println(uuid);

        uuid = uuid.replace("-", "");

        System.out.println(uuid);  
        
        return uuid;
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
		
		System.out.println("sign-------------------����ǰ��"+sb.toString());
		String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8")
				.toUpperCase();
		
		System.out.println("sign-------------------���ܺ�"+sign);
		return sign;

	}
	
	private Object wxRefund(HttpServletRequest request,HttpServletResponse response,String appId,
            String secret,String shh,String key,String orderId,String total_fee,String refund_fee,String path){
	Map<String,String> result=new HashMap<String,String>(); 
	
	String refundid = getUUID();
	//String nonce_str = MD5.getMessageDigest(String.valueOf(new Random().nextInt(10000)).getBytes());
	String nonce_str = getNonceStr();
	
	/*-----  1.����Ԥ֧��������Ҫ�ĵ�package����-----*/
	SortedMap<String, String> packageParams = new TreeMap<String, String>();
	packageParams.put("appid", appId);  
	packageParams.put("mch_id", shh);  
	packageParams.put("nonce_str", nonce_str);  
	packageParams.put("op_user_id", shh);  
	packageParams.put("out_trade_no", orderId);  
	packageParams.put("out_refund_no", refundid);  
	packageParams.put("total_fee",total_fee);  
	packageParams.put("refund_fee", refund_fee);  
	/*----2.����package����ǩ��sign---- */	
	String sign = createSign(packageParams);
	
	/*----3.ƴװ��Ҫ�ύ��΢�ŵ�����xml---- */
	String xml="<xml>"
	+"<appid>"+appId+"</appid>"
	+ "<mch_id>"+shh+"</mch_id>"
	+ "<nonce_str>"+nonce_str+"</nonce_str>"
	+ "<op_user_id>"+shh+"</op_user_id>"
	+ "<out_trade_no>"+orderId+"</out_trade_no>"
	+ "<out_refund_no>"+refundid+"</out_refund_no>"
	+ "<refund_fee>"+refund_fee+"</refund_fee>"
	+ "<total_fee>"+total_fee+"</total_fee>"
	+ "<sign>"+sign+"</sign>"
	+"</xml>";
	try {
	 /*----4.��ȡ֤���ļ�,��һ����ֱ�Ӵ�΢��֧��ƽ̨�ṩ��demo��copy�ģ�����һ�㲻��Ҫ�޸�---- */
	 KeyStore keyStore  = KeyStore.getInstance("PKCS12");
	 FileInputStream instream = new FileInputStream(new File(path));
	 try {
	     keyStore.load(instream, shh.toCharArray());
	 } finally {
	     instream.close();
	 }
	 // Trust own CA and all self-signed certs
	 SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, shh.toCharArray()).build();
	 // Allow TLSv1 protocol only
	 SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,new String[] { "TLSv1" },null,
	         SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	 CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
	
	 /*----5.�������ݵ�΢�ŵ��˿�ӿ�---- */
	 String url="https://api.mch.weixin.qq.com/secapi/pay/refund";
	 HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
	 httpost.setEntity(new StringEntity(xml, "UTF-8"));
	 HttpResponse weixinResponse = httpClient.execute(httpost);
	 String jsonStr = EntityUtils.toString(weixinResponse.getEntity(), "UTF-8");
	
	 Map map = WeixinUtils.doXMLParse(jsonStr);
	 if("success".equalsIgnoreCase((String) map.get("return_code"))){	    
	     result.put("returncode", "ok");
	     result.put("returninfo", "�˿�ɹ�");
	 }else{	    
	     result.put("returncode", "error");
	     result.put("returninfo", "�˿�ʧ��");
	 }
	} catch (Exception e) {
	result.put("returncode", "error");
	result.put("returninfo", "�˿�ʧ��");
	}
	return result;
	
	}

}
