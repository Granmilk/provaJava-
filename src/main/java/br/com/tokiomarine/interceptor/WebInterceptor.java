package br.com.tokiomarine.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestScope
@Component
public class WebInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String uri = request.getRequestURI();

        if (uri.equals("/") ||
            uri.equals("/timeout") ||
            uri.equals("/login") ||
            uri.contains("assets") ||
            uri.contains("static")) {
            return true;
        }
        if (request.getSession().getAttribute("usuarioLogado") != null) {
            if (uri.equals("/logar") || uri.equals("/validar-login")){
                response.sendRedirect("/");
                return false;
            }else {
                return true;
            }
        }
        response.sendRedirect("/timeout");
        return false;
    }
}