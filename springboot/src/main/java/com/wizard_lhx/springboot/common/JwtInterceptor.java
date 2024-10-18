package com.wizard_lhx.springboot.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.wizard_lhx.springboot.exception.ServiceException;
import com.wizard_lhx.springboot.entity.User;
import com.wizard_lhx.springboot.mapper.UserMapper;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 功能：
 * 作者：wizard-lhx
 * 日期：2024/10/18 10:43
 */
public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token"); //从 http 请求头中取出 token
        if(StrUtil.isEmpty(token)){
            token = request.getParameter("token"); // url 参数中取出 token
        }

        // 如果不是映射到方法直接通过
        if (handler instanceof HandlerMethod) {
            AuthAccess annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
            if (annotation != null) {
                return true;
            }
        }
        //执行认证
        if(StrUtil.isBlank(token)) {
            throw new ServiceException("401", "无token，请重登录");
        }
        // 获取 token 中 的数据
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0); // 将 token 解码
        }catch (JWTDecodeException j) {
            throw new ServiceException("401", "token验证失败");
        }
        //验证用户是否存在
        User user = userMapper.selectById(Integer.parseInt(userId));
        if(user == null) {
            throw new ServiceException("401", "用户不存在，请重新登录");
        }
        // 用户密码加签验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try{
            jwtVerifier.verify(token); //验证 token
        }catch (JWTVerificationException e) {
            throw new ServiceException("401", "用户不存在，请重新登录");
        }
        return true;
    }
}


