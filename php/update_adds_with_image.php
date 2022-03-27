<?php
/* Code for sign up*/
$con =new mysqli("194.5.156.94","u788111513_goldenbeach","goldenBeach@777","u788111513_goldenbeach");


if(!$con){
	echo "noooooooooo";//	image_of_product	
	
	
}else{

		//check for required fields
		if (isset($_POST[add_id]) && isset($_POST['name_of_owner']) && isset($_POST['name_of_image_of_add']) && isset ($_POST['description']) && isset($_POST['date_of_add'])) {
			//get data from post
			$add_id_of_add= $_POST[add_id];
			$add_name_of_owner= $_POST['name_of_owner'];
			$add_name_of_image_of_add = $_POST['name_of_image_of_add'];
			$add_description= $_POST['description'];
			$add_date_of_add = $_POST['date_of_add'];
			

			$path="adds_images/$add_date_of_add.jpg";

			//$path="profiles_images/$date.jpg";
			
			$update_add_query="UPDATE `adds` SET `name_of_owner`='$add_name_of_owner',`name_of_image_of_add`='$path',`description`='$add_description',`date_of_add`='$add_date_of_add'  WHERE add_id=$add_id_of_add";
			
			
				$update_add=mysqli_query($con,$update_add_query);
				if($update_add){			
                                                                        file_put_contents($path,base64_decode($add_name_of_image_of_add));
																									
									$response["response_message"] ="SUCCESSFUL";

								           echo json_encode($response);	

		
				}else{
				                                          $response["response_message"] = "AN ERROR OCCURRED DURING THE EDIT PLEASE TRY AGAIN LATER ..!";
								           echo json_encode($response);	
				}

			

	

		}else{


			// required field is missing
			$response["response_message"] = "Required fields is missing";
			// print JSON response
			echo json_encode($response);

		}

}


										