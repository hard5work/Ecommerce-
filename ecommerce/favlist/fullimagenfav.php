<?php

require '../init.php';

	if (isset($_POST['productid'] ,$_POST['userid'])) {
		$productid = $_POST['productid'];
		$userid = $_POST['userid'];
	

$query1 = "SELECT * FROM fav_list WHERE favorite = '1' AND productid = '$productid' AND userid = '$userid'";
	$result1 = mysqli_query($conn,$query1);
	if (mysqli_num_rows($result1)>0) {
		$json['favorite'] = "favorite item";	
			echo json_encode($json);
	}
	else
	{
		$json['no favorite'] = "non favorite item";
		echo json_encode($json);
	}

	
 mysqli_close($conn);

}


?>