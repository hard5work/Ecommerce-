<?php
$sn = "localhost"; //server name
$un = "root"; //username
$pw = "";//pasword
$dbn = "ecommerce"; //database namme

$conn = mysqli_connect($sn,$un,$pw,$dbn);
if($conn->connect_error)
{
	die("enable to connect".$conn->connect_error);
}
if (isset($_POST['username'],$_POST['password'],$_POST['email'],$_POST['firstname'],$_POST['middlename'],$_POST['lastname'] , $_POST['address'] , $_POST['number'] , $_POST['test'])){

			$username = $_POST['username'];
			$password=$_POST['password'];
			$md5pass = md5($password);
			$email =$_POST['email'];
			$firstname=$_POST['firstname'];
			$middlename=$_POST['middlename'];
			$lastname = $_POST['lastname'];
			$address = $_POST['address'];
			$number = $_POST['number'];
			$test = $_POST['test'];
			
			//$target_path = "images/";
 	//		$target_file = $target_path.basename($_FILES["image"]["name"]);

			$query1 = "SELECT * FROM user_info WHERE email = '$email' OR username = '$username' OR contact_no = '$number'";

				$result1 = mysqli_query($conn,$query1);
				if(mysqli_num_rows($result1)>0)
				{
					$json['exists'] ="email or username or contact no already exists";
				echo json_encode($json);
				mysqli_close($conn);


				}
				else
				{

					$upload_path = "images/$firstname.jpg";
				$query = "INSERT INTO user_info(username,password,email, firstname, middlename, lastname,address,image,contact_no) VALUES ('$username','$md5pass','$email' , '$firstname' , '$middlename','$lastname','$address' , '$upload_path' , '$number')";

				

				$result = mysqli_query($conn,$query);
				if($result)
				{
					file_put_contents($upload_path, base64_decode($test));
			
				 		$json['success'] = "IMAGE uploaded";
				 		echo json_encode($json);
				 	 	}
				 	else
				 	{
				 		$json['unsuccess'] ="IMAGE not uploaded";
				 		echo json_encode($json);
					 	}


				}
				

				}


?>