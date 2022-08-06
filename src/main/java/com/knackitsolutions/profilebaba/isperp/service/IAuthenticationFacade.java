package com.knackitsolutions.profilebaba.isperp.service;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {

  Authentication getAuthentication();
}
