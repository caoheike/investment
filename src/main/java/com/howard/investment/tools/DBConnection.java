package com.howard.investment.tools;




import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.util.Properties;  
  
public class DBConnection {  
     static Connection connect = null;  
     static PreparedStatement stmt = null;  
     static ResultSet rs = null;  
    /** 
     * 该方法用来连接数据库 
     * @param db:数据源名称 
     * */  
    public DBConnection(String db){  
        try{  
//           // Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");//注册驱动  
        	Class.forName("com.hxtt.sql.access.AccessDriver");//注册驱动  
            //Access中的数据库默认编码为GBK，本地项目为UTF-8，若不转码会出现乱码  
            Properties p = new Properties();  
            p.put("charSet", "GBK");  
//            connect = DriverManager.getConnection("jdbc:odbc:"+db,p);  
//            connect = DriverManager.getConnection("jdbc:Access:///reckon.mdb","","");  
            connect = DriverManager.getConnection("Jdbc:Access:////Users/hongzheng/Downloads/#cn-enet#.mdb","","");  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * 该方法用来执行SQL并返回结果集 
     * */  
    public static ResultSet selectQuery(String db,String sql){  
        try{  
            stmt = getConnect(db).prepareStatement(sql);  
            rs = stmt.executeQuery();//执行SQL  
    
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return rs;  
    }  
      
    public static Connection getConnect(String db){  
        DBConnection  conn = new DBConnection(db);  
        return connect;  
    }  
      
    /** 
     * 该方法用来关闭连接 
     * */  
    public static void closeConn() {  
        try {  
            rs.close();  
            stmt.close();  
            connect.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * 测试 
     * */  
    public static void main(String[] args){  
        try{  
            DBConnection connnect = new DBConnection("");  
            if(connect!=null){  
                System.out.println(connect+"\n连接成功");  
            }else{  
                System.out.println("连接失败");  
            }
            int row=connect.createStatement().executeUpdate("");
            System.out.println(row);
//            rs= selectQuery("reckon","select * from table1");  
//            if(rs!=null){  
//                while(rs.next()){  
//                    System.out.println(rs.getString(1)+"\t"+rs.getString(2));    
//                }  
//            }  
            connect.close();
        }catch(Exception e){  
            e.printStackTrace();  
        } 
    }  
}