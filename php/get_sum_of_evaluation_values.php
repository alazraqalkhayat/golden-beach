<?php

$con =new mysqli("194.5.156.94","u788111513_goldenbeach","goldenBeach@777","u788111513_goldenbeach");


if(!$con){
	echo "noooooooooo";
	
	
}else{

						

 
$select_query=mysqli_query($con,"SELECT SUM(value_of_evaluation) as sum_of_evaluation FROM user_evaluate_all WHERE id_of_all=$_GET[id_of_all] ");


		if(mysqli_num_rows($select_query) == 0){

		$post["sum_of_evaluation"]="0";	

echo json_encode($post);

//$out[]=$post;
		//echo json_encode($out);
		//print(json_encode($post));


}else{

//echo json_encode($select_query);
while($row=mysqli_fetch_assoc($select_query)){

	$output=$row;
echo json_encode($output);

	//print(json_encode($output));	
}
//echo json_encode($output);




	
}



}					