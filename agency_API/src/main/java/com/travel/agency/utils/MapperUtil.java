package com.travel.agency.utils;

import java.text.Normalizer;
import java.util.Locale;
import java.util.function.Function;


public class MapperUtil {

    //La idea es crear 2 metodos: toDTO y toEntity y sobrecargarlos. Cambiarles los parametros
    //y el tipo de retorno. Obviamente, esto no es más que una propuesta mia. Si alguien tiene una idea mejor
    //o les parece correcto hacerlo de la manera tradicional con clases separadas para cada entidad, lo
    //discutimos en una reunion.
    //Si implementamos todos los convertidores en esta clase, este metodo va a quedar obsoleto.
    public static <T, D> D mapperEntity(Function<T, D> converter) {
        return converter.apply(null);
    }

}
