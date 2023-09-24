<?php
require '../init.php';
if (isset($_POST['email'])){
	$email = $_POST['email'];

	$checkquery = "SELECT * FROM user_info WHERE email = '$email' OR username = '$email'";
	$result = mysqli_query($conn,$checkquery);

if (mysqli_num_rows($result)>0) {
	$json['matched'] = "Matched";
	//echo json_encode($json);
}
else
{
	$json['error'] = "Error";
	//echo json_encode($json);
}
echo json_encode($json);
}
?>