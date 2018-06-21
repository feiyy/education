package com.neuedu.servlet;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neu.edu.pojo.SNSUserInfo;
import com.neu.edu.pojo.WeixinOauth2Token;
import com.neu.edu.util.AdvancedUtil;
import com.neu.edu.util.WeixinUtils;
import com.neuedu.pojo.WeiXin;

import net.sf.json.JSONObject;

@WebServlet("/GetOpenIdServlet")
public class GetOpenIdServlet extends HttpServlet {

	private String APPID;
	private String APPSECRET;
	private static final String oauth_connect_token_url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String code = request.getParameter("code");
		String qid = request.getParameter("qid");
		
        WeiXin wx = WeixinUtils.getAppInfo(Integer.parseInt(qid));
		
        APPID = wx.getAppid();
        APPSECRET = wx.getAppsecret();
        	
		//2. ���openid			
		String requestUrl = oauth_connect_token_url.replace("APPID", APPID).replace("SECRET", APPSECRET).replace("JSCODE", code);
		
		System.out.println("requestUrl:"+requestUrl);
		JSONObject jsonCode = httpsRequest(requestUrl,"GET",null);
		
		String openid="";
		if (jsonCode != null && !"".equals(jsonCode)) {
			openid = jsonCode.getString("openid");
		}
		System.out.println("==============openId=========================" + openid);

	    response.setCharacterEncoding("utf-8");
	    response.setContentType("text/plain");
	    
	    PrintWriter out = response.getWriter();
	    out.write(openid);
	    out.close();
	}
	
	/**
	 * ����https���󲢻�ȡ���
	 * @param requestUrl
	 *            �����ַ
	 * @param requestMethod
	 *            ����ʽ��GET��POST��
	 * @param outputStr
	 *            �ύ������
	 * @return JSONObject(ͨ��JSONObject.get(key)�ķ�ʽ��ȡjson���������ֵ)
	 */
	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// ����SSLContext���󣬲�ʹ������ָ�������ι�������ʼ��
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// ������SSLContext�����еõ�SSLSocketFactory����
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// ��������ʽ��GET/POST��
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// ����������Ҫ�ύʱ
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// ע������ʽ����ֹ��������
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// �����ص�������ת�����ַ���
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			
			System.out.println("buffer:"+buffer);
			// �ͷ���Դ
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

}
