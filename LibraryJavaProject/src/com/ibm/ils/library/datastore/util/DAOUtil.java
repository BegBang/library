package com.ibm.ils.library.datastore.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOUtil {
	public static void close(ResultSet rs, Statement ps, Connection conn)
	{
	    if (rs!=null)
	    {
	        try
	        {
	            rs.close();

	        }
	        catch(SQLException e)
	        {
	            //logger.error("The result set cannot be closed.", e);
	        }
	    }
	    if (ps != null)
	    {
	        try
	        {
	            ps.close();
	        } catch (SQLException e)
	        {
	            //logger.error("The statement cannot be closed.", e);
	        }
	    }
	    if (conn != null)
	    {
	        try
	        {
	            conn.close();
	        } catch (SQLException e)
	        {
	            //logger.error("The data source connection cannot be closed.", e);
	        }
	    }

	}
}
