package com.platzimarket.persistence.mapper;

import com.platzimarket.domain.Product;
import com.platzimarket.persistence.entity.Producto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {
    @Mappings({
            @Mapping(source = "idProducto", target ="productId"),
            @Mapping(source = "nombre", target ="name"),
            @Mapping(source = "idCategoria", target ="idCategory"),
            @Mapping(source = "precioVenta", target ="price"),
            @Mapping(source = "cantidadStock", target ="stock"),
            @Mapping(source = "estado", target ="active"),
            @Mapping(source = "categoria", target ="category")
    })
    Product toProduct(Producto producto);

    List<Product> toProducts(List<Producto> productos);


    @InheritInverseConfiguration
    @Mapping(target = "codigoBarras", ignore = true)
    Producto toProducto(Product product);

}
