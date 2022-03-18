package com.example.reactiveprogramming.reactor;

import com.example.reactiveprogramming.demo.helper.CommonUtils;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component
public class AsyncFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        CommonUtils.sleep(200);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}