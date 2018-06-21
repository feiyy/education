package com.neu.edu.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.eclipse.jdt.internal.compiler.batch.Main;

public class DBUtil {
	
	/*static
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//创建于MySQL数据库的连接
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/education?useUnicode=true&characterEncoding=UTF-8","root","root");
		return conn;
	}
	
	//进行事物的处理
	public static void closeAUTOCommit(Connection conn) throws SQLException{
		conn.setAutoCommit(false);
	}
	
	//取消PreparedStatement对象的连接
	public static void closePreparedStatement(PreparedStatement ps) throws SQLException{
		ps.close();
	}
	
	//提交事务
	public static void commitTransaction(Connection conn) throws SQLException{
		conn.commit();
	}
	
	//回滚事务
	public static void rollbackTransaction(Connection conn) throws SQLException{
		conn.rollback();
	}
	
	//关闭于数据库的连接
	public static void closeConnection(Connection conn) throws SQLException{
		conn.close();
	}
	
	//关闭结果集的连接
	public static void closeResult(ResultSet rs) throws SQLException{
		rs.close();
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(getConnection());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	private static SqlSessionFactory sqlSessionFactory = null;
    static
    {
        //1. 构建session工厂（session 相同于 connection）
        //入参inputStream
        InputStream inputStream = null;
        try
        {
            inputStream = Resources.getResourceAsStream("SqlMapConfig.xml"); // classpath根路径=》src跟路径
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static SqlSession getSession()
    {
        return sqlSessionFactory.openSession();
    }
	
}
