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
                put("LOGDT007",ColumnType.DATE);
                put("LOGDT028",ColumnType.CLOB);
                put("LOGDT005",ColumnType.BLOB);
                put("LOGDT027",ColumnType.INTEGER);
                put("LOGDT033",ColumnType.BLOB);
                put("LOGDT056",ColumnType.BINARY_DOUBLE);
                put("LOGDT055",ColumnType.BINARY_FLOAT);
                put("LOGDT034",ColumnType.CHAR);
                put("LOGDT029",ColumnType.BLOB);
                put("LOGDT006",ColumnType.CHAR);
                put("LOGDT025",ColumnType.CHAR);
                put("LOGDT030",ColumnType.BLOB);
                put("LOGDT054",ColumnType.DBURITYPE);
                put("LOGDT026",ColumnType.NUMBER);
                put("LOGDT020",ColumnType.NUMBER);
                put("LOGDT008",ColumnType.DATE);
                put("LOGDT021",ColumnType.FLOAT);
                put("LOGDT031",ColumnType.BLOB);
                put("LOGDT052",ColumnType.HTTPURITYPE);
                put("LOGDT049",ColumnType.INTERVAL_YEAR_TO_SECOND);
                put("LOGDT048",ColumnType.INTERVAL_YEAR_TO_MONTH);
                put("LOGDT010",ColumnType.BLOB);
                put("LOGDT011",ColumnType.INTEGER);
                put("LOGDT043",ColumnType.NUMBER);
                put("LOGDT035",ColumnType.NCHAR);
                put("LOGDT036",ColumnType.NCLOB);
                put("LOGDT019",ColumnType.NUMBER);
                put("LOGDT037",ColumnType.NVARCHAR2);
                put("LOGDT022",ColumnType.REAL);
                put("LOGDT032",ColumnType.ROWID);
                put("LOGDT038",ColumnType.DATE);
                put("LOGDT018",ColumnType.SMALLINT);
                put("LOGDT044",ColumnType.NUMBER);
                put("LOGDT039",ColumnType.VARCHAR2);
                put("LOGDT040",ColumnType.BLOB);
                put("LOGDT047",ColumnType.TIMESTAMP_WITH_LOCAL_TIME_ZONE);
                put("LOGDT046",ColumnType.TIMESTAMP_WITH_TIME_ZONE);
                put("LOGDT042",ColumnType.SMALLINT);
                put("LOGDT014",ColumnType.DATE);
                put("LOGDT015",ColumnType.TIMESTAMP);
                put("LOGDT057",ColumnType.CHAR);
                put("LOGDT051",ColumnType.URITYPE);
                put("LOGDT051",ColumnType.BLOB);
                put("LOGDT041",ColumnType.BLOB);
                put("LOGDT023",ColumnType.BLOB);
                put("LOGDT016",ColumnType.BLOB);
                put("LOGDT053",ColumnType.XDBURITYPE);
                put("LOGDT050",ColumnType.XMLTYPE);
            }}
    );

    public static ColumnType getColumnType(String oracleDmdColumnType) {
        if (COLUMNDMDTYPEMAP.containsKey(oracleDmdColumnType)) {
            return COLUMNDMDTYPEMAP.get(oracleDmdColumnType);
        }

        return ColumnType.UNKNOWN;
    }
}
