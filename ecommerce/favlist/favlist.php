<?php
require '../init.php';
if(isset($_POST['userid'])){
	$userid = $_POST['userid'];
	

$query = "SELECT id,name,image,price,long_desc FROM full_image_desc WHERE EXISTS (SELECT productid FROM fav_list WHERE productid = full_image_desc.id and userid = '$userid')";
	
$result = mysqli_query($conn,$query);
$info = array();
while ($row = mysqli_fetch_array($result)) {
	$temp = array();
	$temp['id'] = $row['0'];
	$temp['name'] = $row['1'];
	$temp['image'] = $row['2'];
	$temp['price'] = $row['3'];
	$temp['longdesc'] = $row['4'];
//	$temp['productid'] = $row['5'];
	array_push($info,$temp);
}
echo json_encode($info);

}



?>