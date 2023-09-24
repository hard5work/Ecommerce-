<?php
require '../init.php';

if (isset($_POST['favorite'], $_POST['productid'] , $_POST['userid'])) {
	$favorite = $_POST['favorite'];
	$userid = $_POST['userid'];
	$productid = $_POST['productid'];


	$query1 = "SELECT * FROM fav_list WHERE favorite = '$favorite' AND productid = '$productid' AND userid = '$userid'";
	$result1 = mysqli_query($conn,$query1);
	if(mysqli_num_rows($result1)>0){
	

	$query = "DELETE FROM fav_list WHERE favorite = '$favorite' AND productid = '$productid' AND userid = '$userid' ";
	$result = mysqli_query($conn,$query);
	if($result){
		$json['success'] = "removed from fav";
		echo json_encode($json);
	}
	else
	{
		$json['error'] = "unable to insert data";
		echo json_encode($json);
	}
}
else
{
	$json['nodata'] = "data not found";
	echo json_encode($json);
}


	mysqli_close($conn);

}


?>