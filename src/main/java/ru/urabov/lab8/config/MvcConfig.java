package ru.urabov.lab8.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // Страницы без логики: адрес сразу отдаёт шаблон, контроллер не нужен
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/access-denied").setViewName("access-denied");
    }

    // В каждую страницу передаются данные о вошедшем пользователе – по ним строится меню
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response,
                                   Object handler, ModelAndView mav) {
                if (mav == null || (mav.getViewName() != null && mav.getViewName().startsWith("redirect:"))) {
                    return;
                }
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                boolean loggedIn = auth != null && auth.isAuthenticated()
                        && !(auth instanceof AnonymousAuthenticationToken);
                mav.addObject("currentUser", loggedIn ? auth.getName() : null);
                mav.addObject("isAdmin", loggedIn && auth.getAuthorities().stream()
                        .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority())));
            }
        });
    }
}
