<?php
/* Code for sign up*/
$con =new mysqli("194.5.156.94","u788111513_goldenbeach","goldenBeach@777","u788111513_goldenbeach");


if(!$con){
	echo "noooooooooo";
	
	
}else{
//suggestion_date
		//check for required fields
		if (isset($_POST[id_of_user]) && isset($_POST['suggestion']) && isset($_POST['suggestion_date'])) {
			//get data from post
			$uid = $_POST[id_of_user];
			$suges = $_POST['suggestion'];
                        $sdate= $_POST['suggestion_date'];
			
			
			//$uevaluate=$_POST[valuse_of_evaluation];
			
	
			
			
						$insert_suggestion = mysqli_query($con, "INSERT INTO `users_suggestions`(`id_of_user`,`suggestion`,`suggestion_date`) VALUES ($uid,'$suges','$sdate')");
						// required field is missing
								if($insert_suggestion ){

							$response["response_message"] = "ADDED SUGGESTION SUCCESSFULLY";
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


	