<?php
/* Code for sign up*/
$con =new mysqli("194.5.156.94","u788111513_goldenbeach","goldenBeach@777","u788111513_goldenbeach");


if(!$con){
	echo "noooooooooo";
	
	
}else{

		//check for required fields
		if ( isset($_POST[id_of_user])&& isset($_POST[id_of_all]) && isset($_POST[value_of_evaluation])) {
			//get data from post
			$evaluation_id_of_user = $_POST[id_of_user];
			$evaluation_id_of_all = $_POST[id_of_all];
			$evaluation_valuse_of_evaluation=$_POST[value_of_evaluation];
			
			$select_evaluation = mysqli_query($con, "select value_of_evaluation from `user_evaluate_all` where id_of_user=$evaluation_id_of_user and id_of_all=$evaluation_id_of_all");
	
			if(mysqli_num_rows($select_evaluation) == 0){
			
						$insert_evaluate = mysqli_query($con, "INSERT INTO `user_evaluate_all` (`id_of_user`, `id_of_all`, `value_of_evaluation`) VALUES ($evaluation_id_of_user 
						,$evaluation_id_of_all ,$evaluation_valuse_of_evaluation)");
						// required field is missing
								if($insert_evaluate){

							$response["response_message"] = "The evaluation process was successful";
										// print JSON response
										echo json_encode($response);
							}else{

								$response["response_message"] = "AN ERROR OCCURRED DURING THE EDIT PLEASE TRY AGAIN LATER ..!";
										// print JSON response
										echo json_encode($response);		

							}
			
			
			}else{
								$set_evaluate = mysqli_query($con, "update `user_evaluate_all` set value_of_evaluation=$evaluation_valuse_of_evaluation where id_of_user=$evaluation_id_of_user and id_of_all='$evaluation_id_of_all'");
						// required field is missing
								if($set_evaluate){

							$response["response_message"] = "your evaluation has been changed successfully";
										// print JSON response
										echo json_encode($response);
							}else{

								$response["response_message"] = "AN ERROR OCCURRED DURING THE EDIT PLEASE TRY AGAIN LATER ..!";
										// print JSON response
										echo json_encode($response);		

							}
			
			}
			





		}else{


			// required field is missing
			$response["response_message"] = "Required fields is missing";
			// print JSON response
			echo json_encode($response);

		}

}


	