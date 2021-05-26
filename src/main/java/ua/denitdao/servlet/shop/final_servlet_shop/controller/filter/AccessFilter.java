package ua.denitdao.servlet.shop.final_servlet_shop.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.final_servlet_shop.model.entity.Roles;
import ua.denitdao.servlet.shop.final_servlet_shop.model.entity.User;
import ua.denitdao.servlet.shop.final_servlet_shop.util.Paths;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class AccessFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(AccessFilter.class);
    private static final Map<String, List<String>> accessMap = new HashMap<>();
    private static List<String> outOfControl = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        accessMap.put(Roles.ADMIN.toString(), asList(filterConfig.getInitParameter("admin").split(" ")));
        accessMap.put(Roles.USER.toString(), asList(filterConfig.getInitParameter("user").split(" ")));
        accessMap.put(Roles.GUEST.toString(), asList(filterConfig.getInitParameter("guest").split(" ")));
        outOfControl = asList(filterConfig.getInitParameter("out-of-control").split(" "));
        logger.trace("Access map: {} \nOut of control: {}", accessMap, outOfControl);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (accessAllowed(request)) {
            logger.debug("---- allow");
            chain.doFilter(request, response);
        } else {
            logger.debug("---- reject -> error.jsp");
            String errorMessage = "You do not have permission to access the requested resource";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher(((HttpServletRequest) request).getContextPath() + Paths.ERROR_JSP)
                    .forward(request, response);
        }
    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String commandName = httpRequest.getRequestURI().replaceAll(".*?/c/", "");
        HttpSession session = httpRequest.getSession();
        String role = (String) session.getAttribute("role");

        User user = (User) session.getAttribute("user");
        logger.debug("---- '{}' (user: {})", httpRequest.getRequestURI(), user);

        if (commandName.isEmpty())
            return false;

        if (outOfControl.contains(commandName))
            return true;

        if (role == null)
            return accessMap.get(Roles.GUEST.toString()).contains(commandName);
        return accessMap.get(user.getRole()).contains(commandName);
    }

}
