<?php
require "../init.php";

$query = "SELECT id,firstname,middlename,lastname,email,image,contact_no FROM user_info ";
$result = mysqli_query($conn , $query);
$info = array();
while($row = mysqli_fetch_array($result)){
	$temp = array();
	$temp['id'] = $row['0'];
	$temp['firstname'] = $row['1'];
	$temp['middlename'] = $row['2'];
	$temp['lastname'] = $row['3'];
	$temp['email'] = $row['4'];
	$temp['image'] = $row['5'];
	$temp['contact'] = $row['6'];
	array_push($info, $temp);
}
echo json_encode($info);




?>