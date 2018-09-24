CREATE TABLE marca(
    id_marca INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    sede VARCHAR(100) NOT NULL,
    pais VARCHAR(100) NOT NULL,
    activa BOOLEAN NOT NULL DEFAULT 0
);

CREATE TABLE cerveza(
    id_cerveza INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    graduacion FLOAT NOT NULL,
    precio FLOAT NOT NULL,
    activa BOOLEAN NOT NULL DEFAULT 0,
    marca INT NOT NULL,
    FOREIGN KEY(marca) REFERENCES marca(id_marca)
);

CREATE TABLE empleado(
    id_empleado INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    DNI VARCHAR(9) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT 0
);

CREATE TABLE empleado_tparcial(
    id_empleado INT NOT NULL,
    turno ENUM('m', 't') NOT NULL,
    PRIMARY KEY (id_empleado),
    FOREIGN KEY (id_empleado) REFERENCES empleado (id_empleado)
);

CREATE TABLE empleado_tcompleto(
    id_empleado INT NOT NULL,
    horas_extra INT,
    PRIMARY KEY (id_empleado),
    FOREIGN KEY (id_empleado) REFERENCES empleado (id_empleado)
);

CREATE TABLE factura(
    id_factura INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    precio_total FLOAT NOT NULL DEFAULT 0,
    empleado INT NOT NULL,
    abierta BOOLEAN NOT NULL DEFAULT 1,
    FOREIGN KEY(empleado) REFERENCES empleado(id_empleado)
);

CREATE TABLE asociada(
    factura INT NOT NULL,
    cerveza INT NOT NULL,
    cantidad INT NOT NULL,
    precio FLOAT NOT NULL,
    PRIMARY KEY (factura, cerveza),
    FOREIGN KEY(factura) REFERENCES factura(id_factura) ON DELETE CASCADE,
    FOREIGN KEY(cerveza) REFERENCES cerveza(id_cerveza) ON DELETE CASCADE
);











