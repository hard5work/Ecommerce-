<?php
 include '../init.php';

// $query = "SELECT full_image_desc.price ,men_wear.price FROM full_image_desc inner JOIN men_wear ON 'full_image_desc.name' = 'men_wear.name' ";
//$query = "SELECT price,name  FROM full_image_desc UNION SELECT price,name FROM men_wear ";



 
 $result = mysqli_query($conn,$query);
	$info = array();
while ($row = mysqli_fetch_array($result)) {
	$temp = array();
	$temp['price'] = $row['0'];
	$temp['name'] = $row['1'];

	array_push($info,$temp);
}
echo json_encode($info);




?>