<?php 

require 'init.php';

if (isset($_POST['productid'],$_POST['size'])) {
	$productid = $_POST['productid'];
	$size = $_POST['size'];

	$query = "INSERT INTO product_size(productid,size) VALUES ('$productid','$size')";
	$result = mysqli_query($conn,$query);
	if ($result) {
		$json['success'] = "Sized Inserted";
		echo json_encode($json);
	}
	else{
		$json['error'] = "Sized not inserted";
		echo json_encode($json);
	}

}
else{
	$json['null'] = "no key";
	echo json_encode($json);
}


?>