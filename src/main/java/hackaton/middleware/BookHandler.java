package hackaton.middleware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import hackaton.controller.BookController;

@Component
public class BookHandler implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getRequestURI().contains(BookController.PREFIX)) {
            if (request.getMethod().toLowerCase().equals("post")) {

                if (this.hasName(request, response)) {
                    return this.hasIsbn(request, response);
                } else {
                    return false;
                }
            } else if (request.getMethod().toLowerCase().equals("put")) {
                return this.hasName(request, response);
            }
        }

        return true;

    }

    private boolean hasName(final HttpServletRequest request, final HttpServletResponse response) {
        if (request.getParameter(BookController.NAME_PARAM) == null) {
            response.setHeader("Error", "Name not found");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }

        return true;

    }

    private boolean hasIsbn(final HttpServletRequest request, final HttpServletResponse response) {
        if (request.getParameter(BookController.ID_PARAM) == null) {
            response.setHeader("Error", "No ISBN found");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }

        return true;
    }

}
