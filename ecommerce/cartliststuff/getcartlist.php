<?php
require '../init.php';

$query = "SELECT * FROM cart_list";
$result = mysqli_query($conn,$query);
$info = array();
while ($row = mysqli_fetch_array($result)) {
	$temp = array();
	$temp['id'] = $row['0'];
	$temp['userid'] = $row['1'];
	$temp['productid'] = $row['2'];
	$temp['qnty'] = $row['3'];
	$temp['amount'] = $row['4'];
	array_push($info,$temp);
}
echo json_encode($info);

?>
