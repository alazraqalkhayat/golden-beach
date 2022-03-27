<!DOCTYPE html>
<html>
<body>
<div class="card">
<form action="upload_movies.php" method="post" enctype="multipart/form-data">
	<h2>upload movies </h2><br>
	Select image to upload:
    <input type="file" name="fileToUpload[]" id="fileToUpload" multiple="">
    <input type="submit" value="Upload movies" name="submit">
</form>
</div>

<br><br><br>

<div class="card">
<form action="upload_series.php" method="post" enctype="multipart/form-data">
	<h2>upload series </h2><br>
	Select image to upload:
    <input type="file" name="fileToUpload[]" id="fileToUpload" multiple="">
    <input type="submit" value="Upload series" name="submit">
</form>
</div>


<br><br><br>

<div class="card">
<form action="upload_animations.php" method="post" enctype="multipart/form-data">
	<h2> upload animations </h2><br>
	Select image to upload:
    <input type="file" name="fileToUpload[]" id="fileToUpload" multiple="">
    <input type="submit" value="Upload animations" name="submit">
</form>
</div>

<br><br><br>

<div class="card">
<form action="upload_plays.php" method="post" enctype="multipart/form-data">
	<h2>upload plays </h2><br>
	Select image to upload:
    <input type="file" name="fileToUpload[]" id="fileToUpload" multiple="">
    <input type="submit" value="Upload plays" name="submit">
</form>

<br><br><br>

<div class="card">
<form action="upload_documentaries.php" method="post" enctype="multipart/form-data">
	<h2>upload documentaries </h2><br>
	Select image to upload:
    <input type="file" name="fileToUpload[]" id="fileToUpload" multiple="">
    <input type="submit" value="Upload documentaries" name="submit">
</form>

<br><br><br>

<div class="card">
<form action="upload_others.php" method="post" enctype="multipart/form-data">
	<h2>upload others </h2><br>
	Select image to upload:
    <input type="file" name="fileToUpload[]" id="fileToUpload" multiple="">
    <input type="submit" value="Upload others" name="submit">
</form>

<br><br><br>
<div class="card">
<form action="upload_games.php" method="post" enctype="multipart/form-data">
	<h2>upload games </h2> <br>
	Select image to upload:
    <input type="file" name="fileToUpload[]" id="fileToUpload" multiple="">
    <input type="submit" value="Upload games" name="submit">
</form>

</body>
</html>
<style type="text/css">
	.card {
    /* Add shadows to create the "card" effect */
    box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
    transition: 0.3s;
    height: 100px;
    width: 500px;
    padding-top: 50px;
    font-size: 20px;
    margin-top:28px;
}

/* On mouse-over, add a deeper shadow */
.card:hover {
    box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
}
</style>