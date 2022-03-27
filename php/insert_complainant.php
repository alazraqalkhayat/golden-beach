<?php
/* Code for sign up*/
$con =new mysqli("194.5.156.94","u788111513_goldenbeach","goldenBeach@777","u788111513_goldenbeach");


if(!$con){
	echo "noooooooooo";
	
	
}else{
//complainant_date
		//check for required fields
		if (isset($_POST['id_of_user']) && isset($_POST['complainant']) && isset($_POST['complainant_date'])) {
			//get data from post
			$uid = $_POST[id_of_user];
			$compl = $_POST['complainant'];
			$date = $_POST['complainant_date'];
			
			
			//$uevaluate=$_POST[valuse_of_evaluation];
			
	
			
			
						$insert_complainant = mysqli_query($con, "INSERT INTO `users_complainants`(`id_of_user`,`complainant`,`complainant_date`) VALUES ($uid,'$compl','$date')");
						// required field is missing
								if($insert_complainant){

							$response["response_message"] = "ADDED COMPLAINANT SUCCESSFULLY";
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


	