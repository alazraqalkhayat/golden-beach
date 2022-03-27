<?php
/* Code for sign up*/
$con =new mysqli("194.5.156.94","u788111513_goldenbeach","goldenBeach@777","u788111513_goldenbeach");


if(!$con){
	echo "noooooooooo";
	
	
}else{

		//check for required fields
		if (isset($_POST[add_id])) {
			//get data from post
			$pid = $_POST[add_id];
			

						$delete_add = mysqli_query($con, "DELETE FROM `adds` WHERE add_id=$pid");
								if($delete_add ){

							$response["response_message"] = "DELETED SUCCESSFULLY";
										// print JSON response
										echo json_encode($response);
							}else{

								$response["response_message"] = "AN ERROR OCCURRED DURING THE EDIT PLEASE TRY AGAIN LATER ..! ";
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


	