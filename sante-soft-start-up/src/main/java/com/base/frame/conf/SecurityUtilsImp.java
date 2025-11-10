package com.base.frame.conf;

import com.base.frame.httpsession.security.SecurityUtils;
import com.base.frame.socle.core.ISecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SecurityUtilsImp  implements ISecurityUtils {
    @Override
    public Optional<String> getCurrentUserLogin() {
        return SecurityUtils.getCurrentUserLogin();
    }

    @Override
    public Optional<String> getCurrentUserJWT() {
        return SecurityUtils.getCurrentUserJWT();
    }

    @Override
    public boolean isAuthenticated() {
        return SecurityUtils.isAuthenticated();
    }

    @Override
    public boolean isCurrentUserInRole(String authority) {
        return SecurityUtils.isCurrentUserInRole(authority);
    }

    @Override
    public List<GrantedAuthority> getRoles() {
        return SecurityUtils.getRoles();
    }
}
