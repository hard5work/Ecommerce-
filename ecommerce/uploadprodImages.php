<?php

require 'init.php';

if(isset($_POST['productid'],$_POST['image'] , $_POST['name'])){
	$productid = $_POST['productid'];
	$imagename = $_POST['name'];
	$image = $_POST['image'];
	$upload_path = "productitems/".$imagename.".jpg";

	$query1 = "INSERT INTO product_images(productid,name,product_image) VALUES ('$productid' , '$imagename' , '$upload_path')";
				$result1 = mysqli_query($conn,$query1);
				if($result1)
				{
					file_put_contents($upload_path, base64_decode($image));
			
				 		$json['success'] = "IMAGE updated";
				 		echo json_encode($json);
				 	 	}
				 	else
				 	{
				 		$json['unsuccess'] ="Unable to update Image";
				 		echo json_encode($json);
					 	}



	}
	else{
		$json['nofile'] = "File not found";
		echo json_encode($json);

	}



?>