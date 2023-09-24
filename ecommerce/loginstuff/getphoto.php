<?php
require '../init.php';
if(isset($_POST['email'])){
	$email = $_POST['email'];

	$query = "SELECT image FROM user_info WHERE email = '$email'";
	$result = mysqli_query($conn, $query);
while ($fetch = mysqli_fetch_ASSOC($result)){
	if($fetch){
		$image = $fetch['image'];
		
		$json['image']= "$image";
		echo json_encode($json);
	}
}

}





?>