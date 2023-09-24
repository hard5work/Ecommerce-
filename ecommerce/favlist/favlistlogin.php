<?php
require '../init.php';
//include 'detailofproduct';
if (isset($_POST['favorite'],$_POST['userid'], $_POST['productid'])) {
	$favorite = $_POST['favorite'];
	$userid = $_POST['userid'];
	$productid = $_POST['productid'];

	$query1 = "SELECT * FROM fav_list WHERE favorite = '$favorite' AND productid = '$productid' AND userid = '$userid'";
	$result1 = mysqli_query($conn,$query1);
	if(mysqli_num_rows($result1)>0){
		$json["exists"] = "already marked favorite";
		echo json_encode($json);
	}

	else{

	$query = "INSERT INTO fav_list(favorite,userid,productid) VALUES ('$favorite','$userid','$productid')";
	$result = mysqli_query($conn,$query);
	if($result){
		$json['success'] = "data entered successs";
		echo json_encode($json);
	}
	else
	{
		$json['error'] = "unable to insert data";
		echo json_encode($json);
	}
}
	mysqli_close($conn);

}



?>