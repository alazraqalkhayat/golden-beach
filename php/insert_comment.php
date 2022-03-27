<?php
/* Code for sign up*/
$con =new mysqli("194.5.156.94","u788111513_goldenbeach","goldenBeach@777","u788111513_goldenbeach");


if(!$con){
	echo "noooooooooo";
	
	
}else{

		//check for required fields
		if (isset($_POST['id_of_user']) && isset($_POST['id_of_all']) && isset($_POST['comment'])) {
			//get data from post
			$uid=$_POST[id_of_user];
			$com=$_POST['comment'];
$allid= $_POST['id_of_all'];
			
			
			//$uevaluate=$_POST[valuse_of_evaluation];
			
	
			
			
						$insert_complainant = mysqli_query($con, "INSERT INTO `users_comments`(`id_of_user`,`id_of_all`,`comment`) VALUES ($uid,$allid,'$com')");
						// required field is missing
								if($insert_complainant){

							$response["response_message"] = "ADDED COMMENT SUCCESSFULLY";
										// print JSON response
										echo json_encode($response);
							}else{

								$response["response_message"] = "AN ERROR OCCURRED DURING THE EDIT PLEASE TRY AGAIN LATER ..!";
										// print JSON response
										echo json_encode($response);		

							}


		}else{


			// required field is missing
			$response["response_message"] = "Required fields is missing";
			// print JSON response
			echo json_encode($response);

		}

}


	