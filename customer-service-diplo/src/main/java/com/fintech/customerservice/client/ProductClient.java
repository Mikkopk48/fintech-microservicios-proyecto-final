package com.fintech.customerservice.client;

import com.fintech.customerservice.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Cliente Feign para comunicarse con product-service.
 *
 * Parece una llamada a un método común, pero por debajo es una llamada HTTP:
 *   1. Feign le pregunta a Eureka dónde está "product-service".
 *   2. Si hay varias copias del servicio, LoadBalancer elige una.
 *   3. Arma el pedido GET /products/customer/{customerId}.
 *   4. Lo envía por la red a la dirección que le dio Eureka.
 *   5. Convierte el JSON de la respuesta en una List<ProductDto>.
 *
 * No hace falta escribir la implementación: Spring la genera sola
 * gracias a @EnableFeignClients en la clase main.
 *
 * La ruta, el parámetro y el tipo de respuesta tienen que coincidir
 * con el endpoint real de ProductController en product-service.
 * Los campos de ProductDto tienen que llamarse igual que los del JSON.
 *
 * Si product-service está apagado, la llamada falla, porque viaja por la red.
 */

@FeignClient(name = "product-service")
//tiene que ser el nombre exactamente que el que aparece en eureka con eso feign sabe a quien preguntarle(no se pone el puerto)

public interface ProductClient {

    @GetMapping("/products/customer/{customerId}")
    //la ruta completa del endpoint
    List<ProductDto> getProductsByCustomerId(@PathVariable("customerId") Long customerId);
    //trae la lista de products del cliente
}