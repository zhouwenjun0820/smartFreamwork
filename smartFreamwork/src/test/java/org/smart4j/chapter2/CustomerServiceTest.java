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
    public CustomerServiceTest(){}

    @Before
    public void setUp(){

    }
    @Test
    public void getList(){
        List<Customer> customers=customerService.getCustomerList();
        Assert.assertEquals(2,customers.size());
    }
    @Test
    public void createCustomer(){
      Map<String,Object> fieldMap=new HashMap<String,Object>();
      fieldMap.put("id",3);
      fieldMap.put("name","customerTest");
      fieldMap.put("contact","tom");
      fieldMap.put("telephone","111111");
      boolean result=customerService.createCustomer(fieldMap);
      Assert.assertTrue(result);
    }
    @Test
    public void getCustomer(){
        long id=3;
        Customer customer=customerService.getCustomer(id);
        Assert.assertNotNull(customer);
    }
    @Test
    public void updateCustomer(){
       long id=3;
        Map<String,Object> fieldMap=new HashMap<String,Object>();
        fieldMap.put("contact","tom-tom");
        boolean result=customerService.updateCustomer(fieldMap,id);
        Assert.assertTrue(result);
    }
}
