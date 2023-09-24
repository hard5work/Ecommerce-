<?php
	require '../init.php'; // For database connection

if(isset($_POST['name'],$_POST['price']))//for variables. doesnot give error even in null
 {
 $target_path = "menwear/";
 $target_file = $target_path.basename($_FILES["image"]["name"]); //url in other name
 $name = $_POST['name'];
 $price = $_POST['price'];
 $query1 = "SELECT * FROM men_wear WHERE image = '$target_file'"; //check if images already existed or not

$result1 = mysqli_query($conn,$query1);
if(mysqli_num_rows($result1)>0)
{
	$json['image'] ="image name already exists"; //gives error if images already exits
echo json_encode($json);
mysqli_close($conn);

}
else
{
 $query = "INSERT INTO men_wear(name,image,price) VALUES ('$name' , '$target_file', '$price')";

 $result = mysqli_query($conn, $query);
 if ($result) {
 	if (move_uploaded_file($_FILES["image"]["tmp_name"], $target_file)) {
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
}	

?>
