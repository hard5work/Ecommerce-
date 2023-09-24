<?php
require '../init.php';
if(isset($_POST['userid'])){
	$userid = $_POST['userid'];

	$query = "SELECT favorite,productid FROM fav_list WHERE userid = '$userid'";
	$result = mysqli_query($conn, $query);
	while ($fetch = mysqli_fetch_ASSOC($result)){
	if($fetch){
		$favorite =$fetch['favorite'];
		$productid = $fetch['productid'];
		$json['favorite'] = "$favorite";
		$json['productid'] = "$productid";
		echo json_encode($json);
	}
}

/*
	$info = array();
while ($row = mysqli_fetch_array($result)) {
	$temp = array();
	$temp['favorite'] = $row['0'];
	$temp['productid'] = $row['1'];
	array_push($info,$temp);

}

echo json_encode($info);*/
}
?>