package com.imaohd.model;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Rol {
    CAJERO, GERENTE, SUPERVISOR;

    public static boolean isValidRol(String rol) {
        for (Rol rolValue : Rol.values()) {
            if (rolValue.name().equals(rol)) {
                return true;
            }
        }
        return false;
    }

    public static String cargosActivos() {
        return Arrays.stream(Rol.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }
}
