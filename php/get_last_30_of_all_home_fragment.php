<?php

$con =new mysqli("194.5.156.94","u788111513_goldenbeach","goldenBeach@777","u788111513_goldenbeach");


if(!$con){
	echo "noooooooooo";
	
	
}else{

						
//$var=$_GET[limit];

$select_query=mysqli_query($con,"SELECT * FROM `all` WHERE name_of_section='".$_GET['name_of_section']."' ORDER BY id DESC LIMIT 30");


		if(mysqli_num_rows($select_query) == 0){

		$post["id"]=0;	
		$post["name_of_section"]=$_GET['name_of_section'];
		//$post["name_of_section"]="null";
		$post["brand_of_section"]="null";
		$post["name_of_category"]="null";
		$post["name"]="null";
		$post["session"]=0;	
		$post["name_of_image"]="null";
		$post["year"]=0;
		$post["story"]="null";
		$post["trailer"]="null";

$out[]=$post;
		echo json_encode($out);
		//print(json_encode($post));


}else{


while($row=mysqli_fetch_assoc($select_query)){

	$output[]=$row;


	//print(json_encode($output));	
}
echo json_encode($output);




	
}



}					