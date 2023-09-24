<?php 
require '../init.php';

if (isset($_POST['productid'],$_POST['favorite'] ,$_POST['userid'])) {
	$productid = $_POST['productid'];
	$favorite = $_POST['favorite'];
	$userid = $_POST['userid'];

	$query1 = "SELECT favorite FROM fav_list WHERE favorite = '$favorite' and productid = '$productid' and userid = '$userid'";
	$check1 = mysqli_query($conn,$query1);
	if (mysqli_num_rows($check1)>0) {
		$json['repeat'] = "already clicked";
		echo json_encode($json);
	}
	else{ 

	$query = "UPDATE fav_list SET favorite = '$favorite' WHERE productid = '$productid' and userid = '$userid'";
	$check = mysqli_query($conn ,$query);
	if ($check) {
		$json['updated'] = "data updated";
		echo json_encode($json);
	}
	else {
		$json['error'] = "no data found";
		echo json_encode($json);
	}
}
	mysqli_close($conn);



}


?>