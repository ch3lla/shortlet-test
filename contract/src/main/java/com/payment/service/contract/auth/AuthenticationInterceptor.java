package com.payment.service.contract.auth;

import com.payment.service.contract.models.Profile;
import com.payment.service.contract.repositories.ProfileRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final ProfileRepository profileRepository;

    public AuthenticationInterceptor(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String profileIdHeader = request.getHeader("profileId");

        if (profileIdHeader == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: profile header missing.");
            return false;
        }

        try {
            Integer profileId = Integer.parseInt(profileIdHeader);
            Optional<Profile> profile = profileRepository.findById(profileId);

            if (profile.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized: profile not found.");
                return false;
            }

            request.setAttribute("profile", profile.get());
            return true;
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Bad Request: Invalid profileId format");
            return false;
        }
    }

}
