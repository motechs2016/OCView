package org.blue.backend.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

/**
 * 数据源连接池工具类
 * @author ldc4
 */
public final class JdbcUtils {
	
	private static DataSource myDataSource = null;
	
	private JdbcUtils() {}
	
	//静态块：创建dbcp数据源
	static {
		InputStream is = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Properties prop = new Properties();
			is = JdbcUtils.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
			prop.load(is);
			myDataSource = BasicDataSourceFactory.createDataSource(prop);
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		} finally {
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//获取数据库连接
	public static Connection getConnection() throws SQLException {
		return myDataSource.getConnection();
	}
	//获取数据源
	public static DataSource getDataSource(){
		return myDataSource;
	}
	//释放资源
	public static void free(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null)
						conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
