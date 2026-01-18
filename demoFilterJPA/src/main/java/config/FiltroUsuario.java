package config;

import java.util.Map;
import java.util.function.Function;

public class FiltroUsuario {
    private static final Map<RestrictionType, Function<Campo,String>> RESOLVER
            //switch
            = Map.of(
            RestrictionType.BOOLEAN, Campo:: value,
            RestrictionType.CURRENT_USER,campo -> SecurityContext.getCurrentUser()
    );
    public static final Function<Campo,String> valorCampo = campo -> RESOLVER.get(campo.restrictionType()).apply(campo);

    //map entry-> Cada valor del mapa

    public static final Function<Map.Entry<String,Filtro>, String> construirFiltro =
            entry -> {
                Filtro filtro = entry.getValue();
                Campo campo = filtro.campo();
                return filtro.alias() + "." + campo.nombre() + "=" + valorCampo.apply(campo);
            };
}

