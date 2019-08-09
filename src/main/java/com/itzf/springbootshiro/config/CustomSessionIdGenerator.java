package com.itzf.springbootshiro.config;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * @AUTHOR ZF
 * @DATE 2019/8/9
 */
public class CustomSessionIdGenerator implements SessionIdGenerator {
    @Override
    public Serializable generateId(Session session) {
        return "ITZF" + UUID.randomUUID().toString().replace("-", "");
    }
}
