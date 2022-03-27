<?php if (!isset($_SESSION)) {
	session_start();
}  ?>
<?php 
include_once('../includes/layout/header.php');
include_once('../includes/session.php');
include_once('../includes/functions.php');
include_once('../includes/cnnectdb.php');
login_check ();

?>
 
<body>
   <!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Uplode Image</h4>
        </div>
        <div class="modal-body">
		
			<h1>UPLOAD MOVIES</h1>
			<br>
          <form action="create_menu_movies.php" method="post" enctype="multipart/form-data">
                <p>Select image to upload:</p>
                <input type="file" name="fileToUpload[]" id="fileToUpload" multiple="">
                <div id="preview"></div>
                <input type="submit" value="Upload Image" name="submit">
          </form>
		  
			<h1>UPLOAD series</h1>
			<br>
			
		<form action="create_menu_series.php" method="post" enctype="multipart/form-data">
                <p>Select image to upload:</p>
                <input type="file" name="fileToUpload[]" id="fileToUpload" multiple="">
                <div id="preview"></div>
                <input type="submit" value="Upload Image" name="submit">
          </form>
		  
		  <h1>UPLOAD animations</h1>
			<br>
			
		<form action="create_menu_animations.php" method="post" enctype="multipart/form-data">
                <p>Select image to upload:</p>
                <input type="file" name="fileToUpload[]" id="fileToUpload" multiple="">
                <div id="preview"></div>
                <input type="submit" value="Upload Image" name="submit">
          </form>
		  
		  <h1>UPLOAD PLAYS</h1>
			<br>
			
		<form action="create_menu_plays.php" method="post" enctype="multipart/form-data">
                <p>Select image to upload:</p>
                <input type="file" name="fileToUpload[]" id="fileToUpload" multiple="">
                <div id="preview"></div>
                <input type="submit" value="Upload Image" name="submit">
          </form>
		  
		  		  <h1>UPLOAD DOCUMENTARIES</h1>
			<br>
			
		<form action="create_menu_documentaries.php" method="post" enctype="multipart/form-data">
                <p>Select image to upload:</p>
                <input type="file" name="fileToUpload[]" id="fileToUpload" multiple="">
                <div id="preview"></div>
                <input type="submit" value="Upload Image" name="submit">
          </form>
		  
		  		  <h1>UPLOAD OTHERS</h1>
			<br>
			
		<form action="create_menu_others.php" method="post" enctype="multipart/form-data">
                <p>Select image to upload:</p>
                <input type="file" name="fileToUpload[]" id="fileToUpload" multiple="">
                <div id="preview"></div>
                <input type="submit" value="Upload Image" name="submit">
          </form>
		  
		<h1>UPLOAD GAMES</h1>
			<br>
			
		<form action="create_menu_games.php" method="post" enctype="multipart/form-data">
                <p>Select image to upload:</p>
                <input type="file" name="fileToUpload[]" id="fileToUpload" multiple="">
                <div id="preview"></div>
                <input type="submit" value="Upload Image" name="submit">
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>

 
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
  <span class="glyphicon glyphicon-star" aria-hidden="true"></span> Add new Movies  
</button>
<a type="button" class="btn btn-danger btn-lg" href="edit_movie.html">
  <span class="glyphicon glyphicon-list" aria-hidden="true"></span> Edit  Movies
</a>   

<a type="button" class="btn btn-danger btn-lg" href="delete_movies.php">
  <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Delete  Movies
</a>   
<br><br> 
  </div>
</div>
</div>
<div class="col-sm-10">
<div class="panel panel-info">
<div class="panel-heading">
</div>
</div>
</div>
</div>
</div>        
</center>
</body>
</html>

 
 <?php  
include("../includes/layout/footer.php");

?>