<?php
require '../init.php';
if (isset($_POST['userid'])) {
	$userid = $_POST['userid'];

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

	
	$productid = $temp['id'];
	$query1 = "SELECT favorite FROM fav_list WHERE userid = '$userid' AND productid = '$productid'";
	$result1 = mysqli_query($conn, $query1);
	$fetch = mysqli_fetch_ASSOC($result1);
	$favorite =$fetch['favorite'];
	$temp['favorite'] = $favorite;

	$query3 = "SELECT status FROM cart_list WHERE userid = '$userid' AND productid = '$productid'";
	$result3 = mysqli_query($conn, $query3);

	$fetch3 = mysqli_fetch_ASSOC($result3);
	$cart =$fetch3['status'];
	$temp['cart'] = $cart;
	array_push($info,$temp);
}
echo json_encode($info); 



}


?>