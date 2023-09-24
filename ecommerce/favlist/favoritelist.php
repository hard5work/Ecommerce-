<?php
require '../init.php';
if(isset($_POST['userid'],$_POST['productid'])){
	$userid = $_POST['userid'];
	$productid = $_POST['productid'];

	$query = "SELECT favorite FROM fav_list WHERE userid = '$userid' AND productid = '$productid'";
	$result = mysqli_query($conn, $query);

	while ($fetch = mysqli_fetch_ASSOC($result)){
	if($fetch){
		$favorite =$fetch['favorite'];
		$json['favorite'] = "$favorite";
		echo json_encode($json);
	}
}


}
?>