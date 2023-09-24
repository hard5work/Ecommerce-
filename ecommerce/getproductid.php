<?php 

require 'init.php';
	
	$query = "SELECT id FROM full_image_desc ";
	$result = mysqli_query($conn,$query);
/*while ($fetch = mysqli_fetch_ASSOC($result)){
	if($fetch){
		$id =$fetch['id'];
		$json['id'] = "$id";
		echo json_encode($json);
	}
	}*/

	$info = array();
	while($row = mysqli_fetch_array($result)){
		$temp = array();
		$temp["id"] = $row['0'];

		array_push($info, $temp);
	}
	echo json_encode($info);






?>