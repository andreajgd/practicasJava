package config;

import org.hibernate.resource.jdbc.spi.StatementInspector;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InspectorStatement implements StatementInspector {

    private static final Pattern FROM_JOIN_PATTERN =
            Pattern.compile("(from|join)\\s+([a-zA-Z0-9_]+)\\s+([a-zA-Z0-9_]+)", Pattern.CASE_INSENSITIVE);


    @Override
    public String inspect(String sql) {
        if (sql == null) return sql;

        String lowerSql = sql.toLowerCase();

        // Evita UPDATE, DELETE, INSERT
        if (lowerSql.startsWith("update") || lowerSql.startsWith("delete") || lowerSql.startsWith("insert")) {
            return sql;
        }

        // Extraer alias de tablas filtrables
        Map<String, String> tablasYAlias = extraerTablasYAlias(lowerSql);

        if (tablasYAlias.isEmpty()) {
            return sql;
        }

        // Construir filtros
        List<String> filtros = new ArrayList<>();
        tablasYAlias.forEach((tabla, alias) -> filtros.add(alias + ".restrictiva = false"));
        String filtroFinal = "(" + String.join(" AND ", filtros) + ")";

        // Insertar filtro antes de ORDER BY o OFFSET
        int posOrderBy = lowerSql.indexOf(" order by");
        int posOffset = lowerSql.indexOf(" offset");
        int insertPos = posOrderBy >= 0 ? posOrderBy
                : posOffset >= 0 ? posOffset
                : sql.length();

        if (lowerSql.contains(" where ")) {
            // Insertar AND después del WHERE
            Pattern patternWhere = Pattern.compile("where", Pattern.CASE_INSENSITIVE);
            Matcher matcher = patternWhere.matcher(sql);
            if (matcher.find()) {
                int pos = matcher.end();
                return sql.substring(0, pos) + " " + filtroFinal + " AND " + sql.substring(pos);
            }
        } else {
            // Insertar WHERE antes de ORDER BY/OFFSET
            return sql.substring(0, insertPos) + " WHERE " + filtroFinal + " " + sql.substring(insertPos);
        }

        return sql;
    }

    private Map<String, String> extraerTablasYAlias(String lowerSql) {
        Map<String, String> resultado = new LinkedHashMap<>();
        Matcher matcher = FROM_JOIN_PATTERN.matcher(lowerSql);
        while (matcher.find()) {
            String tabla = matcher.group(2).toLowerCase();
            String alias = matcher.group(3);
            if (FilterEntityRestrictiva.siFilter(tabla)) {
                resultado.put(tabla, alias);
            }
        }
        return resultado;
    }
}