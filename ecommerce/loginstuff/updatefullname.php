<?php

require '../init.php';


if(isset($_POST['email'],$_POST['firstname'],$_POST['middlename'],$_POST['contact'] , $_POST['address'], $_POST['lastname'] , $_POST['username'])){
	$email = $_POST['email'];
	$username = $_POST['username'];
	$firstname = $_POST['firstname'];
	$lastname = $_POST['lastname'];
	$middlename = $_POST['middlename'];
	$contact = $_POST['contact'];
	$address = $_POST['address'];

	$query1 = "SELECT * FROM user_info WHERE email = '$email' AND username = '$username'";
				$result1 = mysqli_query($conn,$query1);
				if(mysqli_num_rows($result1)>0)
				{
					$updatequery = "UPDATE user_info SET firstname = '$firstname', middlename = '$middlename' , lastname = '$lastname', contact_no = '$contact', address = '$address' WHERE email = '$email'  AND username = '$username'";

				$result = mysqli_query($conn,$updatequery);
				if($result)
				{
			
				 		$json['success'] = "Info updated";
				 		echo json_encode($json);
				 	 	}
				 	else
				 	{
				 		$json['unsuccess'] ="Unable to update Info";
				 		echo json_encode($json);
					 	}



				}
				else{
					$json["wrongggggg"] = "wronggg";
					echo json_encode($json);
				}



}


?>