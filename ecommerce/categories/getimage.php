<?php
require '../init.php';

$query = "SELECT name,url FROM image_info ORDER BY id DESC";
$result = mysqli_query($conn,$query);

$info = array();
while ($row = mysqli_fetch_array($result)) {
	$temp = array();
	$temp['name'] = $row['0'];
	$temp['url'] = $row['1'];
	array_push($info,$temp);
}
echo json_encode($info);
?>