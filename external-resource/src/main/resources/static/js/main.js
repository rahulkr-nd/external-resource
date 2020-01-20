$(document).ready(function() {
	var ENCODED_IN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1Nzg5MTI3MTEsImV4cCI6MTU3OTUxNzUxMSwianRpIjoiNzUxYTRhMDgtYmUxOC00NDE1LWJhZDctMjNmMTM2OWE0NTVmIiwiZW1haWwiOiJnYXVyYXYuYWdhcndhbEBuZXRyYWR5bmUuY29tIiwibmFtZSI6Im9wZXJhdGlvbnMgY29vcmRpbmF0b3IiLCJ1c2VybmFtZSI6Im9wc2NvIiwicGFydG5lcl9yZXNvdXJjZV9hY2Nlc3NpYmxlIjpmYWxzZSwiaW5zdGFsbGVyX3Jlc291cmNlX2FjY2Vzc2libGUiOnRydWV9.2UaP8QYHPswD7l98Zhd3b4QyDyPhIRdjszJ2wYNO9zc";
	var ENCODED_PA = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1Nzg5MTI3MTEsImV4cCI6MTU3OTUxNzUxMSwianRpIjoiNzUxYTRhMDgtYmUxOC00NDE1LWJhZDctMjNmMTM2OWE0NTVmIiwiZW1haWwiOiJnYXVyYXYuYWdhcndhbEBuZXRyYWR5bmUuY29tIiwibmFtZSI6Im9wZXJhdGlvbnMgY29vcmRpbmF0b3IiLCJ1c2VybmFtZSI6Im9wc2NvIiwicGFydG5lcl9yZXNvdXJjZV9hY2Nlc3NpYmxlIjp0cnVlLCJpbnN0YWxsZXJfcmVzb3VyY2VfYWNjZXNzaWJsZSI6ZmFsc2V9.btO6S4EgaOJdq8_BarlLt4fDMB-jMby628FeCSC55XE";
	var EX_ENCODED = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1NzcwOTk5NjEsImV4cCI6MTU3NzcwNDc2MSwianRpIjoiMGIwMmEzODMtZmQ2OC00MDk4LWFmY2MtNDJhOTYyMDY5N2E5IiwiZW1haWwiOiJnYXVyYXYuYWdhcndhbEBuZXRyYWR5bmUuY29tIiwibmFtZSI6IkdhdXJhdiAiLCJ1c2VybmFtZSI6InBhcnRuZXIxIiwicGVybWlzc2lvbnMiOlt7Im5hbWUiOiJjYW4gYWNjZXNzIHBhcnRuZXIgcmVzb3VyY2VzIiwiY29kZSI6ImNhbi5hY2Nlc3MtcGFydG5lci1yZXNvdXJjZXMifSx7Im5hbWUiOiJjYW4gbG9naW4iLCJjb2RlIjoiY2FuLmxvZ2luIn1dfQ.5BkAXAw5FZuI-hDczd-4oR8Al0MetssibdiWQ8N0D6g";
	var INVALID_ENCODED = "yJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1NzcwOTk5NjEsImV4cCI6MTU3NzcwNDc2MSwianRpIjoiMGIwMmEzODMtZmQ2OC00MDk4LWFmY2MtNDJhOTYyMDY5N2E5IiwiZW1haWwiOiJnYXVyYXYuYWdhcndhbEBuZXRyYWR5bmUuY29tIiwibmFtZSI6IkdhdXJhdiAiLCJ1c2VybmFtZSI6InBhcnRuZXIxIiwicGVybWlzc2lvbnMiOlt7Im5hbWUiOiJjYW4gYWNjZXNzIHBhcnRuZXIgcmVzb3VyY2VzIiwiY29kZSI6ImNhbi5hY2Nlc3MtcGFydG5lci1yZXNvdXJjZXMifSx7Im5hbWUiOiJjYW4gbG9naW4iLCJjb2RlIjoiY2FuLmxvZ2luIn1dfQ.5BkAXAw5FZuI-hDczd-4oR8Al0MetssibdiWQ8N0D6g";
  $("#partner").click(function(event) {
//	  $(".loader").show();
	  $("#partner").hide();
	  $("#installer").hide();
	  $.get("http://localhost:8080/access",{jwt:ENCODED_PA,user:'partner'}, function(data, status){
		  if (parseInt(data.id) == 1){
			 window.location.replace("https://idms.netradyne.com/console/#/user/jwt?requestor=resource-service&return_to=installer"	);

		  } 
		  else if (parseInt(data.id) == 2 || parseInt(data.id) == 0)	 {
			  window.location.replace("https://www.netradyne.com/");
		}
		  else if (parseInt(data.id) == 3){
//			  $(".loader").hide();
			  $("#logout").show();
			  myFunction(data.url);
		  }
		  else if (parseInt(data.id) == 4){
			  setTimeout(
					  function(){ 
//					  $(".loader").hide();
					  $("#auth").show();
					  }, 8000);
			  window.location.replace("https://www.netradyne.com/");
		  }
  });
});
  $("#installer").click(function(event) {
	  $("#partner").hide();
	  $("#installer").hide();
//	  $(".loader").show();
	  $.get("http://localhost:8080/access",{jwt:ENCODED_IN,user:'installer'}, function(data, status){
		  if (parseInt(data.id) == 1){
			 window.location.replace("https://idms.netradyne.com/console/#/user/jwt?requestor=resource-service&return_to=installer");

		  } 
		  else if (parseInt(data.id) == 2 || parseInt(data.id) == 0)	 {
			  window.location.replace("https://www.netradyne.com/");
		}
		  else if (parseInt(data.id) == 3){
//			  $(".loader").hide();
			  $("#logout").show();
			  myFunction(data.url);
		  }
		  else if (parseInt(data.id) == 4){
			  setTimeout(
					  function() {
					  $(".loader").hide();
					  $("#auth").show();
					  }, 8000);
			  window.location.replace("https://www.netradyne.com/");
		  }
  });
});
  
  $("#logout").click(function(event){
	  $.post("http://localhost:8080/removecookie",function(data,status){
		  $("IFRAME" ).remove();
		  $("#logout").hide();
		  $("#partner").show();
		  $("#installer").show();
		  console.log("cookie removed successfully!");
	  });
	  
  });

  
  function myFunction(url) {
	  var x = document.createElement("IFRAME");
	  x.setAttribute("src",url );
	  x.height = "100%";
	  x.width = "100%";
	  
	  document.body.appendChild(x);
	}
});