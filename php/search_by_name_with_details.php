<?php
/* Code for sign up*/
$con =new mysqli("194.5.156.94","u788111513_goldenbeach","goldenBeach@777","u788111513_goldenbeach");


if(!$con){
	echo "noooooooooo";//	image_of_product	
	
	
}else{

              // LIMIT $slimit,2
					
			//$select_query="SELECT * FROM `all` WHERE all.name LIKE '%".$_GET['name']."%' AND  LIMIT $_GET[limit],2";

						$baseic_select=mysqli_query($con,
						"SELECT * FROM `all` WHERE all.name LIKE '%".$_GET['name']."%' 
						AND all.name_of_section='".$_GET['name_of_section']."'
						AND all.brand_of_section='".$_GET['brand_of_section']."' 
						AND all.name_of_category='".$_GET['name_of_category']."' LIMIT $_GET[limit],6");

						if(mysqli_num_rows($baseic_select) == 0){

								$post["id"]=0;	
								$post["name_of_section"]="";
								$post["brand_of_section"]="";
								$post["name_of_category"]="";
								$post["name"]="null";
								$post["session"]=0;	
								$post["name_of_image"]="";
								$post["year"]=0;
								$post["story"]="";
								$post["trailer"]="";

								echo "[]";
						//$out[]=$post;
								//echo json_encode($out);
								//print(json_encode($post));


						}else{


						while($row=mysqli_fetch_assoc($baseic_select)){

							$output[]=$row;


							//print(json_encode($output));	
						}
						echo json_encode($output);
														


					}

}


																			