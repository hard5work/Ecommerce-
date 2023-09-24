<?php
$sn = "localhost"; //server name
$un = "root"; //username
$pw = "";//pasword
$dbn = "ecommerce"; //database namme

$conn = mysqli_connect($sn,$un,$pw,$dbn);
if($conn->connect_error)
{
	die("enable to connect".$conn->connect_error);
}

?>