<?php
require '../init.php';
if(isset($_POST['userid'])){
	$userid = $_POST['userid'];

	$query = "SELECT f.ID,f.name,f.image,f.price,f.long_desc,c.qnty,c.amount  FROM full_image_desc f INNER JOIN cart_list c ON f.id = c.productid AND c.userid = '$userid' ORDER BY f.id ASC";

	$result = mysqli_query($conn, $query);

	$info = array();
while ($row = mysqli_fetch_array($result)) {
	$temp = array();
	$temp['id'] = $row['0'];
	$temp['name'] = $row['1'];
	$temp['image'] = $row['2'];
	$temp['price'] = $row['3'];
	$temp['longdesc'] = $row['4'];
	$temp['qnty'] = $row['5'];
	$temp['amount'] = $row['6'];
	array_push($info,$temp);

}

echo json_encode($info);
}
?>