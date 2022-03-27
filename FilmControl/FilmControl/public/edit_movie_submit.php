<?php session_start(); ?>
<?php 
include_once('../includes/layout/header.php');
include_once('../includes/session.php');
include_once('../includes/functions.php');
include_once('../includes/cnnectdb.php');
login_check ();

?>

<?php
if (isset($_POST["search"])) {
  $movie_name = $_POST["search"];

  $film_season=explode(",", $movie_name);
  $fName=$film_season[0];
  $fSeason=(int)$film_season[1];
  ?>


<body>
  

 
<br>

<center>
   <div class="container">
  <div class="row">
    <div class="col-sm-2">    
</div>
    <div class="col-sm-10">
<?php  echo   msg(); ?> 
    
</div>
</div>
</div>
  <div class="container">
  <div class="row">
<div class="col-sm-10">
<div class="panel panel-default">
  <div class="panel-heading"><span style="font-size:30px;"> MOVIES CONTROL</span></div>
  <div class="panel-body">
<br>
<button type="button" class="btn btn-danger btn-lg" data-toggle="modal" data-target="#myModal">
  <span class="glyphicon glyphicon-star" aria-hidden="true"></span> Choose a New Photo  
</button>


 <br><br>
  <div class="container">
  <div class="row">
    <div class="col-sm-2">
    
    
</div>
    <div class="col-sm-10">
<?php  echo   msg(); ?> 
<?php  $errors = err(); ?> 
<?php  error_function($errors); ?> 
    
</div>
</div>
</div>



  
 
  <form action="saveEdit.php" method="POST" enctype="multipart/form-data">
  	<?php 
	$query = "SELECT * FROM `imagestaple` WHERE name='$fName' AND season='$fSeason' ";
	$result = mysqli_query($conn, $query);
	if (mysqli_num_rows($result) > 0) {

          while($row = mysqli_fetch_assoc($result)) {

           ?>
           <!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Uplode Image</h4>
        </div>
        <div class="modal-body">
         
                <p>Select image to Change:</p>
                <input type="file" name="fileToUpload[]" id="fileToUpload" >
                <div id="preview"></div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>



             <div style="display: inline-flex; width: 100%; border: solid 0.5px; margin: 1vh;">
      <div class="form-group" style="width: 30%; ">
    <img src="<?php echo mysqli_real_escape_string($conn,$row["imagepath"]); ?>" name="photo" class="img-thumbnail" style="height: 250px;width: 250px;">
 
    <input type="hidden" name="filmid" value="<?php echo mysqli_real_escape_string($conn,$row["id"]); ?>">
   <input type="hidden" name="oldimagepath" value="<?php echo mysqli_real_escape_string($conn,$row["imagepath"]); ?>">
  </div>

  <div class="form-group" style="width: 30%;">
  <label for="text">The Name:</label>
  <div class="input-group" style="width: 90%;">
    <input id="text" type="text" class="form-control" name="name_image" value="<?php echo $row["name"];?>" placeholder="name...">
  </div>
  <label for="text">The Story:</label>
  <div class="input-group" style="width: 90%;">
    <input id="text" type="text" class="form-control" name="story" value="<?php echo mysqli_real_escape_string($conn,$row["story"]);  ?>" placeholder="story...">
  </div>

  <label for="text">The Trailer:</label>
  <div class="input-group" style="width: 90%;">
    <input id="text" type="text" class="form-control" name="trailer" 
    value="<?php echo mysqli_real_escape_string($conn,$row["trailer"]);  ?>"  placeholder="trailer...">
  </div>

    <label for="text">The Season:</label>
  <div class="input-group" style="width: 90%;">
    <input id="text" type="number" class="form-control" name="season"  value="<?php echo mysqli_real_escape_string($conn,$row["season"]);  ?>" 
     placeholder="season...">
  </div>
  

    <label for="text">The Year:</label>
  <div class="input-group" style="width: 90%;">
    <input id="text" type="number" class="form-control" name="year" 
     value="<?php echo mysqli_real_escape_string($conn,$row["year"]);  ?>" placeholder="year...">
  </div>

  </div>

  <div class="form-group" style="width: 30%;">
<label for="cat" >catygory</label>
    <select class="form-control" id="cat" name="catygory" style="width: 110%;">
      <option><?php echo mysqli_real_escape_string($conn,$row["catygory"]);  ?></option>
   		<option>ACTION</option>
   		<option>WAR</option>
   		<option>CRIME</option>
   		<option>MYSTERY</option>
   		<option>ADVENTURE</option>
   		<option>HORROR</option>
   		<option>DRAMA</option>
   		<option>ROMANTIC</option>
   		<option>COMEDIAN</option>
   		<option>SCIENCE FICTION IMOGEN</option>
   		<option>FANTASY</option>
   		<option>FAMILY</option>
   		<option>HISTORY</option>
   		<option>MUSICAL</option>
   		<option>SPORT</option>
   		<option>WESTERN</option>
   		<option>THRILLING</option>
		<option>PLAYS</option>
		<option>DOCUMENTARIES</option>
		<option>OTHERS</option>
		<option>GAMES</option>
    </select>
    <label for="brand">brand of section</label>
    <select class="form-control" id="brand" name="brand_of_section" style="width: 110%;">
      <option><?php echo mysqli_real_escape_string($conn,$row["brand_of_section"]);  ?></option>
 		<option>FOREIGN</option>
   		<option>ARABIC</option>
   		<option>INDIAN</option>
   		<option>TURKISH</option>
   		<option>KOREAN</option>
   		<option>JAPANESE</option>
   		<option>CHINESE</option>
   		<option>THAI</option>
   		<option>INDONESIAN</option>
		<option>ARABIC CARTONS</option>
		<option>MOVIES TRANSLATED</option>
		<option>MOVIES DABBED</option>
   		<option>SERIES TRANSLATED</option>
		<option>SERIES DABBED</option>
		<option>PLAYS</option>
		<option>DOCUMENTARIES</option>
		<option>OTHERS</option>
		<option>GAMES</option>
    </select>
    <label for="nSection">name of section</label>
    <select class="form-control" id="nSection" name="name_of_section" style="width: 110%;">
       <option><?php echo mysqli_real_escape_string($conn,$row["name_of_section"]);?></option>
      <option>MOVIES</option>
      <option>SERIES</option>
      <option>ANIMATIONS</option>
      <option>PLAYS</option>
      <option>DOCUMENTARIES</option>
      <option>OTHERS</option>
      <option>GAMES</option>
    </select>
  </div>
 

 
 
 </div> 


  <button type="submit" class="btn btn-default" name="submit" style="width: 100%; background-color: cornflowerblue;"><h4>Update</h4></button>
  <?php

}
}
}

?>

</form>
</div>
</div>
</div>
</div>
</div>
</body>
<!--?php
 $target_dir = "uploads/";
 $fname=$_FILES["fileToUpload"]["name"];
 $ftemp=$_FILES["fileToUpload"]["tmp_name"];

if($_SERVER["REQUEST_METHOD"]=="POST") {

   //for ($i=0; $i <sizeof($fname) ; $i++) { 

   
 
     for ($i=0; $i <sizeof($fname) ; $i++) { 
      $datetime =date("Ymdhisa");
    # code...
    $target_file = $target_dir.$datetime.$fname[$i].".jpg";
    $imageFileType = pathinfo($target_file,PATHINFO_EXTENSION);
      //move_uploaded_file($ftemp[$i],$target_file);

    }
  }
  ?-->

 <?php  
include("../includes/layout/footer.php");

?>

