<?php
require '../init.php';

$query = "SELECT ID,name,image,price,long_desc FROM full_image_desc WHERE category = 'M' AND tags LIKE '%watch%' ORDER BY id ASC ";
$result = mysqli_query($conn,$query);


$info = array();
while ($row = mysqli_fetch_array($result)) {
	$temp = array();
	$temp['id'] = $row['0'];
	$temp['name'] = $row['1'];
	$temp['image'] = $row['2'];
	$temp['price'] = $row['3'];
	$temp['longdesc'] = $row['4'];
	array_push($info,$temp);
}
echo json_encode($info); 






?>