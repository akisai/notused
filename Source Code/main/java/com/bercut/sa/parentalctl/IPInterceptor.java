package com.bercut.sa.parentalctl;

import com.bercut.sa.parentalctl.atlas.AtlasProvider;
import com.bercut.sa.parentalctl.logs.LoggerText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by haimin-a on 09.07.2019.
 */
@Component
public class IPInterceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(IPInterceptor.class);

    private final AtlasProvider atlasProvider;

    @Autowired
    public IPInterceptor(AtlasProvider atlasProvider) {
        this.atlasProvider = atlasProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAdress = request.getRemoteAddr();

        List<String> allowedIps = atlasProvider.getAllowedIps();
        if (allowedIps.contains(ipAdress)) {
            long startTime = System.currentTimeMillis();
            request.setAttribute("startTime", startTime);
            return true;
        } else {
            logger.info(LoggerText.IP_DENIED.getText(), request.getRemoteAddr());
            response.getWriter().write("IP denied");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        logger.info(LoggerText.COMPLETE.getText(), executeTime);
    }
}
