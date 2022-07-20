package hackaton.middleware;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import hackaton.controller.NameController;

@Component
public class AuthorHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getRequestURI().contains(NameController.PREFIX)) {
            if (request.getMethod().toLowerCase().equals("post")) {
                return this.hasNameAndSurname(request, response);
            }
    
            if (request.getMethod().toLowerCase().equals("put")) {
                return this.hasNameOrSurname(request, response);
            }
        }
        
        return true;
        //return HandlerInterceptor.super.preHandle(request, response, handler);
    }
    
    private boolean hasNameAndSurname(final HttpServletRequest request, final HttpServletResponse response) {
        if (request.getParameter(NameController.NAME_PARAM) == null
                || request.getParameter(NameController.SURNAME_PARAM) == null) {
            response.setHeader("Error", "Name or Surname not found");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }

        return true;

    }
    
    private boolean hasNameOrSurname(final HttpServletRequest request, final HttpServletResponse response) {
        if (request.getParameter(NameController.NAME_PARAM) == null
                && request.getParameter(NameController.SURNAME_PARAM) == null) {
            response.setHeader("Error", "A Name or a Surname must be provided");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }

        return true;
    }

    
}
