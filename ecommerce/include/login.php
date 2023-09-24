<?php
$sn = "localhost";
$un="root";//username
$pw="";//password
$dbname="ecommerce";//database name
$conn=new mysqli($sn,$un,$pw,$dbname);
if($conn-> connect_error)
{
	die ("Enable to connect".$conn->connect_error);
}
if (isset($_POST['email'],($_POST['password']))){

	$email=$_POST['email'];
	$password=$_POST['password'];

	$query= "SELECT * FROM user_info where email = '$email' AND password ='$password'";

$intiliaze = mysqli_query($conn, $query);
if(mysqli_num_rows($intiliaze) > 0)
{
		$json['success'] = "Connect successful";
		mysqli_close($conn);
}
else
{
	$json['error']= "Invalid username and password";
	mysqli_close($conn);
}
echo json_encode($json);
}


?>