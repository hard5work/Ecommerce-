<?php
require '../init.php';

if (ISSET($_POST['id'])) {

	$id = $_POST['id'];

$query = "SELECT ID,name FROM full_image_desc where id = '$id'";

$result = mysqli_query($conn,$query);
if (mysqli_num_rows($result)>0){

	$json['success'] = "data received";
	echo json_encode($json);


$info = array();
while ($row = mysqli_fetch_array($result)) {
	$temp = array();
	$temp['id'] = $row['0'];
	$temp['name'] = $row['1'];
	//$temp['image'] = $row['2'];
	//$temp['price'] = $row['3'];
	//$temp['longdesc'] = $row['4'];
	array_push($info,$temp);
}

echo json_encode($info);
}
else{
	$json['nomatch'] = "no match found";

}

}
?>