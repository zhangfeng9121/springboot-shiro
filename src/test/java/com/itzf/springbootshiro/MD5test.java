package com.itzf.springbootshiro;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

/**
 * @AUTHOR ZF
 * @DATE 2019/8/9
 */
public class MD5test {
    @Test
    public void testMD5(){

        String hashName = "md5";

        String pwd = "123456789";

        Object result = new SimpleHash(hashName, pwd, "123456", 2);

        System.out.println(result);
    }
}
