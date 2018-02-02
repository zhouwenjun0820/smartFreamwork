package org.smart4j.chapter2.helper;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.smart4j.chapter2.util.PropUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DatebaseHelper {
    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL=new InheritableThreadLocal<Connection>();
    private static final QueryRunner QUERY_RUNNER=new QueryRunner();
    private static  String DRIVER;
    private static  String URL;
    private static  String USERNAME;
    private static  String PASSWORD;


    static{
        Properties jdbcProp= PropUtils.loadProps("jdbc.properties");
        DRIVER =(String) jdbcProp.getProperty("jdbc.driver");
        URL = (String) jdbcProp.getProperty("jdbc.url");
        USERNAME = (String) jdbcProp.getProperty("jdbc.username");
        PASSWORD = (String) jdbcProp.getProperty("jdbc.password");
        try{
            Class.forName(DRIVER);
        }catch (ClassNotFoundException e){
            System.out.println(e);
        }
    }

    /**
     * 新建数据库连接
     */
    public static Connection getConnection() {
        Connection conn=CONNECTION_THREAD_LOCAL.get();
        if(conn == null){
            try {
                conn= DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                System.out.println(e);
            }finally {
                CONNECTION_THREAD_LOCAL.set(conn);
            }
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     */
    public static void closeConnection() {
        Connection conn=CONNECTION_THREAD_LOCAL.get();
        try {
           conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }finally {
            CONNECTION_THREAD_LOCAL.remove();
        }
    }
    /**
     * 查询实体列表
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass){
        List<T> entityList=null;
        String sql="select * from "+getTableName(entityClass);
        try {
            Connection conn=getConnection();
            entityList=QUERY_RUNNER.query(conn,sql,new BeanListHandler<T>(entityClass));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return entityList;
    }

    /**
     * 查询一个实体
     * @param entityClass
     * @param <T>
     * @return
     */
    public static <T>T queryEntity(Class<T> entityClass,long id){
        T entity=null;
        String sql="select * from "+getTableName(entityClass)+" where id = ?";
        Connection connection=getConnection();
        try {
            entity=QUERY_RUNNER.query(connection,sql,new BeanHandler<T>(entityClass),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return entity;
    }

    /**
     * 执行查询
     * @param sql
     * @param params
     * @return
     */
    public static List<Map<String,Object >> executeQuery(String sql,Object...params){
        List<Map<String,Object>> resultSet=null;
        Connection connection=getConnection();
        try{
            resultSet=QUERY_RUNNER.query(connection,sql,new MapListHandler(),params);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return  resultSet;
    }

    /**
     * 执行更新语句
     * @param sql
     * @param params
     * @return
     */

    public static int executeUpdate(String sql,Object... params){
        int rows=0;
        Connection connection=getConnection();
        try {
            rows=QUERY_RUNNER.update(connection,sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return rows;
    }

    /**
     * 根据实体类名获取表名（我们自定义实体类名和表名对应）
     * @param entityClass
     * @return
     */
    private static  String getTableName(Class<?> entityClass){
        return entityClass.getSimpleName();
    }

    /**
     * 插入实体
     * @param entityClass
     * @param fieldMap
     * @param <T>
     * @return true ： 插入成功
     */

    public static <T>boolean insertEntity(Class<T> entityClass,Map<String,Object> fieldMap){
        if(fieldMap.isEmpty()){
            System.out.println("filedMap is empty");
            return false;
        }
        String sql="insert into "+getTableName(entityClass);
        StringBuilder columns=new StringBuilder("( ");
        StringBuilder values=new StringBuilder("( ");
        for(String fieldName:fieldMap.keySet()){
            columns.append(fieldName).append(" , ");
            values.append(" ?, ");
        }
        columns.replace(columns.lastIndexOf(","),columns.length(),")");
        values.replace(values.lastIndexOf(","),values.length(),")");
        sql+=columns+" values "+values;
        Object[] params=fieldMap.values().toArray();
        return executeUpdate(sql,params) == 1;
    }

    /**
     * 更新实体
     * @param entityClass ：实体类名
     * @param fieldMap ：实体属性Map
     * @param <T> 实体类
     * @return true:更新成功
     */
    public static <T>boolean updateEntity(Class<T> entityClass,Map<String,Object>fieldMap,long id){
        if(fieldMap.isEmpty()){
            System.out.println("filedMap is empty");
            return false;
        }
        String sql="update "+getTableName(entityClass)+" set ";
        StringBuilder columns=new StringBuilder();
        for(String column:fieldMap.keySet()){
            columns.append(column).append(" =?, ");
        }
        sql+=columns.substring(0,columns.lastIndexOf(","))+" where id = ?";
        List<Object> paramsList=new ArrayList<Object>();
        paramsList.addAll(fieldMap.values());
        paramsList.add(id);
        Object[] params =paramsList.toArray();
        return executeUpdate(sql,params) == 1 ;
    }

    public static <T>boolean deleteEntity(Class<T> entityClass,long id){
        String sql="delete from "+getTableName(entityClass)+" where id = ?";
        return executeUpdate(sql,id) == 1;
    }
}
