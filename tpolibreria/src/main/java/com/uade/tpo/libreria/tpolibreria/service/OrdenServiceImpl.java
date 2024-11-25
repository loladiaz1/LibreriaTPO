package com.uade.tpo.libreria.tpolibreria.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uade.tpo.libreria.tpolibreria.repository.OrdenRepository;
import com.uade.tpo.libreria.tpolibreria.repository.UsuarioRepository;

import jakarta.mail.MessagingException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uade.tpo.libreria.tpolibreria.controllers.orden.OrdenRequest;
import com.uade.tpo.libreria.tpolibreria.entity.Carrito;
import com.uade.tpo.libreria.tpolibreria.entity.GiftCard;
import com.uade.tpo.libreria.tpolibreria.entity.Libro;
import com.uade.tpo.libreria.tpolibreria.entity.Orden;
import com.uade.tpo.libreria.tpolibreria.entity.ProductoCarrito;
import com.uade.tpo.libreria.tpolibreria.entity.Usuario;
import com.uade.tpo.libreria.tpolibreria.repository.CarritoRepository;
import com.uade.tpo.libreria.tpolibreria.repository.GiftCardRepository;
import com.uade.tpo.libreria.tpolibreria.repository.LibroRepository;

@Service
public class OrdenServiceImpl implements OrdenService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private OrdenRepository OrdenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GiftCardRepository giftCardRepository;

    @Autowired
    private LibroRepository LibroRepository;

    @Autowired
    private MailService MailService;

    @Override
    public Orden createOrden(OrdenRequest ordenRequest) {
        Carrito carrito = carritoRepository.findByMail(ordenRequest.getMail());
            //.orElseThrow(() -> new RuntimeException("No se encontró un carrito asociado al correo: " + mail));

        Orden ordenNueva = new Orden();
        Optional<GiftCard> giftCard =giftCardRepository.findByCodigo(ordenRequest.getCodigo());
        ordenNueva.setGiftCard(giftCard.get());
        ordenNueva.setTotalSinDescuento(carrito.getTotal());
        if (ordenNueva.getGiftCard() != null){
            ordenNueva.setTotalConDescuento(carrito.getTotal() * (1 - ordenNueva.getGiftCard().getDescuento()));
            ordenNueva.setDescuento(ordenNueva.getGiftCard().getDescuento());  
        }else{
            ordenNueva.setTotalConDescuento(carrito.getTotal());
            ordenNueva.setDescuento(0.0);
        }

        Usuario usuario = usuarioRepository.findByMail(ordenRequest.getMail())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el mail: " + ordenRequest.getMail()));
        ordenNueva.setUsuario(usuario);

        // Convertir productos del carrito en JSON
        List<Map<String, Object>> productos = new ArrayList<>();
        List<ProductoCarrito> productosCarrito= carrito.getProductosCarrito();
        for (ProductoCarrito prodcarr : productosCarrito) {
            Map<String, Object> producto = new HashMap<>();
            producto.put("isbn", prodcarr.getLibro().getIsbn());
            producto.put("titulo", prodcarr.getLibro().getTitulo());
            producto.put("cantidad", prodcarr.getCantidad());
            producto.put("precioUnitario", prodcarr.getLibro().getPrecio());
            productos.add(producto);

            // Actualizar el stock del libro
            int cantARestar = prodcarr.getCantidad();
            Libro libro = prodcarr.getLibro();
            int nuevaCantidad = libro.getStock() - cantARestar;
            
            if (nuevaCantidad < 0) {
                throw new IllegalArgumentException("Error al comprar.");
            }
            libro.setStock(nuevaCantidad);
            LibroRepository.save(libro);

            // Serializar a JSON usando Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String productosJson = objectMapper.writeValueAsString(productos);
                ordenNueva.setProductosComprados(productosJson);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error al convertir productos a JSON: " + e.getMessage());
            }
        }

        ordenNueva.setEstado("En proceso"); 
        ordenNueva.setFecha(LocalDate.now());

        String productosHtml = "";
        for (ProductoCarrito prodcarr : productosCarrito) {
            productosHtml += "<li style=\"padding: 10px 0; border-bottom: 1px solid #eee;\">" +
                            "<span style=\"display: block; font-weight: bold;\">" + prodcarr.getLibro().getTitulo() + "</span>" +
                            "<span>Cantidad: " + prodcarr.getCantidad() + "</span><br>" +
                            "<span>Precio Unitario: $" + prodcarr.getLibro().getPrecio() + "</span>" +
                            "</li>";
        }

        String direccionEnvio = usuario.getDireccion();
        String subtotal = String.format("$%.2f", carrito.getTotal());
        String costoEnvio = String.format("$%.2f", 3500.0);
        String descuentoEnvio = String.format("-$%.2f", 3500.0);
        String total = String.format("$%.2f", ordenNueva.getTotalConDescuento());

        String htmlBody = 
        "<html>" +
        "<body style=\"font-family: Arial, sans-serif; line-height: 1.6; color: #333; padding: 20px; background-color: #f9f9f9;\">" +
            "<div style=\"max-width: 600px; margin: auto; background: #fff; border-radius: 10px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); padding: 20px;\">" +
                "<h2 style=\"color: #5a67d8; text-align: center;\">¡Muchas gracias por tu compra!</h2>" +
                "<p style=\"text-align: center;\">Ya recibimos tu orden y pronto será despachada. Aquí tienes los detalles de tu compra:</p>" +
                "<hr style=\"border: none; border-top: 1px solid #eee; margin: 20px 0;\">" +

                "<h3 style=\"color: #5a67d8;\">RESUMEN DE TU ORDEN</h3>" +
                "<p><strong>Dirección de Envío:</strong> " + direccionEnvio + "</p>" +
                "<p><strong>Método de Pago:</strong> Tarjeta de Crédito</p>" +

                "<h4 style=\"margin-top: 20px; color: #5a67d8;\">Tus Productos</h4>" +
                "<ul style=\"list-style: none; padding: 0;\">" +
                    productosHtml +
                "</ul>" +

                "<h4 style=\"margin-top: 20px; color: #5a67d8;\">Resumen de la Orden</h4>" +
                "<table style=\"width: 100%; border-collapse: collapse; margin-top: 10px;\">" +
                    "<tr>" +
                        "<td style=\"padding: 5px; text-align: left;\">Subtotal</td>" +
                        "<td style=\"padding: 5px; text-align: right;\">" + subtotal + "</td>" +
                    "</tr>" +
                    "<tr>" +
                        "<td style=\"padding: 5px; text-align: left;\">Costo de Envío</td>" +
                        "<td style=\"padding: 5px; text-align: right;\">" + costoEnvio + "</td>" +
                    "</tr>" +
                    "<tr>" +
                        "<td style=\"padding: 5px; text-align: left;\">Descuento en el Envío</td>" +
                        "<td style=\"padding: 5px; text-align: right;\">" + descuentoEnvio + "</td>" +
                    "</tr>" +
                    "<tr style=\"border-top: 2px solid #eee; font-weight: bold;\">" +
                        "<td style=\"padding: 5px; text-align: left;\">Total</td>" +
                        "<td style=\"padding: 5px; text-align: right;\">" + total + "</td>" +
                    "</tr>" +
                "</table>" +

                "<p style=\"margin-top: 20px; text-align: center;\">" +
                    "Esperamos que hayas disfrutado del proceso de compra. Si tienes preguntas, no dudes en " +
                    "<a href=\"mailto:thegoldenfeather2024@gmail.com\" style=\"color: #5a67d8;\">contactarnos</a>." +
                "</p>" +
                "<p style=\"text-align: center; font-size: 0.9em; color: #888; margin-top: 20px;\">The Golden Feather - Librería Online</p>" +
            "</div>" +
        "</body>" +
        "</html>";

        try {
            MailService.sendHtmlMail(
                ordenRequest.getMail(),
                "¡Gracias por tu compra!",
                htmlBody
            );
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage(), e);
        }

        //Vacio de carrito, en vaciar carrito se guarda el carrito nuevo en repository:
        carritoService.vaciarCarrito(ordenRequest.getMail());

        return OrdenRepository.save(ordenNueva);

    }

    @Override
    public Page<Orden> getOrdenes(Pageable pageable) {
        return OrdenRepository.findAll(pageable);
    }

    @Override
    public List<Orden> getOrdenesByMail(String mail) {
        return OrdenRepository.findByMail(mail);
    
    }

    @Override
    public void eliminarOrden(Long id) {
        Orden Orden = OrdenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con el id: " + id));

        OrdenRepository.delete(Orden);
        }

    @Override
    public Optional<Orden> getOrdenById(Long id) {
        return OrdenRepository.findById(id);
    }

    @Override
    public void updateOrden(Long id, OrdenRequest ordenRequest) {
        Optional<Orden> ordenOptional = OrdenRepository.findById(id);
        if (ordenOptional.isPresent()) {
            Orden orden = ordenOptional.get();
            //Solo tengo que modificar el estado
            orden.setEstado(ordenRequest.getEstado());
            OrdenRepository.save(orden);
        } else {
            throw new RuntimeException("Orden no encontrada con ID: " + id);
        }
    }
    }

