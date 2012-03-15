package com.feedmesomore.database;

/**
 * User: dayel.ostraco
 * Date: 1/29/12
 * Time: 6:14 AM
 */
public enum SettingsColumn {

    ID("ID", "INTEGER PRIMARY KEY"),
    LIQUID("LIQUID", "TEXT DEFAULT 'oz'"),
    LENGTH("LENGTH", "TEXT DEFAULT 'in'"),
    WEIGHT("WEIGHT", "TEXT DEFAULT 'lbs'"),
    TEMPERATURE("TEMPERATURE", "TEXT DEFAULT 'F'"),
    SOUND("SOUND", "TEXT DEFAULT 'off'"),
    VIBRATE("VIBATE", "TEXT DEFAULT 'off'"),
    CREATED_DATE("CREATED_DATE", "TEXT"),
    LAST_MOD_DATE("LAST_MOD_DATE", "TEXT");

    public static final String TABLE_NAME = "SETTINGS";
    private final String columnName;
    private final String columnType;

    SettingsColumn(String columnName, String columnType) {
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

        SettingsColumn[] columns = SettingsColumn.values();
        for (int i=0; i<columns.length; i++) {

            SettingsColumn column = columns[i];

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
        return new String[] { ID.columnName(), LIQUID.columnName(), LENGTH.columnName(), WEIGHT.columnName(),
                TEMPERATURE.columnName(), SOUND.columnName(), VIBRATE.columnName(), CREATED_DATE.columnName(),
                LAST_MOD_DATE.columnName()};
    }
}