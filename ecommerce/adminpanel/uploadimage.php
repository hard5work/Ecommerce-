<?php
require '../init.php';
if (isset($_POST['imageList'])) {/*
$content = file_get_contents("php://input");
$resp = json_decode($content ,TRUE);
$imageList = json_decode($resp['imageList'] ,TRUE);*/


  // $json = json_decode(file_get_contents('php://input'),true);

  // echo json_encode($json);
 
//   $name = $json["name"]; //within square bracket should be same as Utils.imageName & Utils.imageList

	$imageList = $_POST['imageList'];

	

    //$imageList = $json["imageList"];

//	$imageList = array();
   
 //  $i = 0;

 
//if (isset($_POST['imageList'])) {
  //  if (isset($imageList)) {
  //  if (is_array($imageList)) {

  //	foreach($imageList as $image) {
	for ($i=0 ; $i< count($imageList); $i++)
	{
	      		$upload_file= ("productitems/"."name"."_0" .$i.".JPG");
	      		$query = "INSERT INTO product_images(product_image,productid , name) VALUES ('$upload_file', 7 ,'name')";
			$check = mysqli_query($conn,$query);
			if ($check) {

	      		$decodedImage = base64_decode("$imageList");

			
	      		$return =file_put_contents($upload_file, $decodedImage);
	      		if($return != false){
			   $response['success'] = "Image Uploaded Successfully";
			   echo json_encode($response);
			}else{
			   $response['fail'] = "Image Uploaded Failed";
			   echo json_encode($response);
			}
		}

		//$i++;
		
	}
	       
    	
  //  }
   /* else
    {
    	$response['noarray'] = "No Array found";
    echo json_encode($response);
    }*/
    } else{
        $response['error'] = "List is empty.";
        echo json_encode($response);
    }
 
    
?> 