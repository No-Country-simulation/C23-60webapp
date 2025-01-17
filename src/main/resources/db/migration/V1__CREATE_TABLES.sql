CREATE TABLE compra
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    usuario_id   BIGINT                NULL,
    precio_total DOUBLE                NULL,
    fecha_compra date                  NULL,
    metodo_pago  VARCHAR(255)          NULL,
    status       SMALLINT              NULL,
    CONSTRAINT pk_compra PRIMARY KEY (id)
);

CREATE TABLE compra_paquete_viajes
(
    compra_id         BIGINT NOT NULL,
    paquete_viajes_id BIGINT NOT NULL
);

CREATE TABLE imagen
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    imagen           BLOB                  NULL,
    paquete_viaje_id BIGINT                NULL,
    CONSTRAINT pk_imagen PRIMARY KEY (id)
);

CREATE TABLE paquete_viaje
(
    id                   BIGINT AUTO_INCREMENT NOT NULL,
    titulo               VARCHAR(255)          NULL,
    descripcion          VARCHAR(255)          NULL,
    destino              VARCHAR(255)          NULL,
    fecha_inicio         date                  NULL,
    fecha_final          date                  NULL,
    paquetes_disponibles INT                   NULL,
    paquetes_totales     INT                   NULL,
    cantidad             INT                   NULL,
    precio_unitario      DOUBLE                NULL,
    precio_total         DOUBLE                NULL,
    CONSTRAINT pk_paqueteviaje PRIMARY KEY (id)
);

CREATE TABLE usuario
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    nombre          VARCHAR(255)          NULL,
    email           VARCHAR(255)          NULL,
    nombre_usuario  VARCHAR(255)          NULL,
    clave           VARCHAR(255)          NULL,
    numero_telefono INT                   NULL,
    fecha_registro  date                  NULL,
    CONSTRAINT pk_usuario PRIMARY KEY (id)
);

ALTER TABLE compra_paquete_viajes
    ADD CONSTRAINT uc_compra_paquete_viajes_paqueteviajes UNIQUE (paquete_viajes_id);

ALTER TABLE usuario
    ADD CONSTRAINT uc_usuario_email UNIQUE (email);

ALTER TABLE compra
    ADD CONSTRAINT FK_COMPRA_ON_USUARIO FOREIGN KEY (usuario_id) REFERENCES usuario (id);

ALTER TABLE imagen
    ADD CONSTRAINT FK_IMAGEN_ON_PAQUETE_VIAJE FOREIGN KEY (paquete_viaje_id) REFERENCES paquete_viaje (id);

ALTER TABLE compra_paquete_viajes
    ADD CONSTRAINT fk_compaqvia_on_compra FOREIGN KEY (compra_id) REFERENCES compra (id);

ALTER TABLE compra_paquete_viajes
    ADD CONSTRAINT fk_compaqvia_on_paquete_viaje FOREIGN KEY (paquete_viajes_id) REFERENCES paquete_viaje (id);