<?php
require '../init.php';

if (isset($_POST['name'] , $_POST['email'] , $_POST['price'] , $_POST['qnty'])){
	$name = $_POST['name'];
	$price = $_POST['price'];
	$qnty = $_POST['qnty'];
	$amount = $price * $qnty;
	$email = $_POST['email'];

	$target_path = "menwear/";
 	$target_file = $target_path.basename($_FILES["image"]["name"]);

	$query1 = "SELECT name FROM men_wear WHERE name = '$name' "; 
	 $query2 = "SELECT price FROM men_wear WHERE price = '$price'";
	 $query3 = "SELECT email FROM user_info WHERE email = '$email'";
	 $query4 = "SELECT image FROM men_wear WHERE image = '$target_file'";
	$result1 = mysqli_query($conn,$query1);
	$result2 = mysqli_query($conn,$query2);
	$result3 = mysqli_query($conn,$query3);
	$result4 = mysqli_query($conn,$query4);
if((mysqli_num_rows($result1)>0) && (mysqli_num_rows($result2)>0) && (mysqli_num_rows($result3)>0 )&& (mysqli_num_rows($result4)>0)){

	$json['exists'] ="name and price ,email exists"; //gives error if images already exits
		echo json_encode($json);

	$query = "INSERT INTO cart_list(name,qnty,price,user_email,amount) VALUES ('$name','$qnty','$price','$email','$amount')";
	$result = mysqli_query($conn, $query);
	if($result){
		$json['success'] = "success";
		echo json_encode($json);
	}

	else
	{
		$json['unable'] = "unable to add item.".mysqli_error($conn);
		echo json_encode($json);
	}
}
else{
	$json['nouser'] = "unable to use if loop - variable doent exists".mysqli_error($conn);
		echo json_encode($json);

}
}


?>