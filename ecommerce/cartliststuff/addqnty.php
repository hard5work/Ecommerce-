<?php
require '../init.php';

if (isset($_POST['qnty'],$_POST['userid'], $_POST['price'], $_POST['productid'])) {
	$qnty = $_POST['qnty'];
	$userid = $_POST['userid'];
	$price = $_POST['price'];
	$productid = $_POST['productid'];
	$amount = $price * $qnty;

	$query = "SELECT * FROM cart_list WHERE userid = '$userid' AND productid = '$productid'";
	$result = mysqli_query($conn ,$query);
	if (mysqli_num_rows($result)>0) {
		
		$updt = "UPDATE cart_list SET qnty = '$qnty',amount = '$amount' WHERE userid = '$userid' AND productid = '$productid'";
		$updted = mysqli_query($conn , $updt);
		if ($updted) {
			$json['updated'] = "Data updated ";
			echo json_encode($json);

			
			
		}
		else
		{
			$json['error'] = "unable to update";
			echo json_encode($json);
		}

	}
	else
	{
		$json['nodatafound'] = "data was not found";
		echo json_encode($json);
	}
}
else
{
	$json['dataerror'] = "data not accpted";
	echo json_encode($json);
}


?>