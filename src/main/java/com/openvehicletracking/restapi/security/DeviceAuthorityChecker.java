package com.openvehicletracking.restapi.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface DeviceAuthorityChecker {

    boolean check(Collection<? extends GrantedAuthority> authorities, String deviceId);

}
