package hackaton.middleware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import hackaton.controller.AuthorController;

@Component
/**
 * HandlerInterceptor for the author.
 * In this we ONLY check if the parameters are valir or not.
 * Usually I would do that into the controller
 */
public class AuthorHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getRequestURI().contains(AuthorController.PREFIX)) {
            if (request.getMethod().toLowerCase().equals("post")) {
                return this.hasNameAndSurname(request, response);
            }

            if (request.getMethod().toLowerCase().equals("put")) {
                return this.hasNameOrSurname(request, response);
            }
        }

        return true;
        // return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    /**
     * Check if the request has "name" AND "surname" as a parameter, is so returns
     * true
     * 
     * @param request  : the request
     * @param response : the response, modified if parameters not found
     * @return : has the request name & surname as parameters?
     */
    private boolean hasNameAndSurname(final HttpServletRequest request, final HttpServletResponse response) {
        if (request.getParameter(AuthorController.NAME_PARAM) == null
                || request.getParameter(AuthorController.SURNAME_PARAM) == null) {
            response.setHeader("Error", "Name or Surname not found");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }

        return true;

    }

    /**
     * Check if the request has "name" OR "surname" as parameter, is so returns true
     * 
     * @param request  : the request
     * @param response : the response, modified if parameters not found
     * @return : has the request name & surname as parameters?
     */
    private boolean hasNameOrSurname(final HttpServletRequest request, final HttpServletResponse response) {
        if (request.getParameter(AuthorController.NAME_PARAM) == null
                && request.getParameter(AuthorController.SURNAME_PARAM) == null) {
            response.setHeader("Error", "A Name or a Surname must be provided");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }

        return true;
    }

}
