package com.foufou.interceptor;


import com.foufou.common.constant.JwtClaimsConstant;
import com.foufou.common.properties.JwtProperties;
import com.foufou.utils.JwtUtil;
import com.foufou.utils.ThreadLocalUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenStudentInterceptor implements HandlerInterceptor {

    @Resource
    private JwtProperties jwtProperties;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        //1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getStuTokenName());

        //2、校验令牌
        try {
            //从redis获取相同token
            ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
            String redisToken = operations.get(token);
            if(redisToken==null){
                //token失效
                throw new RuntimeException();
            }
            Claims claims = JwtUtil.parseJWT(jwtProperties.getStuSecretKey(), token);
            Long userId = Long.valueOf(claims.get(JwtClaimsConstant.STU_ID).toString());
            ThreadLocalUtil.set(userId);
            //3、通过，放行
            return true;
        } catch (Exception ex) {
            //4、不通过，响应401状态码
            log.info("jwt校验出错");
            response.setStatus(401);
            return false;
        }
    }




//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        //令牌验证
//        String token=request.getHeader("Authorization");
//        //验证token
//        try{
//            //从redis获取相同token
//            ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
//            String redisToken = operations.get(token);
//            if(redisToken==null){
//                //token失效
//                throw new RuntimeException();
//            }
//            Map<String,Object> claims= JwtUtil.parseToken(token);
//            //把业务数据存储到Threadlocal中
//            ThreadLocalUtil.set(claims);
//            //放行
//            return true;
//        } catch (Exception e) {
//            response.setStatus(401);
//            //不放行
//            return false;
//        }
//    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
