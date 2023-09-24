<?php
	require '../init.php';

	
	if (ISSET($_POST['name'], $_POST['price'] , $_POST['longdesc'], $_POST['category'])) {
		
	
		$name 	  = $_POST['name'];
		$price 	  = $_POST['price'];
		$longdesc = $_POST['longdesc'];
		$category = $_POST['category'];

		$target_path = "menlongdesc/";
 		$target_file = $target_path.basename($_FILES["image"]["name"]);

 		$check = "SELECT * FROM full_image_desc WHERE image = '$target_file'";
 		$initial = mysqli_query($conn, $check);
 		if(mysqli_num_rows($initial)>0){
 			$json['repeat'] = "image repeated please change name";
 			echo json_encode($json);
 		}
 		else{

 			$query = "INSERT INTO full_image_desc(name,image,price,long_desc,category) VALUES ('$name','$target_file','$price','$longdesc' ,'$category')";
 			$result = mysqli_query($conn,$query);
 			if($result){
 				if(move_uploaded_file($_FILES["image"]["tmp_name"], $target_file)){
 					$json['success'] = "IMAGE uploaded";
 		echo json_encode($json);
 	 	}
 			else{

			 		$json['unsuccess'] ="IMAGE not uploaded";
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
/*
$product_id = "SELECT id FROM full_image_desc ORDER BY  id ASC";
$queryi=mysqli_query($conn,$product_id);

while($id=mysqli_fetch_assoc($queryi)){
	if($id){
	$productid= $id['id'];
	$json["id"] = "$productid";
	echo json_encode($json);
}
}*/


/*	$insertfav="INSERT INTO fav_list(favorite,productid) VALUES('0','$productid')";
	$initial2= mysqli_query($conn, $insertfav);
 		if($initial2){
 			$json['addedtofav'] = "data added to fav database";
 			echo json_encode($json);
 		}
*/










?>