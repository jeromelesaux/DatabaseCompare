import fr.axione.dbcompare.common.dbitem.Column;
import fr.axione.dbcompare.common.dbitem.ColumnType;
import fr.axione.dbcompare.common.dbitem.Schema;
import fr.axione.dbcompare.common.dbitem.Table;

/**
 * Created by jls on 20/01/2014.
 */
public class CreateDb {
    Schema schemaLeft;
    Schema schemaRight;
    Schema schemaDiff;

    public void initSchema() {
        schemaRight= new Schema();
        schemaRight.setName("Toto");
        schemaLeft = new Schema();
        schemaLeft.setName("Toto");
        schemaDiff = new Schema();
        schemaDiff.setName("titi");
    }

    public void initTables() {
        this.initSchema();

        Table t1 = new Table(schemaRight);
        t1.setName("T1");
        schemaRight.getTables().put(t1.getName(),t1);

        Table t2 = new Table(schemaRight);
        t2.setName("T2");
        schemaRight.getTables().put(t2.getName(),t2);

        Table t3 = new Table(schemaLeft);
        t3.setName("T1");
        schemaLeft.getTables().put(t3.getName(),t3);

        Table t4 = new Table(schemaLeft);
        t4.setName("T3");
        schemaLeft.getTables().put(t4.getName(),t4);

    }

    public void initColumns(){

        this.initTables();

        Column c1 = new Column("c1_",schemaRight.getTables().get("T1"));
        c1.setIsForeignKey(false);
        c1.setIsPrimaryKey(true);
        c1.setNullable(false);
        c1.setType(ColumnType.VARCHAR2);
        c1.setSize(Integer.valueOf(1024));
        schemaRight.getTables().get("T1").getColumns().put(c1.getName(),c1);

        Column c2 = new Column("c2_",schemaRight.getTables().get("T1"));
        c2.setIsForeignKey(false);
        c2.setIsPrimaryKey(false);
        c2.setNullable(false);
        c2.setType(ColumnType.NUMBER);
        c2.setSize(Integer.valueOf(52));
        schemaRight.getTables().get("T1").getColumns().put(c2.getName(),c2);


        Column c3 = new Column("c3_",schemaRight.getTables().get("T1"));
        c3.setIsForeignKey(false);
        c3.setIsPrimaryKey(false);
        c3.setNullable(false);
        c3.setType(ColumnType.NUMBER);
        c3.setSize(Integer.valueOf(12));
        schemaRight.getTables().get("T1").getColumns().put(c3.getName(),c3);

        Column c4 = new Column("c4_",schemaRight.getTables().get("T2"));
        c4.setIsForeignKey(false);
        c4.setIsPrimaryKey(false);
        c4.setNullable(false);
        c4.setType(ColumnType.NUMBER);
        c4.setSize(Integer.valueOf(14));
        schemaRight.getTables().get("T2").getColumns().put(c4.getName(),c4);


        Column c1b = new Column("c1_",schemaLeft.getTables().get("T1"));
        c1b.setIsForeignKey(false);
        c1b.setIsPrimaryKey(true);
        c1b.setNullable(true);
        c1b.setType(ColumnType.VARCHAR2);
        c1b.setSize(Integer.valueOf(1000));
        schemaLeft.getTables().get("T1").getColumns().put(c1b.getName(),c1b);

        Column c2b = new Column("c2_",schemaLeft.getTables().get("T1"));
        c2b.setIsForeignKey(false);
        c2b.setIsPrimaryKey(false);
        c2b.setNullable(false);
        c2b.setType(ColumnType.NUMBER);
        c2b.setSize(Integer.valueOf(52));
        schemaLeft.getTables().get("T1").getColumns().put(c2b.getName(),c2b);



    }
}
