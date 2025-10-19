package tec.br.opticlinic.api.application.util;

import org.springframework.stereotype.Service;

@Service
public class StringUtilService {

    public String formatSql(String sql) {
        if (sql == null || sql.isBlank()) return sql;

        // Remove múltiplos espaços, tabs e quebras de linha
        String normalized = sql
                .replaceAll("[\\t\\n\\r]+", " ")
                .replaceAll(" +", " ")
                .trim();

        // Padroniza espaçamento após vírgulas e antes de WHERE/AND/OR
        normalized = normalized
                .replaceAll("\\s*,\\s*", ", ")
                .replaceAll("\\s+(?i)(WHERE|AND|OR|SET|FROM|VALUES|INTO|ORDER BY|GROUP BY)\\s+", " $1 ");

        // Coloca palavras-chave em maiúsculo (respeitando case-insensitive)
        String[] keywords = {
                "select", "insert", "update", "delete", "from", "where", "set",
                "values", "into", "order by", "group by", "limit", "offset",
                "join", "inner join", "left join", "right join", "on"
        };

        String formatted = normalized;
        for (String kw : keywords) {
            formatted = formatted.replaceAll("(?i)\\b" + kw + "\\b", kw.toUpperCase());
        }

        // Coloca um pequeno prefixo visual
        return formatted
                .replaceAll("(?i)SELECT", "\n🟩 SELECT")
                .replaceAll("(?i)UPDATE", "\n🟦 UPDATE")
                .replaceAll("(?i)INSERT", "\n🟨 INSERT")
                .replaceAll("(?i)DELETE", "\n🟥 DELETE")
                .replaceAll("(?i)WHERE", "\n   ➤ WHERE")
                .replaceAll("(?i)SET", "\n   ⚙ SET")
                .replaceAll("(?i)FROM", "\n   📄 FROM")
                .replaceAll("(?i)ORDER BY", "\n   ↳ ORDER BY")
                .replaceAll("(?i)GROUP BY", "\n   ↳ GROUP BY");
    }

}
