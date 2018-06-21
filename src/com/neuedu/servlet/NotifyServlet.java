package com.neuedu.servlet;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neu.edu.util.WeixinUtils;
import com.neuedu.service.OrderService;

/**
 * Servlet implementation class NotifyServlet
 */
@WebServlet("/NotifyServlet")
public class NotifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		System.out.println("education回调了。。");
		
		InputStream in;
		StringBuffer buffer = new StringBuffer();
		try {
			in = request.getInputStream();
			InputStreamReader reader = new InputStreamReader(in);
			BufferedReader breader = new BufferedReader(reader);
			
			String line = "";
			
			while((line=breader.readLine())!=null)
			{
				buffer.append(line);
			}
			
			System.out.println(buffer);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		try {
			Map<String, String> m = WeixinUtils.doXMLParse(buffer.toString());
			
			String result_code = m.get("result_code");//业务结果
			String return_code = m.get("return_code");//返回状态吗
			String out_trade_no = m.get("out_trade_no");//商户订单号
			String WEXIN_OPENID = m.get("openid");//微信openid
			String transaction_id = m.get("transaction_id");//微信支付单号
			
			System.out.println("result_code:"+result_code);
			System.out.println("return_code:"+return_code);
			System.out.println("out_trade_no:"+out_trade_no);
			System.out.println("WEXIN_OPENID:"+WEXIN_OPENID);
			System.out.println("transaction_id:"+transaction_id);
				       
	        if (result_code.equals("SUCCESS")  && return_code.equals("SUCCESS")) //验证 是否 微信 回调
	        {
	        	OrderService.updatePayStatus(Integer.parseInt(out_trade_no), transaction_id,"已付款");
	        	        
	            String msg = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
	        
	            response.setContentType("text/xml");
	            PrintWriter out = response.getWriter();
	            out.write(msg);
	            out.close();
	            
	        }
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
