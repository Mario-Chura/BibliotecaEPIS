create database db_biblioteca;

use db_biblioteca;

create table autores(
	id_autor varchar(300) primary key,
	nom_autor varchar(300) not null
);

create table ubicaciones(
	id_ubicacion varchar(300) primary key,
	nom_ubicacion varchar(500) not null
);

create table libros(
	cod varchar(15) primary key,
	portada varchar(500),
	nom_libro varchar(500) not null,
	autor text not null,
	descripcion text,
	ubicacion varchar(500) not null,
	anio_publicacion varchar(4),
	edicion varchar(100),
	existencias Integer not null,
	categoria text not null
);

create table usuarios(
	id_usuario varchar(5) primary key,
	nom_usuario varchar(500) not null,
	estado_usuario varchar(100),
	contrasena varchar(10) not null
);

create table adminusuarios(
	id_usuario varchar(10) primary key,
	nom_usuario varchar(500) not null,
	contrasena varchar(10) not null
);

create table categorias(
	id_categoria varchar(100) primary key,
	nom_categoria text not null
);

create table prestamos(
	cod varchar(15) not null,
	id_usuario varchar(5) not null,
	fecha_prestamo date not null,
	fecha_devolucion date
);

insert into categorias values("c0001", "Ciencias Formales");
insert into categorias values("c0002", "Informatica Basica");
insert into categorias values("c0003", "Internet");
insert into categorias values("c0004", "Topicos de informatica");
insert into categorias values("c0005", "Fundamentos de programacion");
insert into categorias values("c0006", "Algoritmos");
insert into categorias values("c0007", "Estructura de datos");
insert into categorias values("c0008", "Compiladores");
insert into categorias values("c0009", "Computacion Grafica");

insert into usuarios values("u0001", "Gustavo Aguilar", null, "gustavoa");
insert into usuarios values("u0002", "Luis Aliaga", "Deudor", "luisa");
insert into usuarios values("u0003", "Edgar Almanza", "Deudor", "edgara");
insert into usuarios values("u0004", "Alexis Ancco", null, "alexisa");
insert into usuarios values("u0005", "Fernando Araoz", null, "fernandoa");
insert into usuarios values("u0006", "Erick Carpio", null, "erickc");
insert into usuarios values("u0007", "Piero Cruz", null, "pieroc");
insert into usuarios values("u0008", "Diego Florez", null, "diegof");

insert into adminusuarios values("adm0001", "Mario Chura", "marioc");
insert into adminusuarios values("adm0002", "Angie Llaique", "angiel");
insert into adminusuarios values("adm0003", "Claudia Contreras", "claudiac");

insert into autores values("a0001", "Serway");
insert into autores values("a0002", "Seymour Lipschiuz");
insert into autores values("a0003", "Beekman");
insert into autores values("a0004", "Larry Long-Nancy Long");
insert into autores values("a0005", "José Luis Raya ");
insert into autores values("a0006", "Matthew A  Russell");

insert into ubicaciones values("ubc0001", "Biblioteca Ingenierias");
insert into ubicaciones values("ubc0002", "Biblioteca Biomedicas");
insert into ubicaciones values("ubc0003", "Biblioteca Sociales");
insert into ubicaciones values("ubc0004", "Biblioteca Filosofia");
insert into ubicaciones values("ubc0005", "Centro de documentacion EPIS");
insert into ubicaciones values("ubc0006", "Biblioteca electronica");
insert into ubicaciones values("ubc0007", "Biblioteca Ciencias de la computacion");
insert into ubicaciones values("ubc0008", "Biblioteca Telecomunicaciones");

insert into libros values("9786070768828","9786070768828.png","Atrevete a ser quien eres","Walter Riso", "","Planeta","2019", "1ra edicion", 10, "Superacion Personal");


SELECT 
libros.cod, 
libros.nom_libro, 
libros.autor, 
libros.ubicacion, 
libros.anio_publicacion, 
libros.edicion,
usuarios.id_usuario,
usuarios.nom_usuario,
usuarios.estado_usuario, 
prestamos.fecha_prestamo,
prestamos.fecha_devolucion 
from libros, usuarios, prestamos 
where 
libros.cod = prestamos.cod and
usuarios.id_usuario = prestamos.id_usuario;