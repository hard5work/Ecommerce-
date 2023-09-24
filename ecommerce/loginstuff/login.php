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
if (isset($_POST['email'],$_POST['password'] )){

$email =$_POST['email'];
$password=$_POST['password'];
$md5pass = md5($password);

$query1 = "SELECT * FROM user_info WHERE  password = '$md5pass' AND email = '$email'";
$result = mysqli_query($conn,$query1);

$admincheck = "SELECT user_type FROM user_info WHERE password = '$md5pass' AND email = '$email'";
$result1 = mysqli_query($conn,$admincheck);
//if(mysqli_num_rows($result1)>0)
if (mysqli_num_rows($result)>0) 
{

$check = mysqli_fetch_array($result1);
	$json['success'] = "succuessfully logged in";	
	$json['user_type'] = $check['user_type'];





}
else
{
	$json['error'] = "error loggin in";



}


echo json_encode($json);

mysqli_close($conn);
	
}


?>