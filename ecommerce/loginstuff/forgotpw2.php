<?php
require '../init.php';
if (isset($_POST['password'] , $_POST['repassword'] , $_POST['email'])){
	$password = $_POST['password'];
	$email = $_POST['email'];
	$repassword = $_POST['repassword'];

	if ($password == $repassword) {
		$md5pas = md5($repassword);
		$updatepass = "UPDATE user_info SET password = '$md5pas' WHERE email = '$email' OR username = '$email'";
		$checkrslt = mysqli_query($conn,$updatepass);
		if ($checkrslt) {
			
			$json['updated'] = "Password Updated";
			echo json_encode($json);

		}
		else
		{
			$json['pwerror'] =  "Error updating password";
			echo json_encode($json);
		}
		
	}
	else {
		$json['error'] = "Password Didnot match! Try again";
		echo json_encode($json);
	}



}
?>