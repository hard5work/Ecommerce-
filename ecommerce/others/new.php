<?php
$sn="localhost";//severname
$un="root";//username
$pw="";//password
$dbname="ecommerce";//database name
$conn=new mysqli($sn,$un,$pw,$dbname);

if($conn-> connect_error)
{
	die ("Enable to connect".$conn->connect_error);
}
if (isset($_POST['username'], $_POST['password'],$_POST['phone'],$_POST['email'])){

$username = $_POST['username'];
$password = $_POST['password'];
$phone =$_POST['phone'];
$email=$_POST['email'];
//$p = md5($password);

$recheck = "SELECT * FROM user_info WHERE email= '$email'";

$initialize = mysqli_query($conn,$recheck);
if(mysqli_num_rows($initialize)>0)
{
	$json['used']= "Email already used";
	mysqli_close($conn);
}
else
{
$query = "INSERT INTO user_info(Username,password,phone,email) VALUES('$username','$password','$phone','$email')";


$initialize = mysqli_query($conn,$query);
if($initialize)
{
	$json['success'] ="connected successfully";
}
else
{
	$json['error']="unable to connect";
}
mysqli_close($conn);


}

echo json_encode($json);
}



?>
      	