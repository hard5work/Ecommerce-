<?php
require '../init.php';

if (isset($_POST['productid'])) {
	$productid = $_POST['productid'];

	$query = "SELECT product_image FROM product_images WHERE productid =  '$productid'";
	$result = mysqli_query($conn,$query);
	$info = array();
	while ($row = mysqli_fetch_array($result)) {
	$temp = array();
	$temp['image'] = $row['0'];
	array_push($info, $temp);
}
echo json_encode($info);
}



?>