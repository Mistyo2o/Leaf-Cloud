package org.zh.thch.common.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.zh.thch.common.basic.Strings;
import org.zh.thch.common.basic.WebConstants;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhouHui
 * @version 1.0
 * @description TODO
 * @date 2023/10/15 19:34:59
 */
public class WebHttpUtil {

    private static final String HEADER_UNKNOWN = "unknown";

    private WebHttpUtil() {
    }

    /**
     * 获取指定request请求中的所有参数的map集合
     *
     * @param request                请求
     * @param excludedParameterNames 排除的参数名
     * @return 指定request请求中的所有参数的map集合
     */
    public static Map<String, Object> getRequestParameterMap(ServletRequest request, String... excludedParameterNames) {
        Map<String, Object> map = new LinkedHashMap<>();
        Map<String, String[]> params = request.getParameterMap();
        params.forEach((name, values) -> {
            if (values != null && !ArrayUtils.contains(excludedParameterNames, name)) {
                if (values.length == 1) {
                    map.put(name, values[0]);
                } else {
                    map.put(name, values);
                }
            }
        });
        return map;
    }

    public static String getRequestBodyString(ServletRequest request) {
        try {
            BufferedInputStream in = new BufferedInputStream(request.getInputStream());
            in.mark(in.available());
            String s = IOUtils.toString(in, request.getCharacterEncoding());
            in.reset();
            return s;
        } catch (IOException e) {
            return null;
        }
    }

    public static Map<String, Object> getRequestBodyMap(ServletRequest request, String... excludedParameterNames) {
        Map<String, Object> body;
        String json = getRequestBodyString(request);
        if (StringUtils.isNotBlank(json)) {
            body = JsonUtil.json2Map(json);
        } else {
            body = new HashMap<>();
        }
        if (body.size() > 0 && excludedParameterNames.length > 0) {
            for (String excludedParameterName : excludedParameterNames) {
                body.remove(excludedParameterName);
            }
        }
        return body;
    }

    /**
     * 获取指定URL去掉web项目根路径之后的相对路径URL
     *
     * @param request 请求
     * @param url     URL
     * @return 相对于web项目的URL
     */
    public static String getRelativeUrl(HttpServletRequest request, String url) {
        String root = request.getContextPath();
        if (!root.equals(Strings.SLASH) && url.startsWith(root)) {
            url = url.substring(root.length());
        }
        return url;
    }

    /**
     * 获取相对于web项目的请求URL，不包含请求参数串
     *
     * @param request 请求
     * @return 相对于web项目的请求URL
     */
    public static String getRelativeRequestUrl(HttpServletRequest request) {
        return getRelativeUrl(request, request.getRequestURI());
    }

    /**
     * 获取相对于web项目的包含请求参数串的请求URL
     *
     * @param request               请求
     * @param encode                是否进行字符转码
     * @param ignoredParameterNames 不包含在参数串中的参数名清单
     * @return 相对于web项目的请求URL
     */
    public static String getRelativeRequestUrlWithQueryString(HttpServletRequest request, boolean encode,
                                                              String... ignoredParameterNames) {
        String encoding = request.getCharacterEncoding();
        if (encoding == null) {
            encoding = System.getProperty("file.encoding", Strings.ENCODING_UTF8);
        }
        String url = getRelativeRequestUrl(request);
        String queryString = request.getQueryString();
        if (queryString != null) {
            if (ignoredParameterNames.length > 0) {
                String[] params = queryString.split("&");
                for (int i = 0; i < params.length; i++) {
                    for (String name : ignoredParameterNames) {
                        String prefix = name + "=";
                        if (params[i].startsWith(prefix)) {
                            params[i] = null;
                            break;
                        }
                    }
                    if (params[i] != null && encode) {
                        int index = params[i].indexOf('=');
                        if (index >= 0) {
                            String value = params[i].substring(index + 1);
                            try {
                                value = URLEncoder.encode(value, encoding);
                                params[i] = params[i].substring(0, index + 1) + value;
                            } catch (UnsupportedEncodingException e) {
                                LogUtil.error(WebHttpUtil.class, e);
                            }
                        }
                    }
                }
                queryString = "";
                for (String param : params) {
                    if (param != null) {
                        queryString += param + "&";
                    }
                }
                if (queryString.length() > 0) {
                    queryString = queryString.substring(0, queryString.length() - 1);
                }
            }
            if (queryString.length() > 0) {
                url += "?" + queryString;
            }
        }
        if (encode) {
            int index1 = url.lastIndexOf('/');
            int index2 = url.indexOf('.', index1);
            if (index2 > index1) {
                String tail = url.substring(index1 + 1, index2); // 取得链接的最后一级，并去掉访问后缀

                try {
                    tail = URLEncoder.encode(tail, encoding);
                } catch (UnsupportedEncodingException e) {
                    LogUtil.error(WebHttpUtil.class, e);
                }
                url = url.substring(0, index1 + 1) + tail + url.substring(index2);
            }
        }
        return url;
    }






    /**
     * 从指定HTTP请求中获取访问的协议名称，如：http、https<br>
     * 如果使用了nginx，则nginx配置中需添加：proxy_set_header X-Forwarded-Proto $scheme;
     *
     * @param request HTTP请求
     * @return 访问的协议名称
     */
    public static String getProtocol(HttpServletRequest request) {
        String protocol = request.getHeader("X-Forwarded-Proto");
        if (StringUtils.isNotBlank(protocol) && !HEADER_UNKNOWN.equalsIgnoreCase(protocol)) {
            // 多次反向代理后会有多个值，第一个为真实值。
            int index = protocol.indexOf(Strings.COMMA);
            if (index >= 0) {
                return protocol.substring(0, index);
            } else {
                return protocol;
            }
        }
        return request.getScheme();
    }


    /**
     * 判断指定请求是否AJAX请求
     *
     * @param request HTTP请求
     * @return 是否AJAX请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader(WebConstants.HEADER_AJAX_REQUEST))) {
            return true;
        }
        String referer = request.getHeader(WebConstants.HEADER_REFERER);
        if (referer != null && (referer.endsWith("/swagger-ui.html") || referer.endsWith("/doc.html"))) {
            return true;
        }
        return false;
    }




    /**
     * 从HTTP响应对象中获取图片输出流，该输出流专用于向浏览器客户端输出图片，其它情况请避免使用
     *
     * @param response HTTP响应对象
     * @return 图片输出流，如果出现IO错误则返回null
     */
    public static ServletOutputStream getImageOutputStream(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        try {
            return response.getOutputStream();
        } catch (Exception e) {
            LogUtil.error(WebHttpUtil.class, e);
        }
        return null;
    }



    public static Object removeSessionAttribute(HttpSession session, String name) {
        Object value = session.getAttribute(name);
        if (value != null) {
            session.removeAttribute(name);
        }
        return value;
    }

    /**
     * 解码指定参数
     *
     * @param request HTTP请求
     * @param param   解码前参数
     * @return 解码后参数
     */
    public static String decodeParameter(HttpServletRequest request, String param) {
        if (RequestMethod.GET.name().equalsIgnoreCase(request.getMethod())) {
            String encoding = request.getCharacterEncoding();
            if (encoding == null) {
                encoding = Strings.ENCODING_UTF8;
            }
            try {
                return URLDecoder.decode(param, encoding);
            } catch (UnsupportedEncodingException e) {
                LogUtil.error(WebHttpUtil.class, e); // 编码已确保有效，不应该出现该异常
            }
        }
        return param;
    }

    /**
     * 根据名字从请求中获取cookie
     *
     * @param request 请求
     * @param name    cookie名称
     * @return cookie对象
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        if (StringUtils.isNotBlank(name)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(name)) {
                        return cookie;
                    }
                }
            }
        }
        return null;
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie cookie = getCookie(request, cookieName);
        return cookie == null ? null : cookie.getValue();
    }

    /**
     * 获取Map形式的Cookie集
     *
     * @param request 请求
     * @return Map形式的Cookie集
     */
    public static Map<String, Cookie> getCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 创建Cookie对象
     *
     * @param name     名称
     * @param value    值
     * @param path     路径
     * @param maxAge   有效时间，单位：秒
     * @param httpOnly 是否禁止客户端javascript访问
     * @return Cookie对象
     */
    public static Cookie createCookie(String name, String value, String path, int maxAge, boolean httpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(httpOnly);
        cookie.setPath(path);
        return cookie;
    }

    /**
     * 创建Cookie对象
     *
     * @param request  请求
     * @param name     名称
     * @param value    值
     * @param maxAge   有效时间，单位：秒
     * @param httpOnly 是否禁止客户端javascript访问
     * @return Cookie对象
     */
    public static Cookie createCookie(HttpServletRequest request, String name, String value, int maxAge,
                                      boolean httpOnly) {
        return createCookie(name, value, getCookieDefaultPath(request), maxAge, httpOnly);
    }

    private static String getCookieDefaultPath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        if (StringUtils.isBlank(contextPath)) {
            contextPath = Strings.SLASH;
        }
        return contextPath;
    }

    /**
     * 添加Cookie
     *
     * @param request  请求
     * @param response 响应
     * @param name     名称
     * @param value    值
     * @param maxAge   有效时间，单位：秒
     */
    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
                                 int maxAge) {
        Cookie cookie = createCookie(request, name, value, maxAge, true);
        response.addCookie(cookie);
    }

    /**
     * 添加长期有效的Cookie
     *
     * @param request  请求
     * @param response 响应
     * @param name     名称
     * @param value    值
     */
    public static void addLongTermCookie(HttpServletRequest request, HttpServletResponse response, String name,
                                         String value) {
        addCookie(request, response, name, value, Integer.MAX_VALUE);
    }

    /**
     * 设置Cookie，该方法只能设置一个Cookie，响应内已设置的Cookie将被覆盖，但可设置同源策略
     *
     * @param response 响应
     * @param name     名称
     * @param value    值
     * @param path     路径
     * @param maxAge   有效时间，单位：秒
     * @param httpOnly 是否禁止客户端javascript访问
     * @param sameSite 同源策略
     */
    public static void setCookie(HttpServletResponse response, String name, String value, String path,
                                 int maxAge, boolean httpOnly, org.springframework.boot.web.server.Cookie.SameSite sameSite) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path(path)
                .maxAge(maxAge)
                .httpOnly(httpOnly)
                .sameSite(sameSite.name())
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
                                 int maxAge) {
        setCookie(response, name, value, getCookieDefaultPath(request), maxAge, true,
                org.springframework.boot.web.server.Cookie.SameSite.LAX);
    }

    /**
     * 设置长期有效的Cookie
     *
     * @param request  请求
     * @param response 响应
     * @param name     名称
     * @param value    值
     */
    public static void setLongTermCookie(HttpServletRequest request, HttpServletResponse response, String name,
                                         String value) {
        setCookie(request, response, name, value, Integer.MAX_VALUE);
    }

    /**
     * 设置会话内有效的Cookie
     *
     * @param request  请求
     * @param response 响应
     * @param name     名称
     * @param value    值
     */
    public static void setSessionCookie(HttpServletRequest request, HttpServletResponse response, String name,
                                        String value) {
        setCookie(request, response, name, value, -1);
    }

    /**
     * 移除cookie
     *
     * @param response 响应
     * @param name     cookie名称
     * @param path     cookie路径
     * @author jianglei
     */
    public static void removeCookie(HttpServletResponse response, String name, String path) {
        Cookie cookie = new Cookie(name, Strings.EMPTY);
        cookie.setPath(path);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, Strings.EMPTY);
        cookie.setPath(getCookieDefaultPath(request));
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }






    /**
     * 将request中的所有参数都复制到属性集中
     *
     * @param request 请求
     */
    public static void copyParameters2Attributes(HttpServletRequest request) {
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            request.setAttribute(name, request.getParameter(name));
        }
    }



    /**
     * 获取客户端信息
     *
     * @param userAgentHeader userAgent请求头
     * @return 客户端信息
     */
    public static UserAgent getUserAgent(String userAgentHeader) {
        if (StringUtils.isNotBlank(userAgentHeader)) {
            return UserAgentUtil.parse(userAgentHeader);
        }
        return null;
    }

    /**
     * 获取客户端信息
     *
     * @param request 请求
     * @return 客户端信息
     */
    public static UserAgent getUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        if (userAgent != null) {
            return UserAgentUtil.parse(userAgent);
        }
        return null;
    }

    /**
     * 判断请求是否来自微软IE浏览器
     *
     * @param request 请求
     * @return 请求是否来自微软IE浏览器
     */
    public static boolean isRequestFromMsie(HttpServletRequest request) {
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        if (userAgent != null) {
            userAgent = userAgent.toUpperCase();
            return userAgent.contains("MSIE") || userAgent.contains("TRIDENT");
        }
        return false;
    }

    /**
     * 判断请求是否来自微信内嵌浏览器
     *
     * @param request 请求
     * @return 请求是否来自微信内嵌浏览器
     */
    public static boolean isRequestFromWechat(HttpServletRequest request) {
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        if (userAgent != null) {
            userAgent = userAgent.toUpperCase();
            return userAgent.contains("MICROMESSENGER");
        }
        return false;
    }


    public static String getHeader(HttpServletRequest request, String name) {
        String value = request.getHeader(name);
        if (value == null) {
            Enumeration<String> values = request.getHeaders(name);
            if (values != null && values.hasMoreElements()) {
                StringBuilder sbv = new StringBuilder();
                while (values.hasMoreElements()) {
                    sbv.append(", ").append(values.nextElement());
                }
                sbv.delete(0, 2);
                value = sbv.toString();
            }
        }
        return value;
    }


    /**
     * 获取所有请求头信息
     *
     * @param request 请求
     * @return 所有请求头信息
     */
    public static Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = getHeader(request, name);
            headers.put(name, value);
        }
        return headers;
    }

    /**
     * 依次尝试获取参数和属性值
     *
     * @param request http请求
     * @param name    参数或属性名
     * @return 参数或属性值
     */
    public static String getParameterOrAttribute(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value == null) {
            value = (String) request.getAttribute(name);
        }
        return value;
    }




}
