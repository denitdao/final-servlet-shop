package ua.denitdao.servlet.shop.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.util.Arrays.asList;

public class LocaleFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(LocaleFilter.class);
    private static final String LOCALE_ATTR = "locale";
    private static final String LOCALE_PARAM = "lang";
    private static final List<String> supportedLocales = new ArrayList<>();
    private static String defaultLocale;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        supportedLocales.addAll(asList(filterConfig.getInitParameter("supported-locales").split(" ")));
        defaultLocale = filterConfig.getInitParameter("default");
        logger.trace("Supported locales: {} \nDefault locale: {}", supportedLocales, defaultLocale);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String passedLang = httpRequest.getParameter(LOCALE_PARAM);

        if (passedLang != null) {
            // user pressed a button to change locale
            setLocale(httpRequest, passedLang);
        } else if (httpRequest.getSession().getAttribute(LOCALE_ATTR) == null) {
            // first initialization (with browser default locale, or app default if not supported)
            String language = httpRequest.getLocale().getLanguage(); // remove country '_XX' part from locale
            setLocale(httpRequest, language);
        }

        chain.doFilter(request, response);
    }

    private static void setLocale(HttpServletRequest httpRequest, String locale) {
        if (!supportedLocales.contains(locale))
            locale = defaultLocale;
        logger.info("---- setting locale: {}", locale);
        httpRequest.getSession().setAttribute(LOCALE_ATTR, new Locale(locale));
    }

}
