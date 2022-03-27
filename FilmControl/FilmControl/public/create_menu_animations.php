<?php session_start(); 
 
 
?>
<?php 
include_once('../includes/session.php');
include_once('../includes/functions.php');
include_once('../includes/cnnectdb.php');

login_check ();
 

include_once('../includes/layout/header.php');

?>


<body>

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



  <div class="container">
  <div class="row">

<div class="col-sm-10">
   
 <div class="panel panel-danger">
  <div class="panel-heading"><center><h2>Add new Movies</h2></center></div>


  <div class="panel-body">
<?php
$target_dir = "uploads/";
$fname=$_FILES["fileToUpload"]["name"];
$ftemp=$_FILES["fileToUpload"]["tmp_name"];
if(isset($_POST["submit"])) {
  ?>
  <form action="save.php" method="post">
  <?php
  for ($i=0; $i <sizeof($fname) ; $i++) { 

      $datetime =date("Ymdhisa");
    # code...
    $target_file = $target_dir .$datetime.$fname[$i].".jpg";
    $imageFileType = pathinfo($target_file,PATHINFO_EXTENSION);
      move_uploaded_file($ftemp[$i], $target_file);
   ?>
    <div style="display: inline-flex; width: 100%; border: solid 0.5px; margin: 1vh;">
      <div class="form-group" style="width: 30%; ">
    <img src="<?php echo $target_file ?>" name="photo" class="img-thumbnail" style="height: 250px;width: 250px;">
    <input type="hidden" name="array_of_image[<?php echo $i ?>]" value="<?php echo $target_file ?>">
   <input type="hidden" name="images_names[<?php echo $i ?>]" value="<?php echo $fname[$i] ?>">
  </div>

  <div class="form-group" style="width: 30%;">
  <label for="text">The Name:</label>
  <div class="input-group" style="width: 90%;">
    <input id="text" type="text" class="form-control" name="name_image[]" value="<?php echo $fname[$i] ?>" placeholder="name...">
  </div>
  <label for="text">The Story:</label>
  <div class="input-group" style="width: 90%;">
    <input id="text" type="text" class="form-control" name="story[]" placeholder="story...">
  </div>

  <label for="text">The Trailer:</label>
  <div class="input-group" style="width: 90%;">
    <input id="text" type="text" class="form-control" name="trailer[]" placeholder="trailer...">
  </div>

    <label for="text">The Season:</label>
  <div class="input-group" style="width: 90%;">
    <input id="text" type="number" class="form-control" name="season[]" placeholder="season...">
  </div>

    <label for="text">The Year:</label>
  <div class="input-group" style="width: 90%;">
    <input id="text" type="number" class="form-control" name="year[]" placeholder="year...">
  </div>

  </div>

  <div class="form-group" style="width: 30%;">
<label for="cat" >catygory</label>
    <select class="form-control" id="cat" name="catygory[]" style="width: 110%;">
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
    </select>
    <label for="brand">brand of section</label>
    <select class="form-control" id="brand" name="brand_of_section[]" style="width: 110%;">
       	<option>MOVIES TRANSLATED</option>
		<option>MOVIES DABBED</option>
   		<option>SERIES TRANSLATED</option>
		<option>SERIES DABBED</option>
    </select>
    <label for="nSection">name of section</label>
    <select class="form-control" id="nSection" name="name_of_section[]" style="width: 110%;">
     <option>ANIMATIONS</option>
    </select>
  </div>
 

 
 
 </div> 

 <?php
}

}
?>

  <button type="submit" class="btn btn-default" name="submit" style="width: 100%; background-color: cornflowerblue;"><h4>Save</h4></button>
</form>




  </div>
</div>
  
  
  

</div>


</div>
</div>


 









 
          
        
  
  
 
  </body>
</html>
 
 
 

 
 <?php  
include("../includes/layout/footer.php");

?>