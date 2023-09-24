<?php 
require "init.php";

if (isset($_POST['id'])) {
	$id= $_POST['id'];

	$query = "SELECT image FROM full_image_desc WHERE id = '$id'";
	$result = mysqli_query($conn, $query);
	while($fetch = mysqli_fetch_assoc($result)){
		if ($fetch) {
			$image = $fetch['image'];
			$json['image'] = "$image";
			echo json_encode($json);
		}
	}
}



?>