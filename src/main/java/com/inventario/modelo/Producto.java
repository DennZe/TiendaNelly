package com.inventario.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    @NotBlank(message = "Debe ingresar el nombre del producto")
    private String nombreProducto;

    @Min(value = 1, message = "El stock debe ser mayor a cero")
    private int stock;

    @DateTimeFormat(iso = ISO.DATE)
    @NotNull(message = "Debe ingresar la fecha de vencimiento del producto")
    private LocalDate fechaProducto;

    private LocalDateTime fechaRegistroProd;

    // Getters y Setters
    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDate getFechaProducto() {
        return fechaProducto;
    }

    public void setFechaProducto(LocalDate fechaProducto) {
        this.fechaProducto = fechaProducto;
    }

    public LocalDateTime getFechaRegistroProd() {
        return fechaRegistroProd;
    }

    public void setFechaRegistroProd(LocalDateTime fechaRegistroProd) {
        this.fechaRegistroProd = fechaRegistroProd;
    }

    // Agregar automáticamente la fecha y hora del registro
    @PrePersist
    public void agregarFechaRegistro() {
        fechaRegistroProd = LocalDateTime.now();
    }

    // Constructor con parámetros
    public Producto(Integer idProducto, String nombreProducto, int stock, LocalDate fechaProducto, 
                    LocalDateTime fechaRegistroProd) {
        super();
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.stock = stock;
        this.fechaProducto = fechaProducto;
        this.fechaRegistroProd = fechaRegistroProd;
    }

    // Constructor sin parámetros
    public Producto() {
        super();
    }
}
