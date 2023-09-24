<?php

require '../init.php';

if (isset($_POST['itemname'],$_POST['itemdesc'], $_POST['price'], $_POST['qnty'],$_POST['favourites'])){
	$itemname = $_POST['itemname'];
	$itemdesc = $_POST['itemdesc'];
	$price 	  = $_POST['price'];
	$qnty	  = $_POST['favourites'];
	$amout	  = $price * $qnty;
	
}

?>