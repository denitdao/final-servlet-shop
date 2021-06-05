package ua.denitdao.servlet.shop.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.model.entity.enums.Roles;
import ua.denitdao.servlet.shop.util.ExceptionMessages;
import ua.denitdao.servlet.shop.util.Paths;

import java.io.IOException;
import java.util.*;

import static java.util.Arrays.asList;

public class AccessFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(AccessFilter.class);
    private static final Map<Roles, List<String>> accessMap = new EnumMap<>(Roles.class);
    private static List<String> outOfControl = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) {
        accessMap.put(Roles.ADMIN, asList(filterConfig.getInitParameter("admin").split("\\s+")));
        accessMap.put(Roles.USER, asList(filterConfig.getInitParameter("user").split("\\s+")));
        accessMap.put(Roles.GUEST, asList(filterConfig.getInitParameter("guest").split("\\s+")));
        outOfControl = asList(filterConfig.getInitParameter("out-of-control").split("\\s+"));
        logger.trace("Access map: {} \nOut of control: {}", accessMap, outOfControl);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (accessAllowed(request)) {
            logger.debug("---- allow");
            chain.doFilter(request, response);
        } else {
            logger.debug("---- reject -> error.jsp");
            request.setAttribute("errorMessage", ExceptionMessages.NO_PERMISSION);
            request.getRequestDispatcher(((HttpServletRequest) request).getContextPath() + Paths.ERROR_JSP)
                    .forward(request, response);
        }
    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String commandName = httpRequest.getRequestURI().replaceAll(".*?/shop/", "");
        HttpSession session = httpRequest.getSession();
        Roles role = Optional.ofNullable((Roles) session.getAttribute("role")).orElse(Roles.GUEST);

        User user = (User) session.getAttribute("user");
        logger.debug("---- '{}' (user: {})", httpRequest.getRequestURI(), user);

        if (commandName.isEmpty())
            return false;

        if (outOfControl.contains(commandName))
            return true;

        if (user == null)
            return accessMap.get(role).contains(commandName);
        return accessMap.get(user.getRole()).contains(commandName);
    }

}
