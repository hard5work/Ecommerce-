<?php
require'../init.php';

if(isset($_POST['userid'] , $_POST['productid'] , $_POST['price'])){
	$productid = $_POST['productid'];
	$userid = $_POST['userid'];
	$price = $_POST['price'];

	$query = "SELECT * from cart_list where userid = '$userid' AND productid = '$productid'";
	$result = mysqli_query($conn,$query);
	if(mysqli_num_rows($result)>0)
	{
		$json['already'] = "Item already added";
		echo json_encode($json);
	}

	else
	{
		$insrt = "INSERT INTO cart_list(userid,productid,qnty,amount,status) VALUES ('$userid' , '$productid','1','$price' ,'1')";
		$added = mysqli_query($conn,$insrt);
		if ($added) {
			$json['added'] = "Item added to Cart";
			echo json_encode($json);
		}
		else
		{
			$json['error'] = "Unable to add items to cart ".mysqli_error($conn);
			echo json_encode($json);
		}
	}
}

?>