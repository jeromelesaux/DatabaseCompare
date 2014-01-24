package fr.axione.dbcompare.parser.database;

import fr.axione.dbcompare.model.common.ColumnType;
import fr.axione.dbcompare.model.common.ConstraintType;
import fr.axione.dbcompare.model.dbitem.*;

import java.sql.*;

/**
 * Created by jlesaux on 21/01/14.
 */
public class DatabaseStructure {

    DatabaseMetaData meta;





    public Schema getSchema(Connection connection) throws SQLException {
        Schema schema = null;
        meta  = connection.getMetaData();

        schema = new Schema();
        ResultSet schemaResult = meta.getSchemas();
        if (!schemaResult.isBeforeFirst() && schemaResult.getRow() == 0 ) {
            // get information from catalogue
            schemaResult.close();
            schemaResult = meta.getCatalogs();
            schemaResult.next();
            schema.setName(schemaResult.getString(1));
        }
        else {
            schemaResult.next();
            schema.setName(schemaResult.getString(1));
        }
        schemaResult.close();

        schema = getTables(schema);

        connection.close();
        return schema;
    }

    protected Table setPrimaryKeys(Table table) throws SQLException {
        ResultSet primaryKeyResults = meta.getPrimaryKeys(null,null,table.getName());
        while (primaryKeyResults.next()){
            String columnName = primaryKeyResults.getString("COLUMN_NAME");
            if (table.getColumns().containsKey(columnName)) {
                table.getColumns().get(columnName).setIsPrimaryKey(true);
            }
        }
        return table;
    }

    protected Table setForeignKeys(Table table) throws SQLException {
        ResultSet foreignKeyResults = meta.getExportedKeys(null, null, table.getName());
        while (foreignKeyResults.next()){
            String fkColumnName = foreignKeyResults.getString("FKCOLUMN_NAME");
            String fkTableName = foreignKeyResults.getString("FKTABLE_NAME");
            String pkTableName = foreignKeyResults.getString("PKTABLE_NAME");
            String pkColumnName = foreignKeyResults.getString("PKCOLUMN_NAME");
            String fkName = foreignKeyResults.getString("FK_NAME");
            if (table.getSchema().getTables().containsKey(fkTableName)) {
                Table fkTable = table.getSchema().getTables().get(fkTableName);
                if (fkTable.getColumns().containsKey(fkColumnName)) {
                    Column fkColumn = fkTable.getColumns().get(fkColumnName);

                    fkColumn.setIsForeignKey(true);
                    Index index = new Index(table.getSchema());
                    index.setName(fkName);
                    index.getColumns().add(fkColumn);
                    index.setType(ConstraintType.FOREIGN_KEY);
//                    Constraint constraint = new Constraint(table.getSchema());
//                    constraint.setName(fkName);
//                    constraint.setForeignColumn(fkColumn);
                    if  (table.getName().equals(pkTableName) && table.getColumns().containsKey(pkColumnName)) {
//                        constraint.setPrimaryColumn(table.getColumns().get(pkColumnName));
                        index.getColumns().add(table.getColumns().get(pkColumnName));
                    }
                    fkTable.getIndexes().put(index.getName(),index);
                    table.getIndexes().put(index.getName(),index);
//                    fkTable.getConstraints().put(constraint.getName(),constraint);
//                    table.getConstraints().put(constraint.getName(),constraint);
//                    table.getSchema().getConstraints().put(constraint.getName(),constraint);
                }
            }

        }
        return table;
    }

    protected Table getColumns(Table table) throws SQLException {
        try  {
            Statement statement = meta.getConnection().createStatement();
            ResultSet columnResults = meta.getColumns(null,table.getSchema().getName(),table.getName(),"%");
            Statement s = columnResults.getStatement();
            while (columnResults.next()) {
                String colName = columnResults.getString("COLUMN_NAME");
                String colType = columnResults.getString("TYPE_NAME");
                int  colSize = columnResults.getInt("COLUMN_SIZE");
                int nullable = columnResults.getInt("NULLABLE");

                Column column = new Column(colName,table);
                column.setSize(Integer.valueOf(colSize));
                column.setType(getType(colType));
                column.setNullable(nullable ==DatabaseMetaData.columnNullable ? true : false);
                table.getColumns().put(colName,column);
            }

            columnResults.close();
            s.close();
            statement.close();
        }  catch (SQLException e) {
            throw e;
        }
        table = setPrimaryKeys(table);
        table = setForeignKeys(table);
        return table;
    }

    protected Schema getTables(Schema schema) throws SQLException {

        //String[] TABLE_TYPES = {"TABLE"};
        ResultSet tablesResults = meta.getTables(null,null,"%",new String[] {"TABLE"});
        while (tablesResults.next()){
            Table table = new Table(tablesResults.getString(3),schema);
//            ResultSet indexesResults = meta.getIndexInfo(null,null,table.getName(),false,true);
//            while (indexesResults.next()) {
//                int type = indexesResults.getShort("TYPE");
//                String indexName = indexesResults.getString("INDEX_NAME");
//                boolean isUniq = indexesResults.getBoolean("NON_UNIQUE");
//                String columnName = indexesResults.getString("COLUMN_NAME");
//                System.out.println(columnName);
//            }
            schema.getTables().put(table.getName(), table);
        }

        tablesResults.close();


        // retrieve all tables :
        for (String tableName : schema.getTables().keySet()) {
            Table table = schema.getTables().get(tableName);
            table = getColumns(table);
            schema.getTables().put(tableName,table);
        }
        return schema;
    }

    protected Schema getViews(Schema schema){
        return schema;
    }

    protected  Schema getIndexes(Schema schema) {
        return schema;
    }


    protected ColumnType getType(String type) {
        ColumnType ctype =  ColumnType.UNKNOWN;
        try {
            ctype = ColumnType.valueOf(type.replace(' ','_'));
        } catch (IllegalArgumentException e) {
            ctype = ColumnType.UNKNOWN;
        }
        return ctype;
    }
}
