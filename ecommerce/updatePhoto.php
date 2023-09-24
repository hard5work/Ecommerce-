<?php

require 'init.php';

if(isset($_POST['email'],$_POST['image'] , $_POST['username'])){
	$email = $_POST['email'];
	$username = $_POST['username'];
	$image = $_POST['image'];
	$upload_path = "images/$username.jpg";

	$query1 = "SELECT image FROM user_info WHERE email = '$email' AND username = '$username'";
				$result1 = mysqli_query($conn,$query1);
				if(mysqli_num_rows($result1)>0)
				{
					$updatequery = "UPDATE user_info SET image = '$upload_path' WHERE email = '$email' AND username = '$username'";

				$result = mysqli_query($conn,$updatequery);
				if($result)
				{
					file_put_contents($upload_path, base64_decode($image));
			
				 		$json['success'] = "IMAGE updated";
				 		echo json_encode($json);
				 	 	}
				 	else
				 	{
				 		$json['unsuccess'] ="Unable to update Image";
				 		echo json_encode($json);
					 	}



				}


}







?>