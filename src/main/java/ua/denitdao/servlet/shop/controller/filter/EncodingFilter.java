package ua.denitdao.servlet.shop.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class EncodingFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(EncodingFilter.class);
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) {
        encoding = filterConfig.getInitParameter("encoding");
        if (encoding == null)
            encoding = "UTF-8";
        logger.trace("Filter encoding is set to {}", encoding);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getCharacterEncoding() == null)
            request.setCharacterEncoding(encoding);
        response.setContentType("text/html");
        response.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }
}
