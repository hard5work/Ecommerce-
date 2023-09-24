<?php 

require 'init.php';

if (isset($_POST['productid'],$_POST['color'])) {
	$productid = $_POST['productid'];
	$color = $_POST['color'];

	$query = "INSERT INTO product_color(productid,color) VALUES ('$productid','$color')";
	$result = mysqli_query($conn,$query);
	if ($result) {
		$json['success'] = "Color Inserted";
		echo json_encode($json);
	}
	else{
		$json['error'] = "color not inserted";
		echo json_encode($json);
	}

}
else{
	$json['null'] = "no key";
	echo json_encode($json);
}


?>