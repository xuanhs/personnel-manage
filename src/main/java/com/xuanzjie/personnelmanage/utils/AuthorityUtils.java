package com.xuanzjie.personnelmanage.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Slf4j
public class AuthorityUtils {

    public final static String KEY_USER_ID = "userId";


    /**
     * 获取id
     * @return
     */
    public static Integer getUserId() {
        HttpSession session = getSession();
        Integer id = (Integer) session.getAttribute(KEY_USER_ID);
        if(id == null || id <=0){
            log.info("从session获取id失败：{}",id);
            return 0;
        }
        log.info("从session获取id成功：{}",id);
        return id;
    }

    /**
     * 设置id
     * @param id
     */
    public static void setUserId(Integer id){
        HttpSession session = getSession();
        log.info("userId存入session:{}",id);
        session.setAttribute(KEY_USER_ID,id);
    }

    public static HttpSession getSession() {
        //获取到ServletRequestAttributes 里面有
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取到Request对象
        HttpServletRequest request = attrs.getRequest();
        //获取到Session对象
        HttpSession session = request.getSession();

        //获取到Response对象
        //HttpServletResponse response = attrs.getResponse();
        return session;
    }
}
