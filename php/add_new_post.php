<?php
/* Code for sign up*/
$con =new mysqli("194.5.156.94","u788111513_goldenbeach","goldenBeach@777","u788111513_goldenbeach");


if(!$con){
	echo "noooooooooo";//	image_of_product	
	
	
}else{

		//check for required fields
		if (isset($_POST['name_of_section']) && isset($_POST['brand_of_section']) && isset ($_POST['name_of_category']) && isset($_POST['name']) && isset($_POST[session]) && isset($_POST['name_of_image']) && isset($_POST[year])&& isset($_POST['story'])&& isset($_POST['trailer'])) {
			//get data from post

			$pname_of_section= $_POST['name_of_section'];
			$pbrand_of_section= $_POST['brand_of_section'];
			$pname_of_category = $_POST['name_of_category'];
			$pname = $_POST['name'];
			$psession= $_POST[session];
			$pname_of_image=$_POST['name_of_image'];
			$pyear = $_POST[year];
			$pstory= $_POST['story'];
			$ptrailer= $_POST['trailer'];

			$path="all_images/$pname$psession.jpg";

			//$path="profiles_images/$date.jpg";
			
			

			$select=mysqli_query($con,"SELECT * FROM `all` WHERE name='$pname' AND session=$psession");
			
			if(mysqli_num_rows($select)==0){
				
				$add_new_post=mysqli_query($con,"INSERT INTO `all`(name_of_section,brand_of_section,name_of_category,name,session,name_of_image,year,story,trailer) VALUES ('$pname_of_section','$pbrand_of_section','$pname_of_category','$pname',$psession,'$path',$pyear,'$pstory','$ptrailer')");
				if($add_new_post){																														

									 file_put_contents($path,base64_decode($pname_of_image));
								  $response["response_message"] ="SUCCESSFUL";
								           echo json_encode($response);	

		
				}else{
				                                          $response["response_message"] = "AN ERROR OCCURRED DURING THE EDIT PLEASE TRY AGAIN LATER ..!";
								           echo json_encode($response);	
				}


				 
			}else{
				

	                                $response["response_message"] ="THE ITEM YOU INSERTED IS ALREADY FOND WITH THE SAME SESSION";

                                  		echo json_encode($response);	
				
			}
			


	

		}else{


			// required field is missing
			$response["response_message"] = "Required fields is missing";
			// print JSON response
			echo json_encode($response);

		}

}


											