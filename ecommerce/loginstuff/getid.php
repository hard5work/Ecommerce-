<?php
require '../init.php';
if(isset($_POST['email'])){
	$email = $_POST['email'];

	$query = "SELECT id,username,image FROM user_info WHERE email = '$email'";
	$result = mysqli_query($conn, $query);
while ($fetch = mysqli_fetch_ASSOC($result)){
	if($fetch){
		$id =$fetch['id'];
		$username = $fetch['username'];
		$image = $fetch['image'];
		$json['id'] = "$id";
		$json['username'] = "$username";
		$json['image']= "$image";
		echo json_encode($json);
	}
}

}





?>