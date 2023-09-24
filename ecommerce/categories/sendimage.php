<?php

require '../init.php';

	if (isset($_POST['id'])) {
		$id = $_POST['id'];
	

 $check = "SELECT * FROM full_image_desc WHERE id = '$id'";
 $initial = mysqli_query($conn, $check);
 if(mysqli_num_rows($initial)>0){
 			$json['available'] = "image available";
 			echo json_encode($json);
 }
 else
 {
 	$json['error'] = "method is not possible";
 	echo json_encode($json);
 }
 mysqli_close($conn);

}


?>