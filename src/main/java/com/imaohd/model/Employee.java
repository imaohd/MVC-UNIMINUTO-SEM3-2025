package com.imaohd.model;

public class Employee {

    private int id;
    private String name;
    private Rol rol;

    public Employee(String name, Rol rol) {
        this.name = name;
        this.rol = rol;
    }

    public Employee(int id, String name, Rol rol) {
        this.id = id;
        this.name = name;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", rol=" + rol +
                '}';
    }
}
