package org.smart4j.chapter2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.service.CustomerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerServiceTest {
    private  CustomerService customerService=new CustomerService();

    @Test
    public void createCustomer(){
      Map<String,Object> fieldMap=new HashMap<String,Object>();
      fieldMap.put("id",3);
      fieldMap.put("name","customerTest");
      fieldMap.put("contact","tom");
      fieldMap.put("telephone","111111");
      boolean result=customerService.createCustomer(fieldMap);
      Assert.assertTrue(result);
      System.out.println("成功插入一个客户");
    }
    @Test
    public void getList(){
        List<Customer> customers=customerService.getCustomerList();
        Assert.assertNotNull(customers);
        System.out.println("客户名称列表：");
        for(Customer c: customers)
            System.out.println(c.toString());

    }
    @Test
    public void getCustomer(){
        long id=3;
        Customer customer=customerService.getCustomer(id);
        Assert.assertNotNull(customer);
        System.out.println("成功查找id=3的客户："+customer.toString());
    }
    @Test
    public void updateCustomer(){
        long id=3;
        Map<String,Object> fieldMap=new HashMap<String,Object>();
        fieldMap.put("contact","test-contact");
        fieldMap.put("email","test-email");
        boolean result=customerService.updateCustomer(fieldMap,id);
        Assert.assertTrue(result);
        System.out.println("客户更新成功");
    }
    @Test
    public void deleteCustomer(){
        long id=3;
        boolean result=customerService.deleteCustomer(id);
        Assert.assertTrue(result);
        System.out.println("客户删除成功");
    }
    @Test
    public void testAll(){
        createCustomer();
        getList();
        getCustomer();
        updateCustomer();
        deleteCustomer();
    }
}
