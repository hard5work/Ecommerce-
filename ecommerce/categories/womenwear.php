<?php
require '../init.php';
if (isset($_POST['name'], $_POST['price'])){
	
	$target_path = "womenwear/";
	$target_file = $target_path.basename($_FILES["image"]["name"]);
	$name = $_POST['name'];
	$price = $_POST['price'];

	$query1 = "SELECT * FROM women_wear WHERE image = '$target_file'";

	$result1 = mysqli_query($conn, $query1);
	if(mysqli_num_rows($result1)>0)
		{
			$json['image'] ="image name already exists"; //gives error if images already exits
		echo json_encode($json);
		mysqli_close($conn);

		}
		else
		{
			$query = "INSERT INTO women_wear(name, image, price) VALUES ('$name', '$target_file' , '$price') ";
			$result = mysqli_query($conn,$query);

			if($result)
			{
				if (move_uploaded_file(($_FILES["image"]["tmp_name"]), $target_file)) {
					
				$json['success'] = "IMAGE uploaded";
		 		echo json_encode($json);
		 	 	}
		 	else
		 	{
		 		$json['unsuccess'] ="Database enrty ok butIMAGE not uploaded ". mysqli_error($conn);
		 		echo json_encode($json);
		 	}
		 }

		 else
		 {
		 	$json['fileUnsuccess'] = "FILE NOT UPLOADED";
		 	echo json_encode($json);
		 }

	}
	}	
?>