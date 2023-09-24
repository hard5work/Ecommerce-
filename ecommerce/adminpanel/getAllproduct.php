<?php
require '../init.php';

$query = "SELECT ID,name,image,price FROM full_image_desc ORDER BY id DESC ";
$result = mysqli_query($conn,$query);


$info = array();
while ($row = mysqli_fetch_array($result)) {
	
$temp = array();
	$temp['id'] = $row['0'];
	$temp['name'] = $row['1'];
	$temp['image'] = $row['2'];
	$temp['price'] = $row['3'];

	$productid = $temp['id'];



	$query1 = "SELECT color FROM product_color WHERE productid =  '$productid'";
	$result1 = mysqli_query($conn,$query1);
	$info2 = array();
	while($row = mysqli_fetch_array($result1)){
	
	$temp2['color'] = $row['0'];
	array_push($info2, $temp2);
}

$temp['color'] = $info2;


$query2 = "SELECT size FROM product_size WHERE productid =  '$productid'";
	$result2 = mysqli_query($conn,$query2);
	$info3 = array();
	while($row = mysqli_fetch_array($result2)){
	$temp3['size'] = $row['0'];
	array_push($info3, $temp3);
}

$temp['size'] = $info3;





	array_push($info, $temp);

}
echo json_encode($info); 






?>