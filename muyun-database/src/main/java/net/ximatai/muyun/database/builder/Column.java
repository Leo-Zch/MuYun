package net.ximatai.muyun.database.builder;

public class Column {
    private String name;
    private String comment;
    private String type;
    private Object defaultValue;
    private boolean nullable = true;
    private boolean unique = false;
    private boolean primaryKey = false;
    private boolean sequence = false;
    private boolean indexed = false;

    public enum Type {
        VARCHAR, INT, BOOL
    }

    public static final Column ID_POSTGRES = new Column("id")
        .setPrimaryKey()
        .setType(Type.VARCHAR)
        .setDefaultValue("gen_random_uuid()");

    public static final Column DELETE_FLAG = new Column("b_delete")
        .setType(Type.BOOL)
        .setDefaultValue(false);

    public static final Column TREE_PID = new Column("pid")
        .setType(Type.VARCHAR)
        .setIndexed();

    public static final Column ORDER = new Column("n_order")
        .setSequence()
        .setIndexed();

    public static final Column CREATE = new Column("t_create")
        .setIndexed();

    private Column(String name) {
        this.name = name;
        this.type = buildTypeWithColumnName(name);
    }

    public static Column of(String name) {
        return new Column(name);
    }

    public Column setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Column setType(String type) {
        this.type = type;
        return this;
    }

    public Column setType(DataType type){
        this.type = type.toString();
        return this;
    }

    public Column setType(Type type) {
        this.type = type.toString();
        return this;
    }

    public Column setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public Column setNullable(boolean nullable) {
        this.nullable = nullable;
        return this;
    }

    public Column setUnique(boolean unique) {
        this.unique = unique;
        return this;
    }

    public Column setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
        return this;
    }

    public Column setSequence(boolean sequence) {
        this.sequence = sequence;
        return this;
    }

    public Column setIndexed(boolean indexed) {
        this.indexed = indexed;
        return this;
    }

    public Column setNullable() {
        this.nullable = true;
        return this;
    }

    public Column setUnique() {
        this.unique = true;
        return this;
    }

    public Column setPrimaryKey() {
        this.primaryKey = true;
        this.nullable = false;
        return this;
    }

    public Column setSequence() {
        this.sequence = true;
        return this;
    }

    public Column setIndexed() {
        this.indexed = true;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public String getType() {
        return type;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public boolean isNullable() {
        return nullable;
    }

    public boolean isUnique() {
        return unique;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public boolean isSequence() {
        return sequence;
    }

    public boolean isIndexed() {
        return indexed;
    }

    String buildTypeWithColumnName(String name) {
        String type = null;

        if ("id".equals(name)) {
            type = "varchar";
        } else if ("pid".equals(name)) {
            type = "varchar";
        } else if (name.startsWith("v_")) {
            type = "varchar";
        } else if (name.startsWith("i_")) {
            type = "int";
        } else if (name.startsWith("b_")) {
            type = "boolean";
        } else if (name.startsWith("t_")) {
            type = "timestamp";
        } else if (name.startsWith("d_")) {
            type = "date";
        } else if (name.startsWith("n_")) {
            type = "numeric";
        } else if (name.startsWith("id_")) {
            type = "varchar";
        } else if (name.startsWith("j_")) {
            type = "jsonb";
        } else if (name.startsWith("dict_")) {
            type = "varchar";
        } else if (name.startsWith("file_")) {
            type = "varchar";
        } else if (name.startsWith("files_")) {
            type = "varchar[]";
        } else if (name.startsWith("ids_")) {
            type = "varchar[]";
        } else if (name.startsWith("dicts_")) {
            type = "varchar[]";
        }

        return type;
    }
}
