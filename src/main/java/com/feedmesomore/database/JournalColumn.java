package com.feedmesomore.database;

/**
 * User: dayel.ostraco
 * Date: 1/29/12
 * Time: 6:14 AM
 */
public enum JournalColumn {

    ID("ID", "TEXT PRIMARY KEY"),
    DATE ("DATE", "TEXT"),
    START_TIME("START_TIME", "TEXT"),
    END_TIME("END_TIME", "TEXT"),
    DATE_TIME("DATE_TIME", "TEXT"),
    FEED_TIME("FEED_TIME", "TEXT"),
    SIDE("SIDE", "TEXT"),
    OUNCES("OUNCES", "TEXT"),
    CHILD_ID("CHILD_ID", "INTEGER"),
    LATITUDE("LATITUDE", "TEXT"),
    LONGITUDE("LONGITUDE", "TEXT"),
    CREATED_DATE("CREATED_DATE", "TEXT"),
    LAST_MOD_DATE("LAST_MOD_DATE", "TEXT");

    public static final String TABLE_NAME = "JOURNAL_ENTRIES";
    private final String columnName;
    private final String columnType;

    JournalColumn(String columnName, String columnType) {
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

        JournalColumn[] columns = JournalColumn.values();
        for (int i=0; i<columns.length; i++) {

            JournalColumn column = columns[i];

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
        return new String[] { ID.columnName(), DATE.columnName(), START_TIME.columnName(), END_TIME.columnName(),
                DATE_TIME.columnName(), FEED_TIME.columnName(), SIDE.columnName(), OUNCES.columnName(),
                CHILD_ID.columnName(), LATITUDE.columnName(), LONGITUDE.columnName(), CREATED_DATE.columnName(),
                LAST_MOD_DATE.columnName()};
    }
}