package com.feedmesomore.database;

/**
 * User: dayel.ostraco
 * Date: 1/29/12
 * Time: 6:14 AM
 */
public enum BabyColumn {

    ID("ID", "TEXT PRIMARY KEY"),
    NAME ("NAME", "TEXT"),
    SEX("SEX", "TEXT"),
    HEIGHT("HEIGHT", "TEXT"),
    WEIGHT("WEIGHT", "TEXT"),
    DOB("DOB", "TEXT"),
    PICTURE("PICTURE", "TEXT"),
    LATITUDE("LATITUDE", "TEXT"),
    LONGITUDE("LONGITUDE", "TEXT"),
    CREATED_DATE("CREATED_DATE", "TEXT"),
    LAST_MOD_DATE("LAST_MOD_DATE", "TEXT");

    public static final String TABLE_NAME = "BABIES";
    private final String columnName;
    private final String columnType;
    
    BabyColumn(String columnName, String columnType) {
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

        BabyColumn[] columns = BabyColumn.values();
        for (int i=0; i<columns.length; i++) {

            BabyColumn column = columns[i];

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
        return new String[] { ID.columnName(), NAME.columnName(), SEX.columnName(), HEIGHT.columnName(),
                WEIGHT.columnName(), DOB.columnName(), PICTURE.columnName(), LATITUDE.columnName(),
                LONGITUDE.columnName(), CREATED_DATE.columnName(), LAST_MOD_DATE.columnName()};
    }
}