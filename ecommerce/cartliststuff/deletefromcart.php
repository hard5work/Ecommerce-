<?php
require '../init.php';

if (isset($_POST['userid'],$_POST['productid'])) {
	$userid = $_POST['userid'];
	$productid = $_POST['productid'];

	$check = "SELECT * FROM cart_list WHERE userid = '$userid' AND productid = '$productid'";
	$reslt = mysqli_query($conn,$check);
	if (mysqli_num_rows($reslt)>0) {
		
		$dels = "DELETE FROM cart_list WHERE userid = '$userid' AND productid = '$productid'";
		$succ = mysqli_query($conn ,$dels);
		if ($succ) {
			$json['success']  = "Removed from cart list";
			echo json_encode($json);
		}
		else
		{
			$json['error'] = "Error removing data.....";
			echo json_encode($json);
		}
	}
	else
	{
		$json['nodata'] = "No data found";
		echo json_encode($json);
	}

	mysqli_close($conn);
}


?>