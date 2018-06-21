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
	
	//������MySQL���ݿ������
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/education?useUnicode=true&characterEncoding=UTF-8","root","root");
		return conn;
	}
	
	//��������Ĵ���
	public static void closeAUTOCommit(Connection conn) throws SQLException{
		conn.setAutoCommit(false);
	}
	
	//ȡ��PreparedStatement���������
	public static void closePreparedStatement(PreparedStatement ps) throws SQLException{
		ps.close();
	}
	
	//�ύ����
	public static void commitTransaction(Connection conn) throws SQLException{
		conn.commit();
	}
	
	//�ع�����
	public static void rollbackTransaction(Connection conn) throws SQLException{
		conn.rollback();
	}
	
	//�ر������ݿ������
	public static void closeConnection(Connection conn) throws SQLException{
		conn.close();
	}
	
	//�رս����������
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
        //1. ����session������session ��ͬ�� connection��
        //���inputStream
        InputStream inputStream = null;
        try
        {
            inputStream = Resources.getResourceAsStream("SqlMapConfig.xml"); // classpath��·��=��src��·��
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