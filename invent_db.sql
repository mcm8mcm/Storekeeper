--
-- Файл сгенерирован с помощью SQLiteStudio v3.1.1 в Вт фев 6 23:21:02 2018
--
-- Использованная кодировка текста: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Таблица: goods
DROP TABLE IF EXISTS goods;

CREATE TABLE goods (
    id        INTEGER    PRIMARY KEY AUTOINCREMENT
                         UNIQUE
                         NOT NULL,
    outer_id  CHAR (36)  UNIQUE
                         NOT NULL,
    name      CHAR (150) NOT NULL ON CONFLICT REPLACE
                         DEFAULT ('Не указано'),
    full_name CHAR (150) NOT NULL ON CONFLICT REPLACE
                         DEFAULT ('Не указано') 
);


-- Таблица: goods_indexes
DROP TABLE IF EXISTS goods_indexes;

CREATE TABLE goods_indexes (
    id         INTEGER         UNIQUE
                               NOT NULL
                               PRIMARY KEY,
    goods_id   CHAR (36)       REFERENCES goods (outer_id) ON DELETE CASCADE
                                                           ON UPDATE CASCADE
                               NOT NULL,
    index_type CHAR (1)        NOT NULL,
    val_int    BIGINT,
    val_str    CHAR (150),
    val_float  NUMERIC (12, 3) 
);


-- Таблица: goods_units
DROP TABLE IF EXISTS goods_units;

CREATE TABLE goods_units (
    id          INTEGER         PRIMARY KEY AUTOINCREMENT
                                UNIQUE
                                NOT NULL,
    obj_id      INTEGER         NOT NULL
                                REFERENCES Goods (id),
    outer_id    STRING (36)     NOT NULL
                                UNIQUE,
    unit_name   CHAR (15)       NOT NULL ON CONFLICT REPLACE
                                DEFAULT ('шт.'),
    weight      DECIMAL (12, 3) NOT NULL
                                DEFAULT (0),
    coefficient DECIMAL (12, 3) NOT NULL ON CONFLICT REPLACE
                                DEFAULT (1),
    is_default  BOOLEAN         NOT NULL ON CONFLICT REPLACE
                                DEFAULT (0) 
);


-- Таблица: invents
DROP TABLE IF EXISTS invents;

CREATE TABLE invents (
    id         INTEGER         PRIMARY KEY AUTOINCREMENT
                               UNIQUE
                               NOT NULL,
    doc_id     INTEGER         REFERENCES journal (id) ON DELETE CASCADE
                                                       ON UPDATE CASCADE
                               NOT NULL,
    line_num   INTEGER         NOT NULL,
    stc_id     INTEGER         REFERENCES goods (id) ON DELETE CASCADE
                                                     ON UPDATE CASCADE
                               NOT NULL,
    unit_id    INTEGER         REFERENCES goods_units (id) ON DELETE CASCADE
                                                           ON UPDATE CASCADE
                               NOT NULL,
    qty        DECIMAL (12, 3) NOT NULL ON CONFLICT REPLACE
                               DEFAULT (0),
    is_current BOOLEAN         NOT NULL ON CONFLICT REPLACE
                               DEFAULT (0) 
);


-- Таблица: journal
DROP TABLE IF EXISTS journal;

CREATE TABLE journal (
    id       INTEGER    PRIMARY KEY AUTOINCREMENT
                        UNIQUE
                        NOT NULL,
    doc_type CHAR (1)   NOT NULL,
    doc_date DATETIME   NOT NULL
                        DEFAULT (datetime('now', 'localtime') ),
    doc_num  CHAR (10)  NOT NULL,
    comment  CHAR (100) NOT NULL
                        DEFAULT ('') 
);


-- Таблица: options
DROP TABLE IF EXISTS options;

CREATE TABLE options (
    id         INTEGER    PRIMARY KEY AUTOINCREMENT
                          NOT NULL
                          UNIQUE,
    key_name   CHAR (15)  NOT NULL,
    role       CHAR (15)  NOT NULL,
    sort_order INTEGER    NOT NULL ON CONFLICT REPLACE
                          DEFAULT (0),
    value      CHAR (128) NOT NULL ON CONFLICT REPLACE
                          DEFAULT (''),
    [desc]     CHAR (128) NOT NULL ON CONFLICT REPLACE
                          DEFAULT ('') 
);

INSERT INTO options (id, key_name, role, sort_order, value, "desc") VALUES (1, 'gkt_name', 'goods_index', 3, 'C', 'По наименованию');
INSERT INTO options (id, key_name, role, sort_order, value, "desc") VALUES (2, 'gkt_code', 'goods_index', 2, 'B', 'По коду');
INSERT INTO options (id, key_name, role, sort_order, value, "desc") VALUES (3, 'gkt_barcore', 'goods_index', 1, 'A', 'По штрих-коду');
INSERT INTO options (id, key_name, role, sort_order, value, "desc") VALUES (4, 'dt_invent', 'doc_type', 1, 'A', 'Инвентаризация');

-- Индекс: idx_gindex_float
DROP INDEX IF EXISTS idx_gindex_float;

CREATE UNIQUE INDEX idx_gindex_float ON goods_indexes (
    val_float ASC
);


-- Индекс: idx_gindex_gid
DROP INDEX IF EXISTS idx_gindex_gid;

CREATE UNIQUE INDEX idx_gindex_gid ON goods_indexes (
    goods_id
);


-- Индекс: idx_gindex_id
DROP INDEX IF EXISTS idx_gindex_id;

CREATE UNIQUE INDEX idx_gindex_id ON goods_indexes (
    id
);


-- Индекс: idx_gindex_int
DROP INDEX IF EXISTS idx_gindex_int;

CREATE INDEX idx_gindex_int ON goods_indexes (
    val_int
);


-- Индекс: idx_gindex_str
DROP INDEX IF EXISTS idx_gindex_str;

CREATE INDEX idx_gindex_str ON goods_indexes (
    val_str COLLATE RTRIM COLLATE NOCASE ASC
);


-- Индекс: idx_gindex_typeid
DROP INDEX IF EXISTS idx_gindex_typeid;

CREATE UNIQUE INDEX idx_gindex_typeid ON goods_indexes (
    index_type COLLATE NOCASE ASC
);


-- Индекс: idx_goods_id
DROP INDEX IF EXISTS idx_goods_id;

CREATE UNIQUE INDEX idx_goods_id ON Goods (
    id
);


-- Индекс: idx_goods_name
DROP INDEX IF EXISTS idx_goods_name;

CREATE INDEX idx_goods_name ON Goods (
    Name
);


-- Индекс: idx_goods_outer_id
DROP INDEX IF EXISTS idx_goods_outer_id;

CREATE INDEX idx_goods_outer_id ON Goods (
    outer_id
);


-- Индекс: idx_goods_units_id
DROP INDEX IF EXISTS idx_goods_units_id;

CREATE UNIQUE INDEX idx_goods_units_id ON goods_units (
    id
);


-- Индекс: idx_goods_units_obj_id
DROP INDEX IF EXISTS idx_goods_units_obj_id;

CREATE INDEX idx_goods_units_obj_id ON goods_units (
    obj_id
);


-- Индекс: idx_invent_doc_id
DROP INDEX IF EXISTS idx_invent_doc_id;

CREATE UNIQUE INDEX idx_invent_doc_id ON invents (
    id
);


-- Индекс: idx_invent_is_current
DROP INDEX IF EXISTS idx_invent_is_current;

CREATE INDEX idx_invent_is_current ON invents (
    is_current
);


-- Индекс: idx_journal_doc_date
DROP INDEX IF EXISTS idx_journal_doc_date;

CREATE INDEX idx_journal_doc_date ON journal (
    doc_date
);


-- Индекс: idx_journal_doc_id
DROP INDEX IF EXISTS idx_journal_doc_id;

CREATE UNIQUE INDEX idx_journal_doc_id ON journal (
    id
);


-- Индекс: idx_journal_doc_num
DROP INDEX IF EXISTS idx_journal_doc_num;

CREATE INDEX idx_journal_doc_num ON journal (
    doc_num
);


-- Индекс: idx_journal_doc_type
DROP INDEX IF EXISTS idx_journal_doc_type;

CREATE INDEX idx_journal_doc_type ON journal (
    doc_type
);


-- Индекс: idx_opt_key_adn_role
DROP INDEX IF EXISTS idx_opt_key_adn_role;

CREATE UNIQUE INDEX idx_opt_key_adn_role ON options (
    key_name,
    role
);


-- Индекс: idx_opt_key_name
DROP INDEX IF EXISTS idx_opt_key_name;

CREATE UNIQUE INDEX idx_opt_key_name ON options (
    key_name
);


-- Индекс: idx_opt_role
DROP INDEX IF EXISTS idx_opt_role;

CREATE INDEX idx_opt_role ON options (
    role
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
