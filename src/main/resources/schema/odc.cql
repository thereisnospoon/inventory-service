CREATE KEYSPACE odc WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

USE odc;

CREATE TABLE inventory (
    sku text,
    storeId text,
    value int,
    threshold int,
    PRIMARY KEY (sku, storeId));