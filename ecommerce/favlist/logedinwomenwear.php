<?php
require '../init.php';
if (isset($_POST['userid'])) {
	$userid = $_POST['userid'];


$query = "SELECT f.ID,fl.favorite FROM full_image_desc f INNER JOIN fav_list fl ON f.id = fl.productid AND f.category = 'F'AND fl.userid = '$userid' ORDER BY f.id ASC ";
$result = mysqli_query($conn,$query);

$info = array();
while ($row = mysqli_fetch_array($result)) {
	$temp = array();
	$temp['productid'] = $row['0'];
	$temp['favorite'] = $row['1'];
	array_push($info,$temp);
}
echo json_encode($info); 


}


?>