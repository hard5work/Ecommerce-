<?php
require '../init.php';
if (isset($_POST['userid'])) {
	$userid = $_POST['userid'];


$query = "SELECT f.ID,f.name,f.image,f.price,f.long_desc,fl.favorite FROM full_image_desc f INNER JOIN fav_list fl ON f.id = fl.productid AND f.category = 'M'AND fl.userid = '$userid' ORDER BY f.id ASC ";
$result = mysqli_query($conn,$query);

$info = array();
while ($row = mysqli_fetch_array($result)) {
	$temp = array();
	$temp['productid'] = $row['0'];
	$temp['name'] = $row['1'];
	$temp['image'] = $row['2'];
	$temp['price'] = $row['3'];
	$temp['longdesc'] = $row['4'];
	$temp['favorite'] = $row['5'];
	array_push($info,$temp);
}
echo json_encode($info); 


}


?>