package fr.axione.dbcompare.model.dmditem;

import fr.axione.dbcompare.model.common.ColumnType;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by jlesaux on 22/01/14.
 */
public class ColumnDmdTypeMapper {
    final static HashMap<String,ColumnType> COLUMNDMDTYPEMAP = (HashMap<String, ColumnType>) Collections.unmodifiableMap(
            new HashMap<String, ColumnType>() {{
                put("LOGDT024",ColumnType.VARCHAR2);
                put("LOGDT019",ColumnType.NUMBER);
                put("LOGDT007", ColumnType.DATE);
            }}
    );

    public static ColumnType getColumnType(String oracleDmdColumnType) {
        if (COLUMNDMDTYPEMAP.containsKey(oracleDmdColumnType)) {
            return COLUMNDMDTYPEMAP.get(oracleDmdColumnType);
        }

        return ColumnType.UNKNOWN;
    }
}
