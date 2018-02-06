package ua.in.algoritm.storekeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MCM on 06.02.2018.
 */

public class DBProvider extends SQLiteOpenHelper {

    private static final String SQL_DELETE_DB= "DROP TABLE IF EXISTS goods;";

    private static final String SQL_CREATE_DB = "--\n" +
            "-- Файл сгенерирован с помощью SQLiteStudio v3.1.1 в Вт фев 6 23:21:02 2018\n" +
            "--\n" +
            "-- Использованная кодировка текста: UTF-8\n" +
            "--\n" +
            "PRAGMA foreign_keys = off;\n" +
            "BEGIN TRANSACTION;\n" +
            "\n" +
            "-- Таблица: goods\n" +
            "DROP TABLE IF EXISTS goods;\n" +
            "\n" +
            "CREATE TABLE goods (\n" +
            "    id        INTEGER    PRIMARY KEY AUTOINCREMENT\n" +
            "                         UNIQUE\n" +
            "                         NOT NULL,\n" +
            "    outer_id  CHAR (36)  UNIQUE\n" +
            "                         NOT NULL,\n" +
            "    name      CHAR (150) NOT NULL ON CONFLICT REPLACE\n" +
            "                         DEFAULT ('Не указано'),\n" +
            "    full_name CHAR (150) NOT NULL ON CONFLICT REPLACE\n" +
            "                         DEFAULT ('Не указано') \n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Таблица: goods_indexes\n" +
            "DROP TABLE IF EXISTS goods_indexes;\n" +
            "\n" +
            "CREATE TABLE goods_indexes (\n" +
            "    id         INTEGER         UNIQUE\n" +
            "                               NOT NULL\n" +
            "                               PRIMARY KEY,\n" +
            "    goods_id   CHAR (36)       REFERENCES goods (outer_id) ON DELETE CASCADE\n" +
            "                                                           ON UPDATE CASCADE\n" +
            "                               NOT NULL,\n" +
            "    index_type CHAR (1)        NOT NULL,\n" +
            "    val_int    BIGINT,\n" +
            "    val_str    CHAR (150),\n" +
            "    val_float  NUMERIC (12, 3) \n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Таблица: goods_units\n" +
            "DROP TABLE IF EXISTS goods_units;\n" +
            "\n" +
            "CREATE TABLE goods_units (\n" +
            "    id          INTEGER         PRIMARY KEY AUTOINCREMENT\n" +
            "                                UNIQUE\n" +
            "                                NOT NULL,\n" +
            "    obj_id      INTEGER         NOT NULL\n" +
            "                                REFERENCES Goods (id),\n" +
            "    outer_id    STRING (36)     NOT NULL\n" +
            "                                UNIQUE,\n" +
            "    unit_name   CHAR (15)       NOT NULL ON CONFLICT REPLACE\n" +
            "                                DEFAULT ('шт.'),\n" +
            "    weight      DECIMAL (12, 3) NOT NULL\n" +
            "                                DEFAULT (0),\n" +
            "    coefficient DECIMAL (12, 3) NOT NULL ON CONFLICT REPLACE\n" +
            "                                DEFAULT (1),\n" +
            "    is_default  BOOLEAN         NOT NULL ON CONFLICT REPLACE\n" +
            "                                DEFAULT (0) \n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Таблица: invents\n" +
            "DROP TABLE IF EXISTS invents;\n" +
            "\n" +
            "CREATE TABLE invents (\n" +
            "    id         INTEGER         PRIMARY KEY AUTOINCREMENT\n" +
            "                               UNIQUE\n" +
            "                               NOT NULL,\n" +
            "    doc_id     INTEGER         REFERENCES journal (id) ON DELETE CASCADE\n" +
            "                                                       ON UPDATE CASCADE\n" +
            "                               NOT NULL,\n" +
            "    line_num   INTEGER         NOT NULL,\n" +
            "    stc_id     INTEGER         REFERENCES goods (id) ON DELETE CASCADE\n" +
            "                                                     ON UPDATE CASCADE\n" +
            "                               NOT NULL,\n" +
            "    unit_id    INTEGER         REFERENCES goods_units (id) ON DELETE CASCADE\n" +
            "                                                           ON UPDATE CASCADE\n" +
            "                               NOT NULL,\n" +
            "    qty        DECIMAL (12, 3) NOT NULL ON CONFLICT REPLACE\n" +
            "                               DEFAULT (0),\n" +
            "    is_current BOOLEAN         NOT NULL ON CONFLICT REPLACE\n" +
            "                               DEFAULT (0) \n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Таблица: journal\n" +
            "DROP TABLE IF EXISTS journal;\n" +
            "\n" +
            "CREATE TABLE journal (\n" +
            "    id       INTEGER    PRIMARY KEY AUTOINCREMENT\n" +
            "                        UNIQUE\n" +
            "                        NOT NULL,\n" +
            "    doc_type CHAR (1)   NOT NULL,\n" +
            "    doc_date DATETIME   NOT NULL\n" +
            "                        DEFAULT (datetime('now', 'localtime') ),\n" +
            "    doc_num  CHAR (10)  NOT NULL,\n" +
            "    comment  CHAR (100) NOT NULL\n" +
            "                        DEFAULT ('') \n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Таблица: options\n" +
            "DROP TABLE IF EXISTS options;\n" +
            "\n" +
            "CREATE TABLE options (\n" +
            "    id         INTEGER    PRIMARY KEY AUTOINCREMENT\n" +
            "                          NOT NULL\n" +
            "                          UNIQUE,\n" +
            "    key_name   CHAR (15)  NOT NULL,\n" +
            "    role       CHAR (15)  NOT NULL,\n" +
            "    sort_order INTEGER    NOT NULL ON CONFLICT REPLACE\n" +
            "                          DEFAULT (0),\n" +
            "    value      CHAR (128) NOT NULL ON CONFLICT REPLACE\n" +
            "                          DEFAULT (''),\n" +
            "    [desc]     CHAR (128) NOT NULL ON CONFLICT REPLACE\n" +
            "                          DEFAULT ('') \n" +
            ");\n" +
            "\n" +
            "INSERT INTO options (id, key_name, role, sort_order, value, \"desc\") VALUES (1, 'gkt_name', 'goods_index', 3, 'C', 'По наименованию');\n" +
            "INSERT INTO options (id, key_name, role, sort_order, value, \"desc\") VALUES (2, 'gkt_code', 'goods_index', 2, 'B', 'По коду');\n" +
            "INSERT INTO options (id, key_name, role, sort_order, value, \"desc\") VALUES (3, 'gkt_barcore', 'goods_index', 1, 'A', 'По штрих-коду');\n" +
            "INSERT INTO options (id, key_name, role, sort_order, value, \"desc\") VALUES (4, 'dt_invent', 'doc_type', 1, 'A', 'Инвентаризация');\n" +
            "\n" +
            "-- Индекс: idx_gindex_float\n" +
            "DROP INDEX IF EXISTS idx_gindex_float;\n" +
            "\n" +
            "CREATE UNIQUE INDEX idx_gindex_float ON goods_indexes (\n" +
            "    val_float ASC\n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Индекс: idx_gindex_gid\n" +
            "DROP INDEX IF EXISTS idx_gindex_gid;\n" +
            "\n" +
            "CREATE UNIQUE INDEX idx_gindex_gid ON goods_indexes (\n" +
            "    goods_id\n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Индекс: idx_gindex_id\n" +
            "DROP INDEX IF EXISTS idx_gindex_id;\n" +
            "\n" +
            "CREATE UNIQUE INDEX idx_gindex_id ON goods_indexes (\n" +
            "    id\n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Индекс: idx_gindex_int\n" +
            "DROP INDEX IF EXISTS idx_gindex_int;\n" +
            "\n" +
            "CREATE INDEX idx_gindex_int ON goods_indexes (\n" +
            "    val_int\n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Индекс: idx_gindex_str\n" +
            "DROP INDEX IF EXISTS idx_gindex_str;\n" +
            "\n" +
            "CREATE INDEX idx_gindex_str ON goods_indexes (\n" +
            "    val_str COLLATE RTRIM COLLATE NOCASE ASC\n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Индекс: idx_gindex_typeid\n" +
            "DROP INDEX IF EXISTS idx_gindex_typeid;\n" +
            "\n" +
            "CREATE UNIQUE INDEX idx_gindex_typeid ON goods_indexes (\n" +
            "    index_type COLLATE NOCASE ASC\n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Индекс: idx_goods_id\n" +
            "DROP INDEX IF EXISTS idx_goods_id;\n" +
            "\n" +
            "CREATE UNIQUE INDEX idx_goods_id ON Goods (\n" +
            "    id\n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Индекс: idx_goods_name\n" +
            "DROP INDEX IF EXISTS idx_goods_name;\n" +
            "\n" +
            "CREATE INDEX idx_goods_name ON Goods (\n" +
            "    Name\n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Индекс: idx_goods_outer_id\n" +
            "DROP INDEX IF EXISTS idx_goods_outer_id;\n" +
            "\n" +
            "CREATE INDEX idx_goods_outer_id ON Goods (\n" +
            "    outer_id\n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Индекс: idx_goods_units_id\n" +
            "DROP INDEX IF EXISTS idx_goods_units_id;\n" +
            "\n" +
            "CREATE UNIQUE INDEX idx_goods_units_id ON goods_units (\n" +
            "    id\n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Индекс: idx_goods_units_obj_id\n" +
            "DROP INDEX IF EXISTS idx_goods_units_obj_id;\n" +
            "\n" +
            "CREATE INDEX idx_goods_units_obj_id ON goods_units (\n" +
            "    obj_id\n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Индекс: idx_invent_doc_id\n" +
            "DROP INDEX IF EXISTS idx_invent_doc_id;\n" +
            "\n" +
            "CREATE UNIQUE INDEX idx_invent_doc_id ON invents (\n" +
            "    id\n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Индекс: idx_invent_is_current\n" +
            "DROP INDEX IF EXISTS idx_invent_is_current;\n" +
            "\n" +
            "CREATE INDEX idx_invent_is_current ON invents (\n" +
            "    is_current\n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Индекс: idx_journal_doc_date\n" +
            "DROP INDEX IF EXISTS idx_journal_doc_date;\n" +
            "\n" +
            "CREATE INDEX idx_journal_doc_date ON journal (\n" +
            "    doc_date\n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Индекс: idx_journal_doc_id\n" +
            "DROP INDEX IF EXISTS idx_journal_doc_id;\n" +
            "\n" +
            "CREATE UNIQUE INDEX idx_journal_doc_id ON journal (\n" +
            "    id\n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Индекс: idx_journal_doc_num\n" +
            "DROP INDEX IF EXISTS idx_journal_doc_num;\n" +
            "\n" +
            "CREATE INDEX idx_journal_doc_num ON journal (\n" +
            "    doc_num\n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Индекс: idx_journal_doc_type\n" +
            "DROP INDEX IF EXISTS idx_journal_doc_type;\n" +
            "\n" +
            "CREATE INDEX idx_journal_doc_type ON journal (\n" +
            "    doc_type\n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Индекс: idx_opt_key_adn_role\n" +
            "DROP INDEX IF EXISTS idx_opt_key_adn_role;\n" +
            "\n" +
            "CREATE UNIQUE INDEX idx_opt_key_adn_role ON options (\n" +
            "    key_name,\n" +
            "    role\n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Индекс: idx_opt_key_name\n" +
            "DROP INDEX IF EXISTS idx_opt_key_name;\n" +
            "\n" +
            "CREATE UNIQUE INDEX idx_opt_key_name ON options (\n" +
            "    key_name\n" +
            ");\n" +
            "\n" +
            "\n" +
            "-- Индекс: idx_opt_role\n" +
            "DROP INDEX IF EXISTS idx_opt_role;\n" +
            "\n" +
            "CREATE INDEX idx_opt_role ON options (\n" +
            "    role\n" +
            ");\n" +
            "\n" +
            "\n" +
            "COMMIT TRANSACTION;\n" +
            "PRAGMA foreign_keys = on;";

    public DBProvider(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_DB);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
