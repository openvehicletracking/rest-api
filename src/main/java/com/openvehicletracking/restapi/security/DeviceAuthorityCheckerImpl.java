package com.openvehicletracking.restapi.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class DeviceAuthorityCheckerImpl implements DeviceAuthorityChecker {

    @Override
    public boolean check(Collection<? extends GrantedAuthority> authorities, String deviceId) {
        return authorities.stream().anyMatch(auth -> auth.getAuthority().equals(deviceId));
    }
}
