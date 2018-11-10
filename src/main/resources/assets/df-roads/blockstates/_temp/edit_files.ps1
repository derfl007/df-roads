foreach($file in Get-ChildItem *.json) {
  $file_content = Get-Content $file
  $file_name = $file.BaseName
  $file_content.replace('road_sign_mandat_10', $file_name) | Set-Content $file
}
pause
