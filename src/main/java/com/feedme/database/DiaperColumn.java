package com.feedme.database;

/**
 * User: dayel.ostraco
 * Date: 1/29/12
 * Time: 6:14 AM
 */
public enum DiaperColumn {

    ID("ID", "INTEGER PRIMARY KEY"),
    TYPE("TYPE", "TEXT"),
    CONSISTENCY("CONSISTENCY", "TEXT"),
    COLOR("COLOR", "TEXT"),
    DATE("DATE", "TEXT"),
    TIME("TIME", "TEXT"),
    DATE_TIME("DATE_TIME", "TEXT"),
    CHILD_ID("CHILD_ID", "TEXT"),
    LATITUDE("LATITUDE", "TEXT"),
    LONGITUDE("LONGITUDE", "TEXT"),
    CREATED_DATE("CREATED_DATE", "TEXT"),
    LAST_MOD_DATE("LAST_MOD_DATE", "TEXT");

    public static final String TABLE_NAME = "DIAPERS";
    private final String columnName;
    private final String columnType;

    DiaperColumn(String columnName, String columnType) {
        this.columnName = columnName;
        this.columnType = columnType;
    }
    
    public String columnName() {
        return columnName;
    }
    
    public String columnType() {
        return columnType;
    }

    public static String createTableStatement() {
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE ");
        sb.append(TABLE_NAME);
        sb.append("(");

        DiaperColumn[] columns = DiaperColumn.values();
        for (int i=0; i<columns.length; i++) {

            DiaperColumn column = columns[i];

            if (i!=columns.length-1) {
                sb.append(column.columnName());
                sb.append(" ");
                sb.append(column.columnType());
                sb.append(",");

            } else {
                sb.append(column.columnName());
                sb.append(" ");
                sb.append(column.columnType());
                sb.append(")");
            }
        }

        return sb.toString();
    }

    public static String[] getColumnNames(){
        return new String[] { ID.columnName(), TYPE.columnName(), CONSISTENCY.columnName(), COLOR.columnName(),
                DATE.columnName(), TIME.columnName(), DATE_TIME.columnName(), LATITUDE.columnName(),
                LONGITUDE.columnName(), CREATED_DATE.columnName(), LAST_MOD_DATE.columnName()};
    }
}