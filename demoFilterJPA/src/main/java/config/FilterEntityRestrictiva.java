package config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Table;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;

import java.util.HashMap;
import java.util.Map;

public class FilterEntityRestrictiva {

    private static final Map<String,Campo> FILTERED_TABLES = new HashMap<>();

    public static void registerEntity(EntityManagerFactory emf) {
        Metamodel metamodel = emf.getMetamodel();
        for (EntityType<?> entity : metamodel.getEntities()) {
            Class<?> javaType = entity.getJavaType();
            if (javaType.isAnnotationPresent(RestrictivaUsuario.class))  {
                Table table = javaType.getAnnotation(RestrictivaUsuario.class).tipo();
                String value = javaType.getAnnotation(RestrictivaUsuario.class).valor();
                Table table = javaType.getAnnotation(Table.class);
                if (table != null && !table.name().isBlank()) {
                    FILTERED_TABLES.put(table.name().toLowerCase()
                            , new Campo(campo,restrictionType,value) );
                }
                else {
                    FILTERED_TABLES.put(javaType.getSimpleName.toLowerCase(),
                            new Campo(campo,restrictionType,value));
                }
            }
        }
    }

    public static boolean siFilter(String tableName) {
        return FILTERED_TABLES.containsKey(tableName.toLowerCase());
    }

    public static int getSizeFilterTable() {
        return FILTERED_TABLES.size();
    }

    public static Map<String,Campo> getFilteredTables() {
        return FILTERED_TABLES;
    }
}