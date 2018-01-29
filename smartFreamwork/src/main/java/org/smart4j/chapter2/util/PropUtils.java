package org.smart4j.chapter2.util;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;
public class PropUtils {
    //private static final Log
    //加载属性文件
    public static Properties loadProps(String fileName){
        Properties props=null;
        InputStream is=null;
        try{
            is=Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if(is==null){
                throw  new FileNotFoundException(fileName+":file is not find");
            }
            props=new Properties();
            props.load(is);
        }catch (IOException e){

        }finally {
            if(is != null){
                try{
                    is.close();
                }catch (IOException e){

                }
            }
        }

        return props;
    }
    //获取配置文件中的某个属性
    public static Object getProperty(Properties props,String key){
        if(props.contains(key)){
            return props.getProperty(key);
        }
        return null;
    }
}
