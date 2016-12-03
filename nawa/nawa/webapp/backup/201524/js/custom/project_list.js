/**
 * 
 */

var username;
var handlingProjectCount;

$( document ).ready(function(token) {
    console.log( "시작" );
    username = $("#username-id");
    handlingProjectCount = $("#count-id");
    $("#profile-name").text(username);
    $("#project-count").text(handlingProjectCount);
});