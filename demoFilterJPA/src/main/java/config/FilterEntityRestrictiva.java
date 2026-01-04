package config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Table;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;

import java.util.HashSet;
import java.util.Set;

public class FilterEntityRestrictiva {

    private static final Set<String> FILTERED_TABLES = new HashSet<String>();

    public static void registerEntity(EntityManagerFactory emf) {
        Metamodel metamodel = emf.getMetamodel();
        for (EntityType<?> entity : metamodel.getEntities()) {
            Class<?> javaType = entity.getJavaType();
            if (IRestrictiva.class.isAssignableFrom(javaType)) {
                Table table = javaType.getAnnotation(Table.class);
                if (table != null && !table.name().isBlank()) {
                    FILTERED_TABLES.add(table.name().toLowerCase());
                }
                else {
                    FILTERED_TABLES.add(javaType.getSimpleName());
                }
            }
        }
    }

    public static boolean siFilter(String tableName) {
        return FILTERED_TABLES.contains(tableName.toLowerCase());
    }

    public static int getSizeFilterTable() {
        return FILTERED_TABLES.size();
    }
}