<?php

$db_host = "localhost";
$db_user = "root";
$db_pass = "";
$db_name = "db_biblioteca";

// VARIABLE QUE ALMACENA LA CONEXION A LA DB
$mysqli = new mysqli(
    $db_host, 
    $db_user, 
    $db_pass, 
    $db_name
);

if($mysqli->connect_errno){
    die("Fallo la conexion");
} else {
    //"Conexion exitosa";
}