<?php
require '../init.php';
if(isset($_POST['email'])){
	$email = $_POST['email'];

	$query = "SELECT id,username,email,contact_no,address,firstname,lastname,middlename,image FROM user_info WHERE email = '$email'";
	$result = mysqli_query($conn, $query);
while ($fetch = mysqli_fetch_ASSOC($result)){
	if($fetch){
		$id =$fetch['id'];
		$username = $fetch['username'];
		$email = $fetch['email'];
		$contact_no =$fetch['contact_no'];
		$address = $fetch['address'];
		$firstname =$fetch['firstname'];
		$lastname = $fetch['lastname'];
		$middlename =$fetch['middlename'];
		$image = $fetch['image'];
		$json['id'] = "$id";
		$json['username'] = "$username";
		$json['email'] = "$email";
		$json['contact_no'] = "$contact_no";
		$json['address'] = "$address";
		$json['firstname'] = "$firstname";
		$json['lastname'] = "$lastname";
		$json['middlename'] = "$middlename";
		$json['image'] = "$image";
		echo json_encode($json);
	}
}

}





?>