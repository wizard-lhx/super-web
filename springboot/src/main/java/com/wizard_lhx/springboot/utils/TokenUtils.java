package com.wizard_lhx.springboot.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wizard_lhx.springboot.entity.User;
import com.wizard_lhx.springboot.mapper.UserMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 功能：
 * 作者：wizard-lhx
 * 日期：2024/10/18 11:32
 */

@Component
public class TokenUtils {

    private static UserMapper staticUserMapper;

    @Resource
    private UserMapper userMapper;

    @PostConstruct
    private void setUserService() {
        staticUserMapper = userMapper;
    }


    /**
     * 生成Token
     * @param userId
     * @param sign
     * @return
     */
    public static String createToken(String userId, String sign) {
        return JWT.create().withAudience(userId)  //将user ID保存到 token 里面 作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) //2小时后token  过期
                .sign(Algorithm.HMAC256(sign));    //以 sign(password) 作为 token 的密钥
    }

    /**
     * 获取当前用户信息
     * @return
     */
    public static User getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserMapper.selectById(Integer.parseInt(userId));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}

