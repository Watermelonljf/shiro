package org.github.watermelon.authentication;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * <pre>类名: LoginLogoutTest</pre>
 * <pre>描述: </pre>
 * <pre>版权: 税友软件集团股份有限公司</pre>
 * <pre>日期: 2018/1/12  16:38</pre>
 * <pre>作者: ljianf</pre>
 */
public class LoginLogoutTest {

    @Test
    public void testHelloworld() {
        //使用shiro.ini初始化一个factory
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-init/shiro-jdbc-realm.ini");
        //获得一个Manager
        SecurityManager securityManager = factory.getInstance();
        //绑定securityManager到SecurityUtil
        SecurityUtils.setSecurityManager(securityManager);
        //获取当前Subject
        Subject currSubject = SecurityUtils.getSubject();
        //构建UsernamaPasswordToken
        UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");
        try {
            //登录，即身份验证
            currSubject.login(token);
        } catch (AuthenticationException e) {
            //身份验证失败
        }
        currSubject.logout();
    }


    @Test
    public void testJDBCRealm() {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:shiro-init/shiro-jdbc-realm.ini");

        //2、得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

        try {
            //4、登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            //5、身份验证失败
            e.printStackTrace();
        }

        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录

        //6、退出
        subject.logout();
    }


    @After
    public void tearDown() throws Exception {
        ThreadContext.unbindSubject();//退出时请解除绑定Subject到线程 否则对下次测试造成影响
    }
}
