package com.platzimarket.persistence.mapper;

import com.platzimarket.domain.Category;
import com.platzimarket.persistence.entity.Categoria;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mappings({
            @Mapping(source="idCategoria", target="categoryId"),
            @Mapping(source="descripcion", target="category"),
            @Mapping(source="estado", target="action")
    })
    Category toCategory(Categoria categoria);

    @InheritConfiguration
    @Mapping(target = "productos", ignore = true)

    Categoria toCategoria(Category category);

}
