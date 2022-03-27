<?php
//$con =new mysqli("mysql.aba.ae","GoldenBetch","GoldenBetch123","golbetch");
//$con =new mysqli("194.5.156.94","u788111513_goldenbeach","goldenBeach@777","u788111513_goldenbeach");

$servername = "194.5.156.94";
$username = "u788111513_goldenbeach";
$password = "goldenBeach@777";
$dbname = "u788111513_goldenbeach";

$images=$_POST["array_of_image"]; 
// Create connection
$conn = mysqli_connect($servername, $username, $password, $dbname);
// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
for ($i=0; $i < sizeof($_POST["array_of_image"]); $i++) { 
	
	$sql = 'INSERT INTO `all` (name, story, trailer, session, year, name_of_image, name_of_category, brand_of_section, name_of_section) VALUES ("'.$_POST["name_image"][$i].'","'.$_POST["story"][$i].'","'.$_POST["trailer"][$i].'","'.$_POST["season"][$i].'","'.$_POST["year"][$i].'","'.$_POST["array_of_image"][$i].'","'.$_POST["catygory"][$i].'","'.$_POST["brand_of_section"][$i].'","'.$_POST["name_of_section"][$i].'")';

	if (mysqli_query($conn, $sql)) {
    	echo $_POST["images_names"][$i]."Uploaded successfully";
    	?>
    	<br>
    	<?php
	} else {
    		echo "Error: " . $sql . "<br>" . mysqli_error($conn);
	}

	
}
mysqli_close($conn);
?>