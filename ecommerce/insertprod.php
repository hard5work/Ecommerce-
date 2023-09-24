<?php

require 'init.php';

if(isset($_POST['name'],$_POST['image'] , $_POST['price'] , $_POST['longdesc'] , $_POST['category'] , $_POST['tags'])){

	$name = $_POST['name'];
	$price= $_POST['price'];
	$image = $_POST['image'];
	$longdesc = $_POST['longdesc'];
	$category = $_POST['category'];
	$tags = $_POST['tags'];
	$upload_path = "menlongdesc/".$name.$category.".jpg";

	$query = "INSERT INTO full_image_desc(name,image,price,long_desc,category,tags) VALUES ('$name','$upload_path','$price','$longdesc','$category','$tags')";
	$result = mysqli_query($conn,$query);
	if ($result) {
		
			file_put_contents($upload_path, base64_decode($image));
			$json['success'] = "IMAGE updated";
			echo json_encode($json);
			}
			else{
				$json['error'] = "IMAGE not uploaded";
				echo json_encode($json);
			}
	}
	else
	{
		$json["fileerror"] = "File not found";
		echo json_encode($json);
	}







?>