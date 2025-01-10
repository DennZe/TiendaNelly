package com.inventario.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventario.modelo.Producto;
import com.inventario.repositorio.ProductoRepository;

@Controller
public class ProductoController {
    @Autowired
    private ProductoRepository productoRepository;
     
    @GetMapping({"/", ""})
    public String verPaginaInicio(Model modelo) {
        List<Producto> productos = productoRepository.findAll();
        modelo.addAttribute("productos", productos);
        return "index";
    }
    
    @GetMapping("/registroProducto")
    public String mostrarRegistroProd(Model modelo) {
        modelo.addAttribute("producto", new Producto());
        return "registroProducto";
    }
    
    @PostMapping("/registroProducto")
    public String guardarProducto(
            @Validated Producto producto,
            BindingResult bindingResult,
            RedirectAttributes redirect,
            Model modelo) {

        if (bindingResult.hasErrors()) {
            modelo.addAttribute("producto", producto);
            return "registroProducto"; // Regresar a la vista del formulario con errores
        }

        productoRepository.save(producto);
        redirect.addFlashAttribute("msgExito", "El producto se ha guardado con éxito");
        return "redirect:/"; // Redirigir al inicio tras éxito
    	}
    @GetMapping("/{id}/editar")
    public String mostrarRegistroEditarProd(@PathVariable Integer id, Model modelo) {
        Producto producto = productoRepository.getById(id);
        modelo.addAttribute("producto", producto);
        return "registroProducto";
    }
    
    @PostMapping("/{id}/editar")
    public String actualizarProducto(
    		@PathVariable Integer id,@Validated Producto producto, BindingResult bindingResult, RedirectAttributes redirect, Model modelo) {
    		Producto productoDB = productoRepository.getById(id);
        if (bindingResult.hasErrors()) {
            modelo.addAttribute("producto", producto);
            return "registroProducto"; // Regresar a la vista del formulario con errores
        }
        productoDB.setNombreProducto(producto.getNombreProducto());
        productoDB.setStock(producto.getStock());
        productoDB.setFechaProducto(producto.getFechaProducto());
        
        productoRepository.save(productoDB);
        redirect.addFlashAttribute("msgExito", "El producto se ha actualizado con éxito");
        return "redirect:/"; // Redirigir al inicio tras éxito
    
    }
    
    @PostMapping("/{id}/eliminar")
    public String eliminarProducto(@PathVariable Integer id, RedirectAttributes redirect) {
        Optional<Producto> productoOptional = productoRepository.findById(id);
        if (productoOptional.isPresent()) {
            productoRepository.delete(productoOptional.get());
            redirect.addFlashAttribute("msgExito", "El producto se ha eliminado correctamente.");
        } else {
            redirect.addFlashAttribute("msgError", "El producto no fue encontrado.");
        }
        return "redirect:/";
    }

}     