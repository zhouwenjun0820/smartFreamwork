package org.smart4j.chapter2.service;

import org.smart4j.chapter2.helper.DatebaseHelper;
import org.smart4j.chapter2.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerService {
    //private final static Logger








    /**
     * 获取客户列表
     * @return
     */
    public List<Customer> getCustomerList(){
        List<Customer> customers=new ArrayList<Customer>();
        Connection conn=null;
        String sql="select * from customer";
        try {
            conn = DatebaseHelper.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getLong("id"));
                customer.setName(resultSet.getString("name"));
                customer.setContact(resultSet.getString("contact"));
                customer.setEmail(resultSet.getString("email"));
                customer.setRemark(resultSet.getString("remark"));
                customer.setTelephone(resultSet.getString("telephone"));
                customers.add(customer);
            }
        }catch (SQLException e){
            System.out.println(e);
        } finally {
            if(conn !=null){
                try{conn.close();
                } catch (SQLException e){
                    System.out.println(e);
                }
            }
        }
        return customers;
    }

    /**
     * 获取客户
     * @param id
     * @return
     */
    public Customer getCustomer(long id){
        return  null;
    }

    /**
     *
     * @param filedMap
     * @return
     */
    public boolean createCustomer( Map<String ,Object>filedMap){
        return false;
    }

    /**
     *
     * @param filedMap
     * @return
     */
    public boolean updateCustomer( Map<String ,Object>filedMap,long id){
        return false;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean deleteCustomer( long id){
        return false;
    }
}
