<?php
 require '../init.php';
 if(isset($_POST['name']))
 {
 $target_path = "images/";
 $target_file = $target_path.basename($_FILES["url"]["name"]); //url in other name
 $name = $_POST['name'];
 $query = "INSERT INTO image_info(name,url) VALUES ('$name' , '$target_file')";

 $result = mysqli_query($conn, $query);
 if ($result) {
 	if (move_uploaded_file($_FILES["url"]["tmp_name"], $target_file)) {
 		$json['success'] = "IMAGE uploaded";
 		echo json_encode($json);
 	 	}
 	else
 	{
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
else
{
	$json['error']= "Error loading";
	echo json_encode($json);
}


?>

$product_id="SELECT id from full_image_desc order by id,desc";
$query=mysqli_query($ocnn,$product_id)

while($id=mysqli_fetch_assoc($query){
	$productid=$id['id'];
}

<!-- 
<?php
require "../init.php";

$json = json_decode(file_get_contents('php://input'),true);
$imagelist = $json["imagelist"]

$i=0;


if (isset($_POST['imagelist'])) {
	$imagelist = $_POST['imagelist'];
	$upload_path = "productitems/";
	

	if (is_array($imagelist)) {
		foreach ($imagelist as $image) {
			$decodeImage = base64_decode("$image");
			$return = file_put_contents($upload_path."test"."_".$i.".JPG", $decodeImage);
			$upload_file = $upload_path."test"."_".$i.".JPG";
			if ($return !== false) {
				$json['success'] = 1;
				$json['message'] = "Imageuploaded";
				echo json_encode($json);
			}
			else {
				$json['success'] = 0;
				$json['message'] = "Image upload fail";
				echo json_encode($json);
			}
			$query = "INSERT INTO product_images(product_image) VALUES ('$upload_file')";
			$check = mysqli_query($conn,$query);
	
			$i++;
		}
	}
	}

else{
	$json['success'] = 0;
	$json['message'] = "List is Empty";
	echo json_encode($json);
}



?>  -->